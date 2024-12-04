package com.johnnycarreiro.fts.core.domain.exceptions;

import com.johnnycarreiro.fts.core.domain.validation.Error;
import com.johnnycarreiro.fts.core.domain.Entity;
import com.johnnycarreiro.fts.core.domain.Identifier;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {
  protected NotFoundException(final String message, final List<Error> errors) {
    super(message, errors);
  }

  public static NotFoundException with(
      final Class<? extends Entity<?>> anEntity,
      final Identifier<?> id) {
    final var anError = "%s with ID %s was not found".formatted(
        anEntity.getSimpleName(),
        id.getValue());
    return new NotFoundException(anError, Collections.emptyList());
  }

  public static NotFoundException with(final Error error) {
    return new NotFoundException(error.message(), List.of(error));
  }
}
