package com.johnnycarreiro.fts.infra.transfer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer.TransferRepository;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferFeeEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferJpaRepository;

@Repository
public class JpaTransferRepository implements TransferRepository {

  private final TransferJpaRepository jpaRepository;

  public JpaTransferRepository(TransferJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Result<Void, DomainException> save(Transfer transfer) {
    try {
      jpaRepository.save(TransferEntity.fromDomain(transfer));
      return Result.success(null);
    } catch (Exception e) {
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

      var entity = TransferEntity.fromDomain(transfer);
      jpaRepository.save(entity); // Atualiza o registro.
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
