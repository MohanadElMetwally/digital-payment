package com.example.digital_payment.audit.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import com.example.digital_payment.audit.application.dto.CreateAuditLogCommand;
import com.example.digital_payment.audit.application.port.in.AuditUserRegisteredUseCase;
import com.example.digital_payment.audit.domain.enums.AuditAction;
import com.example.digital_payment.audit.domain.enums.EntityType;
import com.example.digital_payment.shared.events.UserRegisteredEvent;

@Component
public class AuditUserRegisteredEventListener {
    private final AuditUserRegisteredUseCase auditUserRegisteredUseCase;

    public AuditUserRegisteredEventListener(AuditUserRegisteredUseCase auditUserRegisteredUseCase) {
        this.auditUserRegisteredUseCase = auditUserRegisteredUseCase;
    }

    @ApplicationModuleListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        auditUserRegisteredUseCase.handle(new CreateAuditLogCommand(event.userId(),
                AuditAction.CREATE, EntityType.USER, event.userId(), null, null));
    }
}
