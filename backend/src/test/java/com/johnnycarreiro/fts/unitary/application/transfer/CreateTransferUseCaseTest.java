package com.johnnycarreiro.fts.unitary.application.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferCommand;
import com.johnnycarreiro.fts.application.transfer.create.CreateTransferUseCase;
import com.johnnycarreiro.fts.core.domain.exceptions.DomainException;
import com.johnnycarreiro.fts.core.utils.Result;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer.TransferRepository;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@DisplayName("Create Transfer Use Case Test Suite")
public class CreateTransferUseCaseTest {
  @Test()
  @DisplayName("Valid Command - Create New Transfer")
  public void givenValidCommand_whenCallExecute_thenCreateANewTransfer() {
    final var sourceAccount = UUID.randomUUID();
    final var destinationAccount = UUID.randomUUID();
    final var amount = 100.50;
    final var scheduledDate = Instant.now();
    final var aCommand = CreateTransferCommand.of(sourceAccount.toString(), destinationAccount.toString(), amount,
        scheduledDate.toString());
    final TransferRepository transferRepository = Mockito.mock(TransferRepository.class);
    final TransferFeeCalculatorService feeCalculatorService = Mockito.mock(TransferFeeCalculatorService.class);
    var transferFees = List.of(
        TransferFee.create("Mesmo Dia", 0, 0, new BigDecimal("3.00"), new BigDecimal("0.025")),
        TransferFee.create("De 1 a 10 dias", 1, 10, new BigDecimal("12.00"), new BigDecimal("0.0")),
        TransferFee.create("De 11 a 20 dias", 11, 20, new BigDecimal("0.00"), new BigDecimal("0.082")),
        TransferFee.create("De 21 a 30 dias", 21, 30, new BigDecimal("0.00"), new BigDecimal("0.069")),
        TransferFee.create("De 31 a 40 dias", 31, 40, new BigDecimal("0.00"), new BigDecimal("0.047")),
        TransferFee.create("De 41 a 50 dias", 41, 50, new BigDecimal("0.00"), new BigDecimal("0.017")));

    Mockito.when(feeCalculatorService.calculateFee(any(), any())).thenReturn(transferFees.get(0));
    Mockito.when(transferRepository.listAllFees()).thenReturn(Result.success(transferFees));
    Mockito.when(transferRepository.save(any())).thenReturn(Result.success(null));

    final var useCase = new CreateTransferUseCase(feeCalculatorService, transferRepository);

    Result<Transfer, DomainException> result = useCase.execute(aCommand);

    Assertions.assertTrue(result.isSuccess());
    Mockito.verify(transferRepository, times(1)).save(Mockito.any());
  }

}
