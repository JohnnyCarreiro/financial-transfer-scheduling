package com.johnnycarreiro.fts.domain.entities.transfer_fee;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.johnnycarreiro.fts.core.domain.Entity;
import com.johnnycarreiro.fts.core.domain.EntityId;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;

import lombok.Getter;

@Getter
public class TransferFee extends Entity<String, EntityId> {
  private final String name;
  private final int minDays;
  private final Integer maxDays;
  private final BigDecimal fixedFee;
  private final BigDecimal percentageFee;

  private TransferFee(
      final String id,
      final String name,
      final int minDays,
      final Integer maxDays,
      final BigDecimal fixedFee,
      final BigDecimal percentageFee,
      final Instant createdAt,
      final Instant updatedAt,
      final Instant deletedAt) {
    super(EntityId.from(id), createdAt, updatedAt, deletedAt);
    this.name = name;
    this.minDays = minDays;
    this.maxDays = maxDays;
    this.fixedFee = fixedFee;
    this.percentageFee = percentageFee;
  }

  public static TransferFee create(
      final String name,
      final int minDays,
      final Integer maxDays,
      final BigDecimal fixedFee,
      final BigDecimal percentageFee) {
    return new TransferFee(
        EntityId.create().getValue(),
        name,
        minDays,
        maxDays,
        fixedFee,
        percentageFee,
        Instant.now(),
        Instant.now(),
        null);
  }

  public static TransferFee from(
      final String id,
      final String name,
      final int minDays,
      final Integer maxDays,
      final BigDecimal fixedFee,
      final BigDecimal percentageFee,
      final Instant createdAt,
      final Instant updatedAt,
      final Instant deletedAt) {
    return new TransferFee(
        id,
        name,
        minDays,
        maxDays,
        fixedFee,
        percentageFee,
        createdAt,
        updatedAt,
        deletedAt);
  }

  @Override
  public void validate(ValidationHandler handler) {
    new TransferFeeValidator(this, handler).validate();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TransferFee that = (TransferFee) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(minDays, that.minDays) &&
        Objects.equals(maxDays, that.maxDays) &&
        Objects.equals(fixedFee, that.fixedFee) &&
        Objects.equals(percentageFee, that.percentageFee) &&
        super.equals(o);
  }

  @Override
  public String toString() {
    return "TransferFee{" +
        "name='" + name + '\'' +
        ", minDays=" + minDays +
        ", maxDays=" + maxDays +
        ", fixedFee=" + fixedFee +
        ", percentageFee=" + percentageFee +
        super.toString() +
        '}';
  }
}
