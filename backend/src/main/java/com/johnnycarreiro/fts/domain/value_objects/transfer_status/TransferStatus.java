package com.johnnycarreiro.fts.domain.value_objects.transfer_status;

import com.johnnycarreiro.fts.core.domain.ValueObject;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;

import java.util.Objects;

/**
 * Represents the transfer status as a value object.
 * This class ensures that the transfer status is valid and immutable.
 */
public class TransferStatus extends ValueObject<Status> {

  /** The current status value of the transfer. */
  private final Status value;

  /**
   * Private constructor to ensure controlled instantiation.
   *
   * @param value the transfer status value.
   */
  private TransferStatus(final Status value) {
    this.value = value;
  }

  /**
   * Validates the current transfer status using {@link TransferStatusValidator}.
   *
   * @param handler the {@link ValidationHandler} to collect validation errors.
   */
  @Override
  public void validate(final ValidationHandler handler) {
    new TransferStatusValidator(this, handler).validate();
  }

  /**
   * Factory method to create a new TransferStatus instance from a {@link Status}.
   *
   * @param value the transfer status.
   * @return a new instance of {@link TransferStatus}.
   */
  public static TransferStatus from(final Status value) {
    return new TransferStatus(value);
  }

  /**
   * Factory method to create a TransferStatus instance from a string
   * representation.
   * Converts the string into a {@link Status} before creating the instance.
   *
   * @param value the string representation of the transfer status.
   * @return a new instance of {@link TransferStatus}.
   * @throws IllegalArgumentException if the string does not match a valid
   *                                  {@link Status}.
   */
  public static TransferStatus fromString(final String value) {
    return new TransferStatus(Status.fromString(value));
  }

  /**
   * Retrieves the current value of the transfer status.
   *
   * @return the {@link Status} value.
   */
  @Override
  public Status getValue() {
    return value;
  }

  /**
   * Checks equality between this and another object.
   *
   * @param o the object to compare with.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TransferStatus that = (TransferStatus) o;
    return value == that.value;
  }

  /**
   * Generates a hash code for the transfer status.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  /**
   * Converts the transfer status to its string representation.
   *
   * @return the string representation of the current {@link Status}.
   */
  @Override
  public String toString() {
    return value.getValue();
  }
}
