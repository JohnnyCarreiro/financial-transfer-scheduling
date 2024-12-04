package com.johnnycarreiro.fts.core.domain.exceptions;

import java.util.List;
import com.johnnycarreiro.fts.core.domain.validation.Error;

public class DomainException extends NoStackTraceException {

  private final List<Error> errors;

  protected DomainException(final String aMessage, final List<Error> errors) {
    super(aMessage);

    this.errors = errors;
  }

  public static DomainException with(Error anError) {
    return new DomainException(anError.message(), List.of(anError));
  }

  public static DomainException with(List<Error> errors) {
    return new DomainException("", errors);
  }

  public List<Error> getErrors() {
    return errors;
  }
}
