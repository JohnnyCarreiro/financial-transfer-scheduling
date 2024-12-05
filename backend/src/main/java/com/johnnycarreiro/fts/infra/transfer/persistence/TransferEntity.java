package com.johnnycarreiro.fts.infra.transfer.persistence;

import java.math.BigDecimal;
import java.time.Instant;

import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;
import com.johnnycarreiro.fts.domain.value_objects.account.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transfers")
public class TransferEntity {

  @Id
  private String id;

  private String sourceAccount;

  private String destinationAccount;

  private BigDecimal amount;

  private Instant scheduledDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transfer_fee_id", referencedColumnName = "id")
  private TransferFeeEntity transferFee;

  // Getters and Setters

  public static TransferEntity fromDomain(Transfer transfer) {
    TransferEntity entity = new TransferEntity();
    entity.id = transfer.getId().getValue();
    entity.sourceAccount = transfer.getSourceAccount().getValue();
    entity.destinationAccount = transfer.getDestinationAccount().getValue();
    entity.amount = transfer.getAmount();
    entity.scheduledDate = transfer.getScheduledDate();
    if (transfer.getTransferFee() != null) {
      entity.transferFee = TransferFeeEntity.fromDomain(transfer.getTransferFee());
    }
    return entity;
  }

  public Transfer toDomain() {
    TransferFee fee = transferFee != null ? transferFee.toDomain() : null;
    return Transfer.create(
        Account.create(sourceAccount),
        Account.create(destinationAccount),
        amount.doubleValue(),
        scheduledDate,
        fee);
  }
}
