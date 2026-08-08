// identity/application/usecase/CheckUsersExistService.java
package com.example.digital_payment.identity.application.usecase;

import com.example.digital_payment.identity.application.port.in.CheckUsersExistUseCase;
import com.example.digital_payment.identity.application.port.out.LoadUserPort;

public class CheckUsersExistService implements CheckUsersExistUseCase {

    private final LoadUserPort loadUserPort;

    public CheckUsersExistService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public boolean hasAnyUsers() {
        return loadUserPort.existsAny();
    }
}
