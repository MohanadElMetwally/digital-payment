package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import org.springframework.stereotype.Component;
import com.example.digital_payment.identity.application.port.out.ResolveCurrencyPort;

@Component
public class ResolveCurrencyAdapter implements ResolveCurrencyPort {

    @Override
    public String resolveCurrency(String countryCode) {
        if (countryCode == null || countryCode.isBlank() || countryCode.equals("ZZ")) {
            throw new IllegalArgumentException(
                    "Invalid or unresolvable country code: " + countryCode);
        }

        try {
            Locale locale = Locale.of("", countryCode);
            CurrencyUnit currencyUnit = Monetary.getCurrency(locale);
            return currencyUnit.getCurrencyCode();
        } catch (UnknownCurrencyException e) {
            throw new IllegalArgumentException("No currency found for country code: " + countryCode,
                    e);
        }
    }

}
