package com.johnnycarreiro.fts.domain.entities.transfer_fee;

import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.core.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class TransferFeeValidator extends Validator {

  private final TransferFee transferFee;

  public TransferFeeValidator(final TransferFee transferFee, final ValidationHandler handler) {
    super(handler);
    this.transferFee = transferFee;
  }

  @Override
  public void validate() {
    if (Objects.isNull(transferFee)) {
      this.validationHandler().append(new Error("Transfer fee cannot be null"));
      return;
    }

    if (Objects.isNull(transferFee.getName()) || transferFee.getName().isBlank()) {
      this.validationHandler().append(new Error("Transfer fee name cannot be null or blank"));
    }

    if (transferFee.getMinDays() < 0) {
      this.validationHandler().append(new Error("Minimum days cannot be negative"));
    }

    if (transferFee.getMaxDays() != null && transferFee.getMaxDays() < transferFee.getMinDays()) {
      this.validationHandler().append(new Error("Maximum days cannot be less than minimum days"));
    }

    if (transferFee.getFixedFee() == null || transferFee.getFixedFee().compareTo(BigDecimal.ZERO) < 0) {
      this.validationHandler().append(new Error("Fixed fee cannot be null or negative"));
    }

    if (transferFee.getPercentageFee() == null || transferFee.getPercentageFee().compareTo(BigDecimal.ZERO) < 0) {
      this.validationHandler().append(new Error("Percentage fee cannot be null or negative"));
    }
  }
}
