package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.port.out.ResolveCountryPort;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Component
public class PhoneCountryResolverAdapter implements ResolveCountryPort {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public String resolveCountry(String phoneNumber) {
        try {
            PhoneNumber parsed = phoneNumberUtil.parse(phoneNumber, null);
            String regionCode = phoneNumberUtil.getRegionCodeForNumber(parsed);
            if (regionCode == null) {
                throw new InvalidUserDataException(
                    "Cannot resolve country from phone: " + phoneNumber);
            }
            return regionCode;
        } catch (NumberParseException e) {
            throw new InvalidUserDataException("Invalid phone number format: " + phoneNumber);
        }
    }
}