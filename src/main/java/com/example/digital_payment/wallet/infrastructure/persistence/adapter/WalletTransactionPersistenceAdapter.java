package com.example.digital_payment.wallet.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionByTransactionId;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionPort;
import com.example.digital_payment.wallet.application.port.out.LoadWalletTransactionsPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletTransactionPort;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletTransactionPort;
import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;
import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletTransactionEntity;
import com.example.digital_payment.wallet.infrastructure.persistence.mapper.WalletTransactionPersistenceMapper;
import com.example.digital_payment.wallet.infrastructure.persistence.repository.WalletTransactionJpaRepository;

@Component
public class WalletTransactionPersistenceAdapter
        implements SaveWalletTransactionPort, LoadWalletTransactionPort, LoadWalletTransactionsPort,
        LoadWalletTransactionByTransactionId, UpdateWalletTransactionPort {
    private final WalletTransactionJpaRepository jpaRepository;
    private final WalletTransactionPersistenceMapper mapper;

    public WalletTransactionPersistenceAdapter(WalletTransactionJpaRepository jpaRepository,
            WalletTransactionPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(WalletTransactions transaction) {
        WalletTransactionEntity entity = mapper.toEntity(transaction);
        jpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletTransactions> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletTransactions> loadWalletTransactions(UUID walletId) {
        return jpaRepository.findByWalletId(walletId).stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional
    public Optional<WalletTransactions> findByTransactionId(UUID transactionId) {
        return jpaRepository.findByTransactionId(transactionId).map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void update(WalletTransactions wtx) {
        WalletTransactionEntity entity = jpaRepository.getReferenceById(wtx.getId());
        mapper.update(wtx, entity);
    }

}
