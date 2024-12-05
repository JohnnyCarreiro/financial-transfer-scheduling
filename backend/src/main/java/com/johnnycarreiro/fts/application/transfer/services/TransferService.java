package com.johnnycarreiro.fts.application.transfer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferJpaRepository;

@Service
public class TransferService {

  private final TransferJpaRepository transferJpaRepository;

  public TransferService(TransferJpaRepository transferJpaRepository) {
    this.transferJpaRepository = transferJpaRepository;
  }

  public Optional<Transfer> findById(String id) {
    return transferJpaRepository.findById(id).map(TransferEntity::toDomain);
  }

  public List<Transfer> listAll() {
    return transferJpaRepository.findAll().stream()
        .map(TransferEntity::toDomain)
        .toList();
  }
}
