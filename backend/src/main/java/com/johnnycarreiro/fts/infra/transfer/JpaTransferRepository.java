package com.johnnycarreiro.fts.infra.transfer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer.TransferRepository;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferFeeEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferJpaRepository;

@Service
public class JpaTransferRepository implements TransferRepository {

  private final TransferJpaRepository jpaRepository;

  public JpaTransferRepository(TransferJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Result<Void, DomainException> save(Transfer transfer) {
    try {
      if (transfer.getTransferFee() == null) {
        return Result.error(DomainException.with(new Error("Transfer must have a TransferFee associated")));
      }

      TransferEntity entity = TransferEntity.fromDomain(transfer);

      if (entity.getTransferFee() == null || entity.getTransferFee().getId() == null) {
        return Result.error(DomainException.with(new Error("Invalid TransferFee associated")));
      }

      jpaRepository.save(entity);
      return Result.success(null);
    } catch (Exception e) {
      // log.error("Error saving transfer: ", e);
      return Result.error(DomainException.with(new Error("Error saving transfer: " + e.getMessage())));
    }
  }

  @Override
  public Result<Optional<Transfer>, DomainException> findById(String id) {
    try {
      Optional<TransferEntity> entityOpt = jpaRepository.findByIdWithFee(id);
      if (entityOpt.isPresent()) {
        return Result.success(Optional.of(entityOpt.get().toDomain()));
      }
      return Result.error(DomainException.with(new Error("Transfer not found")));
    } catch (Exception e) {
      return Result.error(DomainException.with(new Error("Failed to fetch transfer: " + e.getMessage())));
    }
  }

  @Override
  public Result<List<Transfer>, DomainException> listAll() {
    return Result.success(jpaRepository.findAll().stream()
        .map(TransferEntity::toDomain)
        .collect(Collectors.toList()));
  }

  public void deleteById(String id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public Result<Void, DomainException> update(Transfer transfer) {
    try {
      var existingTransfer = jpaRepository.findById(transfer.getId().getValue());
      if (existingTransfer.isEmpty()) {
        return Result.error(DomainException.with(new Error("Transfer not found")));
      }

      // Verificar se TransferFee está associada corretamente
      if (transfer.getTransferFee() == null) {
        return Result.error(DomainException.with(new Error("Transfer must have a TransferFee associated")));
      }

      var entity = TransferEntity.fromDomain(transfer);

      // Se a TransferFee não existir no banco de dados, pode lançar um erro
      if (entity.getTransferFee() == null || entity.getTransferFee().getId() == null) {
        return Result.error(DomainException.with(new Error("Invalid TransferFee associated")));
      }

      // Atualiza a Transfer
      jpaRepository.save(entity);

      return Result.success(null);
    } catch (Exception e) {
      return Result.error(DomainException.with(new Error("Error updating transfer: " + e.getMessage())));
    }
  }

  @Override
  public Result<List<TransferFee>, DomainException> listAllFees() {
    try {
      var feeEntities = jpaRepository.findAllFees();
      var fees = feeEntities.stream()
          .map(TransferFeeEntity::toDomain)
          .collect(Collectors.toList());
      return Result.success(fees);
    } catch (Exception e) {
      return Result.error(DomainException.with(new Error("Error listing all fees: " + e.getMessage())));
    }
  }
}
