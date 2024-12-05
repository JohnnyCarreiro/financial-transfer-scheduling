package com.johnnycarreiro.fts.unitary.domain.value_objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.ThrowsValidationHandler;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.TransferStatus;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.Status;

@DisplayName("TransferStatus Test Suite")
public class TransferStatusTest {

  @Test
  @DisplayName("Valid - Create TransferStatus")
  public void givenValidTransferStatus_whenCallCreate_thenInstantiateNewTransferStatus() {
    final var expectedValue = Status.COMPLETED;

    final var transferStatus = TransferStatus.from(expectedValue);

    Assertions.assertNotNull(transferStatus);
    Assertions.assertEquals(expectedValue, transferStatus.getValue());
  }

  @Test
  @DisplayName("Null TransferStatus - Throws an Exception")
  public void givenNullTransferStatus_whenCallCreate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Transfer status cannot be null";

    final var transferStatus = TransferStatus.from(null);

    final var sut = Assertions.assertThrows(
        DomainException.class, () -> transferStatus.validate(new ThrowsValidationHandler()));

    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

  @Test
  @DisplayName("Invalid TransferStatus - Throws IllegalArgumentException")
  public void givenInvalidTransferStatus_whenCallCreate_thenThrows() {
    final var invalidValue = "INVALID_STATUS";
    final var expectedErrorMessage = "Invalid TransferStatus value: INVALID_STATUS valid values are: "
        + Status.SCHEDULED.getValue() + ", " + Status.COMPLETED.getValue() + ", " + Status.CANCELLED.getValue() + ", "
        + Status.FAILED.getValue();

    final var sut = Assertions.assertThrows(
        IllegalArgumentException.class, () -> TransferStatus.fromString(invalidValue));

    Assertions.assertEquals(expectedErrorMessage, sut.getMessage());
  }
}
