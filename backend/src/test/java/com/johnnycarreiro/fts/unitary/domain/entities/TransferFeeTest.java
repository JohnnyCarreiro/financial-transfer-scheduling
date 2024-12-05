package com.johnnycarreiro.fts.unitary.domain.entities;

import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.domain.validation.ThrowsValidationHandler;
import com.johnnycarreiro.fts.domain.value_objects.transfer_fee.TransferFee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("Transfer Fee Test Suite")
public class TransferFeeTest {

  @Test
  @DisplayName("Valid - Create a Transfer Fee")
  public void givenValidParams_whenCallCreateNewTransferFee_thenInstantiateNewTransferFee() {
    final String expectedName = "Same Day Transfer";
    final int expectedMinDays = 0;
    final Integer expectedMaxDays = null; // Optional field
    final BigDecimal expectedFixedFee = BigDecimal.valueOf(10.00);
    final BigDecimal expectedPercentageFee = BigDecimal.valueOf(0.01);
    final var transferFee = TransferFee.create(expectedName, expectedMinDays, expectedMaxDays, expectedFixedFee,
        expectedPercentageFee);

    Assertions.assertNotNull(transferFee);
    Assertions.assertEquals(expectedName, transferFee.getName());
    Assertions.assertEquals(expectedMinDays, transferFee.getMinDays());
    Assertions.assertEquals(expectedMaxDays, transferFee.getMaxDays());
    Assertions.assertEquals(expectedFixedFee, transferFee.getFixedFee());
    Assertions.assertEquals(expectedPercentageFee, transferFee.getPercentageFee());
  }

  @Test
  @DisplayName("Negative Min Days - Throws an Exception")
  public void givenNegativeMinDays_whenCallsCreateNewTransferFeeValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Minimum days cannot be negative";
    final var transferFee = TransferFee.create("Same Day Transfer", -1, 0, BigDecimal.ZERO, BigDecimal.ZERO);
    final var sut = Assertions.assertThrows(
        DomainException.class, () -> transferFee.validate(new ThrowsValidationHandler()));
    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

  @Test
  @DisplayName("Max Days Less Than Min Days- Throws an Exception")
  public void givenMaxDaysLessThanMinDays_whenCallsCreateNewTransferFeeValidate_thenThrows() {
    final var expectedErrorCount = 1;
    final var expectedErrorMessage = "Maximum days cannot be less than minimum days";
    final var transferFee = TransferFee.create("Same Day Transfer", 2, 1, BigDecimal.ZERO, BigDecimal.ZERO);
    final var sut = Assertions.assertThrows(
        DomainException.class, () -> transferFee.validate(new ThrowsValidationHandler()));
    Assertions.assertEquals(expectedErrorCount, sut.getErrors().size());
    Assertions.assertEquals(expectedErrorMessage, sut.getErrors().get(0).message());
  }

}
