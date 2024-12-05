package com.johnnycarreiro.fts.unitary.domain.services;

import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;
import com.johnnycarreiro.fts.domain.value_objects.transfer_fee.TransferFee;

@DisplayName("Transfer Fee Test Suite")
public class TransferFeeCalculatorServiceTest {
  @Test
  @DisplayName("Valid - Instantiate Transfer")
  void givenValidDates_whenCalculateFee_thenReturnsCorrectFee() {
    var fees = List.of(
        TransferFee.create("Mesmo Dia", 0, 0, new BigDecimal("3.00"), new BigDecimal("0.025")),
        TransferFee.create("De 1 a 10 dias", 1, 10, new BigDecimal("12.00"), new BigDecimal("0.0")),
        TransferFee.create("De 11 a 20 dias", 11, 20, new BigDecimal("0.00"), new BigDecimal("0.082")),
        TransferFee.create("De 21 a 30 dias", 21, 30, new BigDecimal("0.00"), new BigDecimal("0.069")),
        TransferFee.create("De 31 a 40 dias", 31, 40, new BigDecimal("0.00"), new BigDecimal("0.047")),
        TransferFee.create("De 41 a 50 dias", 41, 50, new BigDecimal("0.00"), new BigDecimal("0.017")));
    TransferFeeCalculatorService service = new TransferFeeCalculatorService(fees);

    var scheduledDateAt = Instant.parse("2024-12-01T10:00:00Z");
    var scheduledDateFor = Instant.parse("2024-12-11T10:00:00Z");

    TransferFee fee = service.calculateFee(scheduledDateAt, scheduledDateFor);

    Assertions.assertEquals(new BigDecimal("12.00"), fee.getFixedFee());
    Assertions.assertEquals(new BigDecimal("0.0"), fee.getPercentageFee());
  }
}
