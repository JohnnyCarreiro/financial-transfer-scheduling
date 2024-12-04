package com.johnnycarreiro.fts.domain.value_objects.account;

import com.johnnycarreiro.fts.core.domain.ValueObject;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;

import java.util.Objects;

/**
 * Represents a bank account as a value object.
 * Immutable and validated upon creation.
 */
public class Account extends ValueObject<String> {
  /** The account number associated with this account. */
  private final String accountNumber;

  /**
   * Private constructor to ensure immutability and controlled instantiation.
   *
   * @param accountNumber the account number.
   */
  private Account(final String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Factory method to create a new Account instance.
   *
   * @param accountNumber the account number.
   * @return a new instance of {@link Account}.
   */
  public static Account create(final String accountNumber) {
    return new Account(accountNumber);
  }

  /**
   * Factory method to recreate an Account instance.
   * Used for rehydration from persistence.
   *
   * @param accountNumber the account number.
   * @return an instance of {@link Account}.
   */
  public static Account from(final String accountNumber) {
    return new Account(accountNumber);
  }

  /**
   * Validates the account object.
   * Uses {@link AccountValidator} for validation rules.
   *
   * @param handler the {@link ValidationHandler} to collect validation errors.
   */
  @Override
  public void validate(final ValidationHandler handler) {
    new AccountValidator(this, handler).validate();
  }

  /**
   * Retrieves the account number.
   *
   * @return the account number as a {@link String}.
   */
  @Override
  public String getValue() {
    return accountNumber;
  }

  /**
   * Compares this account with another object for equality.
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
    Account account = (Account) o;
    return Objects.equals(accountNumber, account.accountNumber);
  }

  /**
   * Generates a hash code for this account.
   *
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(accountNumber);
  }

  /**
   * Converts the account object to a string representation.
   *
   * @return the account number as a string.
   */
  @Override
  public String toString() {
    return accountNumber;
  }
}
