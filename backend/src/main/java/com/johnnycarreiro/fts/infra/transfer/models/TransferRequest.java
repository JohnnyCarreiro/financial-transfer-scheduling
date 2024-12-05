package com.johnnycarreiro.fts.infra.transfer.models;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequest {

  @NotBlank
  private String sourceAccount;
  @NotBlank
  private String destinationAccount;
  @NotNull
  private double amount;
  @NotNull
  private String scheduledDate;

  // Getters e Setters

  public CreateTransferCommand toCommand() {
    return CreateTransferCommand.of(sourceAccount, destinationAccount, amount, scheduledDate);
  }
}
