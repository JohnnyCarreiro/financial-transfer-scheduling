
package com.johnnycarreiro.fts.infra.transfer.models;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferRequest(
    @NotBlank(message = "Source account cannot be blank.") String sourceAccount,

    @NotBlank(message = "Destination account cannot be blank.") String destinationAccount,

    @NotNull(message = "Amount cannot be null.") Double amount,

    @NotBlank(message = "Scheduled date cannot be blank.") String scheduledDate) {
  // Método para converter TransferRequest em CreateTransferCommand
  public CreateTransferCommand toCommand() {
    return CreateTransferCommand.of(sourceAccount, destinationAccount, amount, scheduledDate);
  }
}
