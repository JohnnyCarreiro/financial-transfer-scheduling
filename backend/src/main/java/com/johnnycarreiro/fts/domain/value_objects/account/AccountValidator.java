package com.johnnycarreiro.fts.domain.value_objects.account;

import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.core.domain.validation.Validator;

import java.util.Objects;

public class AccountValidator extends Validator {
  private final Account account;

  public AccountValidator(final Account account, final ValidationHandler handler) {
    super(handler);
    this.account = account;
  }

  @Override
  public void validate() {
    if (Objects.isNull(account)) {
      this.validationHandler().append(new Error("Account cannot be null"));
      return;
    }

    if (Objects.isNull(account.getValue())) {
      this.validationHandler().append(new Error("Account number cannot be null"));
    }

    if (account.getValue().isEmpty()) {
      this.validationHandler().append(new Error("Account number cannot be empty"));
    }

    if (account.getValue().length() != 10) {
      this.validationHandler().append(new Error("Account number must have 10 digits"));
    }
  }
}
