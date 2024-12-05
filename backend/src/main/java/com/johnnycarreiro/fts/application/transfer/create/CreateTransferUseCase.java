package com.johnnycarreiro.fts.application.transfer.create;

import java.time.Instant;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer.TransferRepository;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;

/**
 * CreateTransferUseCase
 */
public class CreateTransferUseCase {

  private final TransferFeeCalculatorService feeCalculatorService;
  private final TransferRepository transferRepository;

  public CreateTransferUseCase(TransferFeeCalculatorService feeCalculatorService,
      TransferRepository transferRepository) {
    this.feeCalculatorService = feeCalculatorService;
    this.transferRepository = transferRepository;
  }

  public Result<Transfer, DomainException> execute(CreateTransferCommand transferCommnd) {
    try {
      final var transferFees = transferRepository.listAllFees();
      transferFees.ifPresent(
          fees -> {
            this.feeCalculatorService.setTransferFee(fees);
            return;
          },
          error -> {
            throw error;
          });

      TransferFee transferFee = this.feeCalculatorService.calculateFee(
          Instant.now(),
          transferCommnd.scheduledDate());

      Transfer transfer = Transfer.create(
          Account.create(transferCommnd.sourceAccount().toString()),
          Account.create(transferCommnd.destinationAccount().toString()),
          transferCommnd.amount(),
          transferCommnd.scheduledDate(),
          transferFee);

      Result<Void, DomainException> saveResult = transferRepository.save(transfer);

      if (saveResult.isError()) {
        return Result
            .error(DomainException.with(new Error("Failed to create transfer: " + saveResult.getError().toString())));
      }

      return Result.success(transfer);

    } catch (Exception e) {
      return Result.error(DomainException.with(new Error("Error during transfer creation" + e.getMessage())));
    }
  }
}
