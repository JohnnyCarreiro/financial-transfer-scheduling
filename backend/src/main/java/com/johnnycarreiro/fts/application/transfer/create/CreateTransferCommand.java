package com.johnnycarreiro.fts.application.transfer.create;

import java.time.Instant;
import java.util.UUID;

/**
 * Command object for creating a new Transfer.
 */
public record CreateTransferCommand(
    String sourceAccount,
    String destinationAccount,
    double amount,
    String scheduledDate) {
  /**
   * Factory method to create a new CreateTransferCommand from String IDs for
   * source and destination accounts.
   *
   * @param sourceAccountStr      The source account UUID as a String.
   * @param destinationAccountStr The destination account UUID as a String.
   * @param amount                The transfer amount.
   * @param transferType          The type of transfer (e.g., immediate or
   *                              scheduled).
   * @param scheduledDate         The scheduled transfer date (ISO-8601 string).
   * @return a new instance of CreateTransferCommand.
   */
  public static CreateTransferCommand of(
      final String sourceAccountStr,
      final String destinationAccountStr,
      final double amount,
      final String scheduledDate) {
    return new CreateTransferCommand(sourceAccountStr, destinationAccountStr, amount, scheduledDate);
  }
}
