package com.johnnycarreiro.fts.infra.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.exceptions.NotFoundException;
import com.johnnycarreiro.fts.core.domain.validation.Error;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = DomainException.class)
  public ResponseEntity<?> handleDomainException(final DomainException ex) {
    return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
  }

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
  }

  record ApiError(String message, List<Error> errors) {
    static ApiError from(final DomainException ex) {
      return new ApiError(ex.getMessage(), ex.getErrors());
    }
  }
}
