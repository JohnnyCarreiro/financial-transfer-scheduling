package com.johnnycarreiro.fts.infra.transfer.persistence;

import java.math.BigDecimal;
import java.time.Instant;

import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transfer_fees")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferFeeEntity {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "min_days", nullable = false)
  private int minDays;

  @Column(name = "max_days")
  private Integer maxDays;

  @Column(name = "fixed_fee", nullable = false)
  private BigDecimal fixedFee;

  @Column(name = "percentage_fee", nullable = false)
  private BigDecimal percentageFee;

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant updatedAt;

  @Column(name = "deleted_at", nullable = true, columnDefinition = "TIMESTAMP")
  private Instant deletedAt;

  @PrePersist
  public void prePersist() {
    Instant now = Instant.now();
    if (createdAt == null) {
      this.createdAt = now;
    }
    if (updatedAt == null) {
      this.updatedAt = now;
    }
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }

  public static TransferFeeEntity fromDomain(TransferFee transferFee) {
    var entity = new TransferFeeEntity();
    entity.id = transferFee.getId().getValue();
    entity.name = transferFee.getName();
    entity.minDays = transferFee.getMinDays();
    entity.maxDays = transferFee.getMaxDays();
    entity.fixedFee = transferFee.getFixedFee();
    entity.percentageFee = transferFee.getPercentageFee();
    entity.createdAt = transferFee.getCreatedAt();
    entity.updatedAt = transferFee.getUpdatedAt();
    entity.deletedAt = transferFee.getDeletedAt();
    return entity;
  }

  public TransferFee toDomain() {
    return TransferFee.from(id, name, minDays, maxDays, fixedFee, percentageFee, createdAt, updatedAt, deletedAt);
  }
}
