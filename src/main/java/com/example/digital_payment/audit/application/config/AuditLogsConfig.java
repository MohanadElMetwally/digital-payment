package com.example.digital_payment.audit.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.digital_payment.audit.application.mapper.AuditLogMapper;
import com.example.digital_payment.audit.application.port.in.AuditUserRegisteredUseCase;
import com.example.digital_payment.audit.application.port.in.LoadAuditLogsUseCase;
import com.example.digital_payment.audit.application.port.out.LoadAuditLogsPort;
import com.example.digital_payment.audit.application.port.out.SaveAuditLogPort;
import com.example.digital_payment.audit.application.usecase.AuditUserRegisteredService;
import com.example.digital_payment.audit.application.usecase.LoadAuditLogsService;

@Configuration
public class AuditLogsConfig {
    @Bean
    public AuditLogMapper auditLogMapper() {
        return new AuditLogMapper();
    }

    @Bean
    public LoadAuditLogsUseCase loadAuditLogsUseCase(LoadAuditLogsPort loadAuditLogsPort,
            AuditLogMapper auditLogMapper) {
        return new LoadAuditLogsService(loadAuditLogsPort, auditLogMapper);
    }

    @Bean
    public AuditUserRegisteredUseCase auditUserRegisteredUseCase(
            SaveAuditLogPort saveAuditLogPort) {
        return new AuditUserRegisteredService(saveAuditLogPort);
    }
}
