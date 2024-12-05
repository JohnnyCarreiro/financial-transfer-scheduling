package com.johnnycarreiro.fts.domain.services;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.johnnycarreiro.fts.domain.value_objects.transfer_fee.TransferFee;

public class TransferFeeCalculatorService {

  private final List<TransferFee> feeRules;

  public TransferFeeCalculatorService(List<TransferFee> feeRules) {
    this.feeRules = feeRules;
  }

  public TransferFee calculateFee(Instant scheduledDateAt, Instant scheduleDateFor) {
    long daysDifference = Duration.between(scheduledDateAt, scheduleDateFor).toDays();

    return feeRules.stream()
        .filter(rule -> (rule.getMinDays() <= daysDifference) &&
            (rule.getMaxDays() == null || rule.getMaxDays() >= daysDifference))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No fee rule matches the provided dates."));
  }
}
