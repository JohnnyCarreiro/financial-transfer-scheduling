package com.johnnycarreiro.fts.domain.entities.transfer;

import java.util.UUID;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing the lifecycle of the Transfer aggregate.
 * Provides methods for persistence and querying domain-related data.
 */
public interface TransferRepository {

  /**
   * Lists all Transfer entities from the persistent store.
   * 
   * @return a Result containing a list of Transfer entities.
   */
  Result<List<Transfer>, DomainException> listAll();

  /**
   * Saves a Transfer entity to the persistent store.
   * If the entity already exists, it should be updated.
   *
   * @param transfer the Transfer entity to save or update.
   * @return a Result indicating success or an error.
   */
  Result<Void, DomainException> save(Transfer transfer);

  /**
   * Finds a Transfer by its unique identifier.
   *
   * @param transferId the unique identifier of the Transfer.
   * @return a Result containing the Transfer if found or an error if not.
   */
  Result<Optional<Transfer>, DomainException> findById(String transferId);

  /**
   * Updates an existing Transfer entity.
   * Should throw an exception if the entity does not exist.
   *
   * @param transfer the Transfer entity with updated information.
   * @return a Result indicating success or an error.
   */
  Result<Void, DomainException> update(Transfer transfer);

  // /**
  // * Finds an applicable TransferFee based on the given amount and transfer
  // type.
  // *
  // * @param amount the amount of the transfer.
  // * @param transferType the type of the transfer.
  // * @return a Result containing the applicable TransferFee if found or an error
  // * if not.
  // */
  // Result<Optional<TransferFee>, DomainException> findApplicableFee(BigDecimal
  // amount, String transferType);

  /**
   * Lists all TransferFee entities.
   *
   * @return a Result containing a list of TransferFee entities.
   */
  Result<List<TransferFee>, DomainException> listAllFees();
}
