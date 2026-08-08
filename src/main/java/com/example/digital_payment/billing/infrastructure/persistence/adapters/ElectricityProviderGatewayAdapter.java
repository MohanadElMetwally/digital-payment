package com.example.digital_payment.billing.infrastructure.persistence.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.digital_payment.billing.application.port.out.ProviderGatewayPort;
import com.example.digital_payment.billing.domain.exceptions.BillAlreadyPaidException;
import com.example.digital_payment.billing.domain.exceptions.ProviderBillNotFound;
import com.example.digital_payment.billing.domain.exceptions.ProviderUnavailableException;
import com.example.digital_payment.billing.domain.model.entities.ProviderBills;
import com.example.digital_payment.billing.infrastructure.dto.ProviderBillResponse;
import com.example.digital_payment.billing.infrastructure.persistence.mappers.ProviderBillMapper;

@Component("ELECTRICITY_PROVIDER")
public class ElectricityProviderGatewayAdapter implements ProviderGatewayPort {
    private final RestTemplate restTemplate;
    private final ProviderBillMapper providerBillMapper;

    @Value("${base.provider.url}")
    private String baseUrl;

    public ElectricityProviderGatewayAdapter(RestTemplate restTemplate,
        ProviderBillMapper providerBillMapper) {
        this.restTemplate = restTemplate;
        this.providerBillMapper = providerBillMapper;
    }

    @Override
    public ProviderBills fetchBill(String externalCustomerNumber) {
        try {
            ProviderBillResponse response = restTemplate.getForObject(baseUrl + "/{customerNumber}",
                ProviderBillResponse.class, externalCustomerNumber);

            if (response == null) {
                throw new ProviderBillNotFound(
                    "Provider returned an empty response for: " + externalCustomerNumber);
            }

            return providerBillMapper.toDomain(response);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProviderBillNotFound(
                    "Provider bill not found for customer: " + externalCustomerNumber);
            } else if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new BillAlreadyPaidException("Bill has already been paid");
            }
            throw new ProviderUnavailableException(
                "Unexpected client error from provider: " + e.getStatusCode());

        } catch (HttpServerErrorException e) {
            throw new ProviderUnavailableException("Provider server error: " + e.getStatusCode());

        } catch (RestClientException e) {
            throw new ProviderUnavailableException("Could not reach provider: " + e.getMessage());
        }
    }
}
