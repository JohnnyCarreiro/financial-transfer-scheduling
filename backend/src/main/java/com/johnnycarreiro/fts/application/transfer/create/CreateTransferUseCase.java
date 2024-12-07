package com.johnnycarreiro.fts.application.transfer.create;

import java.time.Instant;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.validation.StackValidationHandler;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer.TransferRepository;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;

/**
 * CreateTransferUseCase
 * 
 * This class represents the use case for creating a transfer. It manages the
 * process of calculating the
 * transfer fee, validating the transfer data, and saving the transfer to the
 * repository. The use case
 * interacts with various services like the `TransferFeeCalculatorService` to
 * determine the appropriate
 * fee for the transfer, and uses the `TransferRepository` to persist the
 * transfer.
 */
public class CreateTransferUseCase {

  private final TransferFeeCalculatorService feeCalculatorService;
  private final TransferRepository transferRepository;
  private ValidationHandler validationHandler = StackValidationHandler.create();

  /**
   * Constructor to initialize the `CreateTransferUseCase` with required services.
   * 
   * @param feeCalculatorService an instance of the `TransferFeeCalculatorService`
   *                             to calculate the fee.
   * @param transferRepository   an instance of the `TransferRepository` to save
   *                             the transfer.
   */
  public CreateTransferUseCase(TransferFeeCalculatorService feeCalculatorService,
      TransferRepository transferRepository) {
    this.feeCalculatorService = feeCalculatorService;
    this.transferRepository = transferRepository;
  }

  /**
   * Executes the use case for creating a transfer.
   * 
   * This method performs the following steps:
   * 1. Lists all available transfer fee rules from the repository.
   * 2. Calculates the transfer fee based on the scheduled date.
   * 3. Validates the transfer data.
   * 4. If no validation errors occur, it saves the transfer to the repository.
   * 
   * @param transferCommnd a `CreateTransferCommand` object containing the details
   *                       of the transfer to be created.
   * @return a `Result<Transfer, DomainException>` indicating the success or
   *         failure of the transfer creation.
   */
  public Result<Transfer, ValidationHandler> execute(CreateTransferCommand transferCommnd) {
    final var transferFees = transferRepository.listAllFees();
    if (transferFees.isError()) {
      return Result.error(StackValidationHandler.create(transferFees.getError()));
    }

    this.feeCalculatorService.setTransferFee(transferFees.getSuccess());

    var transferFeeResult = this.feeCalculatorService.calculateFee(
        Instant.now(),
        Instant.parse(transferCommnd.scheduledDate()));

    if (transferFeeResult.isError()) {
      return Result.error(StackValidationHandler.create(transferFees.getError()));
    }

    TransferFee transferFee = transferFeeResult.getSuccess();

    Transfer transfer = Transfer.create(
        Account.create(transferCommnd.sourceAccount().toString()),
        Account.create(transferCommnd.destinationAccount().toString()),
        transferCommnd.amount(),
        Instant.parse(transferCommnd.scheduledDate()),
        transferFee);

    transfer.validate(validationHandler);

    return validationHandler.hasErrors()
        ? Result.error(validationHandler)
        : create(transfer);
  }

  /**
   * Saves the transfer to the repository.
   * 
   * This method attempts to persist the transfer to the repository. If the save
   * operation fails, it returns
   * a `Result.error` containing the error message. If successful, it returns the
   * created transfer.
   * 
   * @param transfer the `Transfer` object to be saved to the repository.
   * @return a `Result<Transfer, DomainException>` indicating the success or
   *         failure of the save operation.
   */
  private Result<Transfer, ValidationHandler> create(Transfer transfer) {
    Result<Void, DomainException> saveResult = transferRepository.save(transfer);

    if (saveResult.isError()) {
      return Result.error(StackValidationHandler.create(saveResult.getError()));
    }

    return Result.success(transfer);
  }
}
