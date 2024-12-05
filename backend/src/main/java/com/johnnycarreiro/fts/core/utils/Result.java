package com.johnnycarreiro.fts.core.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A Result type that encapsulates either a success value or an error.
 * Inspired by Rust's Result<T, E>.
 *
 * @param <T> the type of the success value
 * @param <E> the type of the error value
 */
public class Result<T, E> {
  private final T success;
  private final E error;

  private Result(T success, E error) {
    this.success = success;
    this.error = error;
  }

  // Static factory methods

  /**
   * Creates a successful Result containing the given value.
   *
   * @param value the success value
   * @param <T>   the type of the success value
   * @param <E>   the type of the error value
   * @return a Result representing success
   */
  public static <T, E> Result<T, E> success(T value) {
    return new Result<>(value, null);
  }

  /**
   * Creates an error Result containing the given value.
   *
   * @param value the error value
   * @param <T>   the type of the success value
   * @param <E>   the type of the error value
   * @return a Result representing an error
   */
  public static <T, E> Result<T, E> error(E value) {
    return new Result<>(null, value);
  }

  // Query methods

  /**
   * Checks if the Result is a success.
   *
   * @return true if success, false otherwise
   */
  public boolean isSuccess() {
    return success != null;
  }

  /**
   * Checks if the Result is an error.
   *
   * @return true if error, false otherwise
   */
  public boolean isError() {
    return error != null;
  }

  // Value access methods

  /**
   * Gets the success value, if present.
   *
   * @return The success value, or empty if not present
   * @throws IllegalStateException if the result is an error
   */
  public T getSuccess() {
    if (isError()) {
      throw new IllegalStateException("Cannot get success value when result is an error");
    }
    return success;
  }

  /**
   * Gets the error value, if present.
   *
   * @return The error value, or empty if not present
   * @throws IllegalStateException if the result is a success
   */
  public E getError() {
    if (isSuccess()) {
      throw new IllegalStateException("Cannot get error value when result is a success");
    }
    return error;
  }
  // Functional API

  /**
   * Applies the given function to the success value, if present, and returns a
   * new Result.
   *
   * @param mapper the function to apply to the success value
   * @param <U>    the type of the new success value
   * @return a new Result with the mapped success value, or the same error
   */
  public <U> Result<U, E> mapSuccess(Function<T, U> mapper) {
    if (isSuccess()) {
      return Result.success(mapper.apply(success));
    }
    return Result.error(error);
  }

  /**
   * Applies the given function to the error value, if present, and returns a new
   * Result.
   *
   * @param mapper the function to apply to the error value
   * @param <F>    the type of the new error value
   * @return a new Result with the same success or mapped error value
   */
  public <F> Result<T, F> mapError(Function<E, F> mapper) {
    if (isError()) {
      return Result.error(mapper.apply(error));
    }
    return Result.success(success);
  }

  /**
   * Consumes the Result by executing the given consumer for success or error.
   *
   * @param onSuccess the consumer for success values
   * @param onError   the consumer for error values
   */
  public void ifPresent(Consumer<T> onSuccess, Consumer<E> onError) {
    if (isSuccess()) {
      onSuccess.accept(success);
    } else {
      onError.accept(error);
    }
  }

  /**
   * Unwraps the success value or throws the error.
   *
   * @return the success value
   * @throws RuntimeException if the result is an error
   */
  public T unwrap() {
    if (isError()) {
      throw new IllegalStateException("Unwrapped an error: " + error);
    }
    return success;
  }
}
