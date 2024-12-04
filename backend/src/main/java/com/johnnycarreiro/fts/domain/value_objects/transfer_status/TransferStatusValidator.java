package com.johnnycarreiro.fts.domain.value_objects.transfer_status;

import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.core.domain.validation.Validator;

import java.util.Objects;

public class TransferStatusValidator extends Validator {
  private final TransferStatus transferStatus;

  public TransferStatusValidator(final TransferStatus transferStatus, final ValidationHandler handler) {
    super(handler);
    this.transferStatus = transferStatus;
  }

  @Override
  public void validate() {
    if (Objects.isNull(transferStatus) || transferStatus.getValue() == null) {
      this.validationHandler().append(new Error("Transfer status cannot be null"));
    }
  }
}
