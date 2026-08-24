package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardsPort;
import com.example.digital_payment.payment.application.port.out.SaveCreditCardPort;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.infrastructure.persistence.entity.CreditCardEntity;
import com.example.digital_payment.payment.infrastructure.persistence.mapper.CreditCardPersistenceMapper;
import com.example.digital_payment.payment.infrastructure.persistence.repository.CreditCardJpaRepository;

@Component
public class CreditCardPersistenceAdapter
        implements SaveCreditCardPort, LoadCreditCardsPort, LoadCreditCardPort {
    private final CreditCardJpaRepository creditCardJpaRepository;
    private final CreditCardPersistenceMapper mapper;

    public CreditCardPersistenceAdapter(CreditCardJpaRepository creditCardJpaRepository,
            CreditCardPersistenceMapper mapper) {
        this.creditCardJpaRepository = creditCardJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CreditCards save(CreditCards card) {
        CreditCardEntity entity = mapper.toEntity(card);
        creditCardJpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCards> findByUserId(UUID userId) {
        return creditCardJpaRepository.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCards> findById(UUID cardId) {
        return creditCardJpaRepository.findById(cardId).map(mapper::toDomain);
    }
}
