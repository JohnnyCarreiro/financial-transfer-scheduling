package com.johnnycarreiro.fts.unitary.domain.entities;

import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.ThrowsValidationHandler;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.Status;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.TransferStatus;

@DisplayName("Transfer Test Suite")
public class TransferTest {
  @Test
  @DisplayName("Valid - Instantiate Transfer")
  public void givenValidParams_whenCallCreateANewTransfer_thenInstantiateNewTransfer() {
    final var expectedSourceAccount = Account.from("0123456789");
    final var expectedDestinationAccount = Account.from("9876543210");
    final var expectedAmount = 200.0;
    final var expectedScheduledDate = Instant.now();
    final var expectedTransferDate = Instant.now();
    final var expectedTransferFee = TransferFee.create("Transfer Fee", 0, 0, BigDecimal.valueOf(0.0),
        BigDecimal.valueOf(0.0));
    final var expectedFixedFee = 0.0;
    final var expectedPercentageFee = 0.0;

    final var sut = Transfer.create(expectedSourceAccount, expectedDestinationAccount, expectedAmount,
        expectedScheduledDate, expectedTransferFee);

    Instant expectedTransferDateTruncated = expectedTransferDate.truncatedTo(ChronoUnit.SECONDS);
    Instant actualTransferDateTruncated = sut.getTransferDate().truncatedTo(ChronoUnit.SECONDS);

    Assertions.assertNotNull(sut);
    Assertions.assertEquals(expectedSourceAccount, sut.getSourceAccount());
    Assertions.assertEquals(expectedDestinationAccount, sut.getDestinationAccount());
    Assertions.assertEquals(BigDecimal.valueOf(expectedAmount), sut.getAmount());
    Assertions.assertEquals((expectedScheduledDate), sut.getScheduledDate());
    Assertions.assertEquals(expectedTransferDateTruncated, actualTransferDateTruncated);
    Assertions.assertEquals(expectedTransferFee, sut.getTransferFee());
    Assertions.assertEquals(BigDecimal.valueOf(expectedFixedFee), sut.getFixedFee());
    Assertions.assertEquals(BigDecimal.valueOf(expectedPercentageFee), sut.getPercentageFee());
    Assertions.assertEquals(TransferStatus.from(Status.SCHEDULED), sut.getStatus());
  }

  @Test
  @DisplayName("Null Scheduled Date - Throws an Exception")
  public void givenNullScheduledDate_whenCallsCreateANewTransferValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Scheduled date cannot be null";

    final var sourceAccount = Account.from("0123456789");
    final var destinationAccount = Account.from("9876543210");
    final var amount = 200.0;
    final var transferFee = TransferFee.create("Transfer Fee", 0, 0, BigDecimal.valueOf(0.0),
        BigDecimal.valueOf(0.0));

    final var actualTransfer = Transfer.create(sourceAccount, destinationAccount, amount,
        null, transferFee);

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

    final var sourceAccount = Account.from("0123456789");
    final var destinationAccount = Account.from("9876543210");
    final var amount = 200.0;
    final var pastDate = Instant.now().minusSeconds(60 * 60 * 24); // one day before today
    final var transferFee = TransferFee.create("Transfer Fee", 0, 0, BigDecimal.valueOf(0.0),
        BigDecimal.valueOf(0.0));

    final var actualTransfer = Transfer.create(sourceAccount, destinationAccount, amount,
        pastDate, transferFee);

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> actualTransfer.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

}
