package com.johnnycarreiro.fts.domain.entities.transfer;

import com.johnnycarreiro.fts.core.domain.AggregateRoot;
import com.johnnycarreiro.fts.core.domain.EntityId;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
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
   * The date when the transfer is scheduled.
   */
  private Instant transferDate;

  /**
   * The transfer fee for the transfer.
   */
  private TransferFee transferFee;

  /**
   * The fixed fee for the transfer.
   */
  private BigDecimal fixedFee;

  /**
   * The percentage fee for the transfer.
   */
  private BigDecimal percentageFee;

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
   * @param scheduledDate      When the transfer was scheduled transfer date.
   * @param transdferDate      When the transfer is scheduled.
   * @param transferFee        The transfer fee for the transfer.
   * @param fixedFee           The fixed fee for the transfer.
   * @param percentageFee      The percentage fee for the transfer.
   * @param status             The status of the transfer.
   * @param createdAt          The creation date of the transfer.
   * @param updatedAt          The last update date of the transfer.
   * @param deletedAt          The deletion date of the transfer (if any).
   */
  private Transfer(
      final String id,
      final Account sourceAccount,
      final Account destinationAccount,
      final BigDecimal amount,
      final Instant scheduledDate,
      final Instant transdferDate,
      final TransferFee transferFee,
      final BigDecimal fixedFee,
      final BigDecimal percentageFee,
      final TransferStatus status,
      final Instant createdAt,
      final Instant updatedAt,
      final Instant deletedAt) {
    super(EntityId.from(id), createdAt, updatedAt, deletedAt);
    this.sourceAccount = sourceAccount;
    this.destinationAccount = destinationAccount;
    this.amount = amount;
    this.scheduledDate = scheduledDate;
    this.transferDate = transdferDate;
    this.transferFee = transferFee;
    this.fixedFee = fixedFee;
    this.percentageFee = percentageFee;
    this.status = status;
  }

  /**
   * Factory method to create a new Transfer.
   *
   * @param sourceAccount      The source account.
   * @param destinationAccount The destination account.
   * @param amount             The transfer amount.
   * @param schedluedDate      The scheduled transfer date (ISO-8601 string).
   * @param transferFee        The transfer fee for the transfer.
   * 
   * @return A new Transfer instance.
   */
  public static Transfer create(
      final Account sourceAccount,
      final Account destinationAccount,
      final double amount,
      final String schedluedDate,
      final TransferFee transferFee) {
    return new Transfer(
        EntityId.create().getValue(),
        sourceAccount,
        destinationAccount,
        BigDecimal.valueOf(amount),
        schedluedDate != null ? Instant.parse(schedluedDate) : null,
        Instant.now(),
        transferFee,
        transferFee.getFixedFee(),
        transferFee.getPercentageFee(),
        TransferStatus.from(Status.SCHEDULED),
        Instant.now(),
        Instant.now(),
        null);
  }

  /**
   * Factory method to recreate a Transfer entity from its properties.
   *
   * @param sourceAccount      The source account ID.
   * @param destinationAccount The destination account ID.
   * @param amount             The transfer amount.
   * @param scheduledDate      When the transfer was scheduled transfer date.
   * @param transferDate       When the transfer is scheduled.
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
      Instant scheduledDate,
      Instant transferDate,
      TransferFee transferFee,
      BigDecimal fixedFee,
      BigDecimal percentageFee,
      String status,
      String createdAt,
      String updatedAt,
      String deletedAt) {
    return new Transfer(
        EntityId.create().getValue(),
        Account.from(sourceAccount),
        Account.from(destinationAccount),
        BigDecimal.valueOf(amount),
        scheduledDate,
        transferDate,
        transferFee,
        fixedFee,
        percentageFee,
        TransferStatus.fromString(status),
        Instant.parse(createdAt),
        Instant.parse(updatedAt),
        deletedAt != null ? Instant.parse(deletedAt) : null);
  }

  /**
   * Updates the amount and scheduled date of the transfer.
   *
   * @param amount       The new transfer amount.
   * @param transferDate The new scheduled date.
   * @param transferFee  The new transfer fee.
   * @return The updated Transfer instance.
   */
  public Transfer update(final BigDecimal amount, final Instant transferDate, final TransferFee transferFee) {
    this.amount = amount;
    this.transferDate = transferDate;
    this.setUpdatedAt(Instant.now());
    this.transferFee = transferFee;
    this.fixedFee = transferFee.getFixedFee();
    this.percentageFee = transferFee.getPercentageFee();
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
        Objects.equals(scheduledDate, transfer.scheduledDate) &&
        Objects.equals(transferFee, transfer.transferFee) &&
        Objects.equals(fixedFee, transfer.fixedFee) &&
        Objects.equals(percentageFee, transfer.percentageFee) &&
        Objects.equals(transferDate, transfer.transferDate) && super.equals(o);
  }

  /**
   * Generates a hash code based on source account, destination account, amount,
   * and scheduled date.
   */
  @Override
  public int hashCode() {
    return Objects.hash(sourceAccount, destinationAccount, amount, scheduledDate, transferDate, transferFee, fixedFee,
        percentageFee, status,
        super.hashCode());
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
        ", transferDate=" + transferDate +
        ", transferFee=" + transferFee.toString() +
        ", fixedFee=" + fixedFee +
        ", percentageFee=" + percentageFee +
        ", status=" + status +
        super.toString() +
        '}';
  }
}
