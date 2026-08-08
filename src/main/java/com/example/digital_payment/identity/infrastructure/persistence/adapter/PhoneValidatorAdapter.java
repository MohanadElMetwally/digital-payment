package com.example.digital_payment.identity.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import com.example.digital_payment.identity.application.port.out.PhoneValidatorPort;
import com.example.digital_payment.identity.domain.exceptions.InvalidUserDataException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Component
public class PhoneValidatorAdapter implements PhoneValidatorPort {
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    @Override
    public void validate(String phoneNumber) {
        try {
            PhoneNumber parsed = phoneUtil.parse(phoneNumber, null);
            if (!phoneUtil.isValidNumber(parsed)) {
                throw new InvalidUserDataException("Phone number is not valid: " + phoneNumber);
            }
        } catch (NumberParseException e) {
            throw new InvalidUserDataException("Invalid phone number format: " + phoneNumber);
        }
    }

}
