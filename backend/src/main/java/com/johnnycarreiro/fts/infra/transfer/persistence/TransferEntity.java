package com.johnnycarreiro.fts.infra.transfer.persistence;

import java.math.BigDecimal;
import java.time.Instant;

import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.value_objects.transfer_status.TransferStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transfers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferEntity {

  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "source_account", nullable = false)
  private String sourceAccount;

  @Column(name = "destination_account", nullable = false)
  private String destinationAccount;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "scheduled_date", nullable = false)
  private Instant scheduledDate;

  @Column(name = "transfer_date")
  private Instant transferDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transfer_fee_id", referencedColumnName = "id", nullable = false)
  private TransferFeeEntity transferFee;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Instant updatedAt;

  @Column(name = "deleted_at", nullable = true, columnDefinition = "TIMESTAMP")
  private Instant deletedAt;

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }

  public static TransferEntity fromDomain(Transfer transfer) {
    TransferEntity entity = new TransferEntity();
    entity.id = transfer.getId().getValue();
    entity.sourceAccount = transfer.getSourceAccount().getValue();
    entity.destinationAccount = transfer.getDestinationAccount().getValue();
    entity.amount = transfer.getAmount();
    entity.scheduledDate = transfer.getScheduledDate();
    entity.transferDate = transfer.getTransferDate();

    if (transfer.getTransferFee() != null) {
      entity.transferFee = TransferFeeEntity.fromDomain(transfer.getTransferFee());
    }
    if (transfer.getStatus() != null) {
      entity.status = transfer.getStatus().getValue().getValue();
    } else {
      entity.status = TransferStatus.fromString("scheduled").getValue().getValue();
    }

    entity.createdAt = transfer.getCreatedAt();
    entity.updatedAt = transfer.getUpdatedAt();
    entity.deletedAt = transfer.getDeletedAt();

    return entity;
  }

  public Transfer toDomain() {
    TransferFee fee = transferFee != null ? transferFee.toDomain() : null;
    Transfer transfer = Transfer.from(
        id,
        sourceAccount,
        destinationAccount,
        amount.doubleValue(),
        scheduledDate,
        transferDate,
        fee,
        fee.getFixedFee(),
        fee.getPercentageFee(),
        status,
        createdAt,
        updatedAt,
        deletedAt);

    return transfer;
  }
}
