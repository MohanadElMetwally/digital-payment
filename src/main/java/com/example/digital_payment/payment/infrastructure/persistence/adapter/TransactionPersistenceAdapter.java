package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.digital_payment.payment.application.port.out.LoadTransactionByUserIdAndKeyPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionsPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.payment.infrastructure.persistence.entity.TransactionEntity;
import com.example.digital_payment.payment.infrastructure.persistence.mapper.TransactionPersistenceMapper;
import com.example.digital_payment.payment.infrastructure.persistence.repository.TransactionJpaRepository;

@Component
public class TransactionPersistenceAdapter implements SaveTransactionPort, LoadTransactionsPort,
    LoadTransactionPort, LoadTransactionByUserIdAndKeyPort, UpdateTransactionPort {
    private final TransactionJpaRepository transactionJpaRepository;
    private final TransactionPersistenceMapper mapper;

    public TransactionPersistenceAdapter(TransactionJpaRepository transactionJpaRepository,
        TransactionPersistenceMapper mapper) {
        this.transactionJpaRepository = transactionJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(Transactions transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        transactionJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transactions> loadTransactions(UUID userId) {
        return transactionJpaRepository.findByUserId(userId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Transactions> findById(UUID id) {
        return transactionJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Transactions> findByUserIdAndKey(UUID userId, UUID idempotencyKey) {
        return transactionJpaRepository.findByUserIdAndIdempotencyKey(userId, idempotencyKey)
            .map(mapper::toDomain);
    }

    @Override
    public void update(Transactions transaction) {
        TransactionEntity entity = transactionJpaRepository.getReferenceById(transaction.getId());
        mapper.updateEntity(transaction, entity);
    }
}
