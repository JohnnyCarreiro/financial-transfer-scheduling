package com.johnnycarreiro.fts.domain.entities.transfer;

import com.johnnycarreiro.fts.core.domain.AggregateRoot;
import com.johnnycarreiro.fts.core.domain.EntityId;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Represents a Transfer Entity.
 */
@Getter
public class Transfer extends AggregateRoot<String, EntityId> {

  /**
   * The source account initiating the transfer.
   */
  private final Account sourceAccount;

  /**
   * The destination account receiving the transfer.
   */
  private final Account destinationAccount;

  /**
   * The amount of money being transferred.
   */
  private BigDecimal amount;

  /**
   * The date when the transfer is scheduled.
   */
  private Instant scheduledDate;

  /**
   * The date when the transfer was created.
   */
  private final Instant createdAt;

  /**
   * The current status of the transfer.
   */
  private TransferStatus status;

  /**
   * Private constructor for the Transfer entity.
   * Use factory methods for object instantiation.
   *
   * @param id                 The unique identifier of the transfer.
   * @param sourceAccount      The source account.
   * @param destinationAccount The destination account.
   * @param amount             The transfer amount.
   * @param scheduledDate      The scheduled transfer date.
   * @param createdAt          The creation date of the transfer.
   * @param updatedAt          The last update date of the transfer.
   * @param deletedAt          The deletion date of the transfer (if any).
   * @param status             The status of the transfer.
   */
  private Transfer(
      final String id,
      final Account sourceAccount,
      final Account destinationAccount,
      final BigDecimal amount,
      final Instant scheduledDate,
      final Instant createdAt,
      final Instant updatedAt,
      final Instant deletedAt,
      final TransferStatus status) {
    super(EntityId.from(id), createdAt, updatedAt, deletedAt);
    this.sourceAccount = sourceAccount;
    this.destinationAccount = destinationAccount;
    this.amount = amount;
    this.scheduledDate = scheduledDate;
    this.createdAt = createdAt;
    this.status = status;
  }

  /**
   * Factory method to create a new Transfer.
   *
   * @param sourceAccount      The source account.
   * @param destinationAccount The destination account.
   * @param amount             The transfer amount.
   * @param scheduledDate      The scheduled transfer date (ISO-8601 string).
   * @return A new Transfer instance.
   */
  public static Transfer create(
      final Account sourceAccount,
      final Account destinationAccount,
      final double amount,
      final String scheduledDate) {
    return new Transfer(
        EntityId.create().getValue(),
        sourceAccount,
        destinationAccount,
        BigDecimal.valueOf(amount),
        scheduledDate != null ? Instant.parse(scheduledDate) : null,
        Instant.now(),
        Instant.now(),
        null,
        TransferStatus.from(Status.SCHEDULED));
  }

  /**
   * Factory method to recreate a Transfer entity from its properties.
   *
   * @param sourceAccount      The source account ID.
   * @param destinationAccount The destination account ID.
   * @param amount             The transfer amount.
   * @param scheduledDate      The scheduled transfer date (ISO-8601 string).
   * @param createdAt          The creation date (ISO-8601 string).
   * @param updatedAt          The last update date (ISO-8601 string).
   * @param deletedAt          The deletion date (ISO-8601 string, optional).
   * @param status             The transfer status as a string.
   * @return A Transfer instance.
   */
  public static Transfer from(
      String sourceAccount,
      String destinationAccount,
      double amount,
      String scheduledDate,
      String createdAt,
      String updatedAt,
      String deletedAt,
      String status) {
    return new Transfer(
        EntityId.create().getValue(),
        Account.from(sourceAccount),
        Account.from(destinationAccount),
        BigDecimal.valueOf(amount),
        Instant.parse(scheduledDate),
        Instant.parse(createdAt),
        Instant.parse(updatedAt),
        deletedAt != null ? Instant.parse(deletedAt) : null,
        TransferStatus.fromString(status));
  }

  /**
   * Updates the amount and scheduled date of the transfer.
   *
   * @param amount        The new transfer amount.
   * @param scheduledDate The new scheduled date.
   * @return The updated Transfer instance.
   */
  public Transfer update(final BigDecimal amount, final Instant scheduledDate) {
    this.amount = amount;
    this.scheduledDate = scheduledDate;
    this.setUpdatedAt(Instant.now());
    return this;
  }

  /**
   * Marks the transfer as deleted.
   *
   * @return The updated Transfer instance.
   */
  public Transfer delete() {
    this.setDeletedAt(Instant.now());
    this.setUpdatedAt(Instant.now());
    return this;
  }

  /**
   * Restores a previously deleted transfer.
   *
   * @return The restored Transfer instance.
   */
  public Transfer restore() {
    this.setDeletedAt(null);
    this.setUpdatedAt(Instant.now());
    return this;
  }

  /**
   * Validates the Transfer entity using a given ValidationHandler.
   *
   * @param handler The ValidationHandler instance.
   */
  @Override
  public void validate(final ValidationHandler handler) {
    new TransferValidator(this, handler).validate();
  }

  /**
   * Checks equality based on source account, destination account, amount, and
   * scheduled date.
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Transfer transfer = (Transfer) o;
    return Objects.equals(sourceAccount, transfer.sourceAccount) &&
        Objects.equals(destinationAccount, transfer.destinationAccount) &&
        Objects.equals(amount, transfer.amount) &&
        Objects.equals(scheduledDate, transfer.scheduledDate);
  }

  /**
   * Generates a hash code based on source account, destination account, amount,
   * and scheduled date.
   */
  @Override
  public int hashCode() {
    return Objects.hash(sourceAccount, destinationAccount, amount, scheduledDate);
  }

  /**
   * Provides a string representation of the Transfer entity.
   */
  @Override
  public String toString() {
    return "Transfer{" +
        "sourceAccount=" + sourceAccount +
        ", destinationAccount=" + destinationAccount +
        ", amount=" + amount +
        ", scheduledDate=" + scheduledDate +
        ", createdAt=" + createdAt +
        ", status=" + status +
        '}';
  }
}
