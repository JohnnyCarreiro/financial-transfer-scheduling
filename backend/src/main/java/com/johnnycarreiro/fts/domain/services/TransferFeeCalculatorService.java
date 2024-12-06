package com.johnnycarreiro.fts.domain.services;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;

public class TransferFeeCalculatorService {

  private List<TransferFee> feeRules;

  public TransferFeeCalculatorService() {
  }

  /**
   * Calculates the fee for a transfer based on the scheduled and target dates.
   *
   * @param scheduledDateAt The date the transfer was scheduled.
   * @param scheduleDateFor The date the transfer is intended for.
   * @return A Result encapsulating either the matched TransferFee or an error
   *         message.
   */
  public Result<TransferFee, DomainException> calculateFee(Instant scheduledDateAt, Instant scheduleDateFor) {
    if (feeRules == null || feeRules.isEmpty()) {
      return Result.error(DomainException.with(new Error("Fee rules are not configured.")));
    }

    long daysDifference = Duration.between(scheduledDateAt, scheduleDateFor).toDays();

    return feeRules.stream()
        .filter(rule -> (rule.getMinDays() <= daysDifference) &&
            (rule.getMaxDays() == null || rule.getMaxDays() >= daysDifference))
        .findFirst()
        .<Result<TransferFee, DomainException>>map(Result::success)
        .orElseGet(() -> Result.error(DomainException.with(new Error("No fee rule matches the provided dates."))));
  }

  /**
   * Updates the fee rules for the service.
   *
   * @param feeRules The new list of fee rules.
   * @return A Result indicating success or an error message.
   */
  public Result<Void, String> setTransferFee(List<TransferFee> feeRules) {
    if (feeRules == null || feeRules.isEmpty()) {
      return Result.error("Fee rules cannot be null or empty.");
    }
    this.feeRules = feeRules;
    return Result.success(null); // Success without a value
  }
}
