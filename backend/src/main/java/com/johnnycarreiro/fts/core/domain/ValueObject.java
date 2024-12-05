package com.johnnycarreiro.fts.core.domain;

import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;

public abstract class ValueObject<T> {
  public abstract T getValue();

  public abstract void validate(ValidationHandler handler);
}
