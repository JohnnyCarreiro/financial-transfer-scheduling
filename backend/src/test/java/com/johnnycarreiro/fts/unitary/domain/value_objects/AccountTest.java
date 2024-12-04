package com.johnnycarreiro.fts.unitary.domain.value_objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.ThrowsValidationHandler;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;

@DisplayName("Account Test Suite")
public class AccountTest {

  @Test
  @DisplayName("Valid - Create Account")
  public void givenValidAccountNumber_whenCallCreate_thenInstantiateNewAccount() {
    final var expectedValue = "0123456789";

    final var account = Account.from(expectedValue);

    Assertions.assertNotNull(account);
    Assertions.assertEquals(expectedValue, account.getValue());
  }

  @Test
  @DisplayName("Null Account Number - Throws an Exception")
  public void givenNullAccountNumber_whenCallCreateValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Account number cannot be null";

    final var account = Account.from(null);

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> account.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

  @Test
  @DisplayName("Empty Account Number - Throws an Exception")
  public void givenEmptyAccountNumber_whenCallCreateValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Account number cannot be empty";

    final var account = Account.from("");

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> account.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

  @Test
  @DisplayName("Invalid Length Account Number - Throws an Exception")
  public void givenInvalidLengthAccountNumber_whenCallCreateValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Account number must have 10 digits";

    final var account = Account.from("12345"); // Invalid length

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> account.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }
}
