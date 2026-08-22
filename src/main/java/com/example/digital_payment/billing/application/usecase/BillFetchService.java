package com.example.digital_payment.billing.application.usecase;

import java.util.Map;
import java.util.Optional;
import com.example.digital_payment.billing.application.dto.BillFetchCommand;
import com.example.digital_payment.billing.application.dto.BillResult;
import com.example.digital_payment.billing.application.mapper.BillMapper;
import com.example.digital_payment.billing.application.port.in.BillFetchUseCase;
import com.example.digital_payment.billing.application.port.out.LoadBillByExternalRefPort;
import com.example.digital_payment.billing.application.port.out.LoadBillerPort;
import com.example.digital_payment.billing.application.port.out.ProviderGatewayPort;
import com.example.digital_payment.billing.application.port.out.SaveBillPort;
import com.example.digital_payment.billing.application.port.out.SyncBillPort;
import com.example.digital_payment.billing.domain.exceptions.BillerNotFoundException;
import com.example.digital_payment.billing.domain.exceptions.ProviderBillNotFound;
import com.example.digital_payment.billing.domain.exceptions.ProviderNotSupportedException;
import com.example.digital_payment.billing.domain.model.entities.Billers;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.billing.domain.model.entities.ProviderBills;
import com.example.digital_payment.billing.domain.model.valueobjects.BillCreateData;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class BillFetchService implements BillFetchUseCase {
    private final LoadBillerPort loadBillerPort;
    private final LoadBillByExternalRefPort loadBillByExternalRefPort;
    private final SaveBillPort saveBillPort;
    private final SyncBillPort syncBillPort;
    private final TransactionPort transactionPort;
    private final Map<String, ProviderGatewayPort> providers;
    private final BillMapper billMapper;

    public BillFetchService(LoadBillerPort loadBillerPort,
            LoadBillByExternalRefPort loadBillByExternalRefPort, SaveBillPort saveBillPort,
            SyncBillPort syncBillPort, TransactionPort transactionPort,
            Map<String, ProviderGatewayPort> providers, BillMapper billMapper) {
        this.loadBillerPort = loadBillerPort;
        this.loadBillByExternalRefPort = loadBillByExternalRefPort;
        this.saveBillPort = saveBillPort;
        this.syncBillPort = syncBillPort;
        this.transactionPort = transactionPort;
        this.providers = providers;
        this.billMapper = billMapper;
    }

    @Override
    public BillResult fetchBill(BillFetchCommand command) {
        Billers biller = loadBillerPort.findById(command.billerId())
                .orElseThrow(() -> new BillerNotFoundException(command.billerId()));

        ProviderGatewayPort gateway = resolveGateway(biller);
        ProviderBills providerBill = gateway.fetchBill(command.externalCustomerNumber());

        // dummy exception since all requests go to a single fake bill controller
        if (!biller.getServiceProvider().equals(providerBill.getProvider())) {
            throw new ProviderBillNotFound("No Bill found from this provider");
        }

        return transactionPort.execute(() -> {
            Bills bill = syncBillWithProvider(providerBill, command);
            return billMapper.toResult(bill);
        });
    }

    private ProviderGatewayPort resolveGateway(Billers biller) {
        return Optional.ofNullable(providers.get(biller.getServiceProvider().toString()))
                .orElseThrow(() -> new ProviderNotSupportedException(
                        "No gateway registered for provider: " + biller.getServiceProvider()));
    }

    private Bills syncBillWithProvider(ProviderBills providerBill, BillFetchCommand command) {
        Optional<Bills> existing = loadBillByExternalRefPort
                .findByExternalBillIdAndBillerId(providerBill.getId(), command.billerId());

        if (existing.isPresent()) {
            Bills found = existing.get();
            if (found.isResyncable()) {
                found.syncFromProvider(providerBill.getAmount());
            }
            return syncBillPort.sync(found);
        }

        BillCreateData createData = new BillCreateData(command.billerId(), command.userId(),
                providerBill.getCustomerNumber(), providerBill.getCustomerName(),
                providerBill.getId(), providerBill.getAmount(), providerBill.getCurrency(),
                providerBill.getBillingPeriodStart(), providerBill.getBillingPeriodEnd(),
                providerBill.getDueDate());
        return saveBillPort.save(Bills.create(createData));
    }
}
