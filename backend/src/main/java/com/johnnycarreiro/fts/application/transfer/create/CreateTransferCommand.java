package com.johnnycarreiro.fts.application.transfer.create;

import java.time.Instant;
import java.util.UUID;

/**
 * Command object for creating a new Transfer.
 */
public record CreateTransferCommand(
    UUID sourceAccount,
    UUID destinationAccount,
    double amount,
    Instant scheduledDate) {
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
    // Convert the String IDs to UUID
    UUID sourceAccount = UUID.fromString(sourceAccountStr);
    UUID destinationAccount = UUID.fromString(destinationAccountStr);
    Instant scheduledDateParsed = Instant.parse(scheduledDate); // Convert ISO-8601 string to Instant

    return new CreateTransferCommand(sourceAccount, destinationAccount, amount, scheduledDateParsed);
  }
}
