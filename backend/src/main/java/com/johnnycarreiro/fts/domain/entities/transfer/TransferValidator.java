
package com.johnnycarreiro.fts.domain.entities.transfer;

import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.core.domain.validation.Validator;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class TransferValidator extends Validator {
  private final Transfer transfer;

  public TransferValidator(final Transfer transfer, final ValidationHandler handler) {
    super(handler);
    this.transfer = transfer;
  }

  @Override
  public void validate() {

    if (Objects.isNull(transfer)) {
      this.validationHandler().append(new Error("Transfer cannot be null"));
      return;
    }

    if (transfer.getAmount() == null || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      this.validationHandler().append(new Error("Amount must be greater than zero"));
    }

    if (Objects.isNull(transfer.getSourceAccount())) {
      this.validationHandler().append(new Error("Source account cannot be null"));
    }

    if (Objects.isNull(transfer.getDestinationAccount())) {
      this.validationHandler().append(new Error("Target account cannot be null"));
    }

    if (Objects.equals(transfer.getSourceAccount(), transfer.getDestinationAccount())) {
      this.validationHandler().append(new Error("Source and target accounts must be different"));
    }

    if (transfer.getScheduledDate() == null) {
      this.validationHandler().append(new Error("Scheduled date cannot be null"));
    }
    LocalDate scheduledDate = transfer.getScheduledDate()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
    LocalDate today = Instant.now()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
    if (scheduledDate.isBefore(today)) {
      this.validationHandler().append(new Error("Scheduled date cannot be in the past"));
    }
  }
}
