package com.johnnycarreiro.fts.unitary.domain.entities;

import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.ThrowsValidationHandler;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;

@DisplayName("Transfer Test Suite")
public class TransferTest {
  @Test
  @DisplayName("Valid - Instantiate Transfer")
  public void givenValidParams_whenCallCreateANewTransfer_thenInstantiateNewTransfer() {
    final var expectedSourceAccount = Account.from("0123456789");
    final var expectedDestinationAccount = Account.from("9876543210");
    final var expectedAmount = 200.0;
    final var expectedScheduledDate = Instant.now().toString();

    final var sut = Transfer.create(expectedSourceAccount, expectedDestinationAccount, expectedAmount,
        expectedScheduledDate);

    System.out.println(sut);

    Assertions.assertNotNull(sut);
    Assertions.assertEquals(expectedSourceAccount, sut.getSourceAccount());
    Assertions.assertEquals(expectedDestinationAccount, sut.getDestinationAccount());
    Assertions.assertEquals(BigDecimal.valueOf(expectedAmount), sut.getAmount());
    Assertions.assertEquals(Instant.parse(expectedScheduledDate), sut.getScheduledDate());
  }

  @Test
  @DisplayName("Null Scheduled Date - Throws an Exception")
  public void givenNullScheduledDate_whenCallsCreateANewTransferValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Scheduled date cannot be null";

    final var expectedSourceAccount = Account.from("0123456789");
    final var expectedDestinationAccount = Account.from("9876543210");
    final var expectedAmount = 200.0;
    final var actualTransfer = Transfer.create(expectedSourceAccount, expectedDestinationAccount, expectedAmount,
        null);

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> actualTransfer.validate(new ThrowsValidationHandler()));
    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

  @Test
  @DisplayName("Past Scheduled Date - Throws an Exception")
  public void givenPastScheduledDate_whenCallsCreateANewTransferValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Scheduled date cannot be in the past";

    final var expectedSourceAccount = Account.from("0123456789");
    final var expectedDestinationAccount = Account.from("9876543210");
    final var expectedAmount = 200.0;
    final var pastDate = Instant.now().minusSeconds(60 * 60 * 24).toString(); // one day before today

    final var actualTransfer = Transfer.create(expectedSourceAccount, expectedDestinationAccount, expectedAmount,
        pastDate);

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> actualTransfer.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

}
