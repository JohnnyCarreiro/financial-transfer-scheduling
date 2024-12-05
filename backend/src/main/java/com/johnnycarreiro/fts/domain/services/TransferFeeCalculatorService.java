package com.johnnycarreiro.fts.domain.services;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;

public class TransferFeeCalculatorService {

  private List<TransferFee> feeRules;

  public TransferFeeCalculatorService() {
  }

  public TransferFee calculateFee(Instant scheduledDateAt, Instant scheduleDateFor) {
    if (this.feeRules == null) {
      throw new IllegalArgumentException("No fee rule matches the provided dates.");
    }
    long daysDifference = Duration.between(scheduledDateAt, scheduleDateFor).toDays();

    return feeRules.stream()
        .filter(rule -> (rule.getMinDays() <= daysDifference) &&
            (rule.getMaxDays() == null || rule.getMaxDays() >= daysDifference))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No fee rule matches the provided dates."));
  }

  public void setTransferFee(List<TransferFee> feeRules) {
    if (feeRules == null || feeRules.isEmpty()) {
    }
    this.feeRules = feeRules;
  }
}
