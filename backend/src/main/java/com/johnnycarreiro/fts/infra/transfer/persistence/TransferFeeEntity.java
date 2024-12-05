package com.johnnycarreiro.fts.infra.transfer.persistence;

import java.math.BigDecimal;

import com.johnnycarreiro.fts.domain.entities.transfer_fee.TransferFee;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transfer_fees")
public class TransferFeeEntity {

  @Id
  private String id;
  private String name;
  private int minDays;
  private Integer maxDays;
  private BigDecimal fixedFee;
  private BigDecimal percentageFee;

  // Getters and Setters

  public static TransferFeeEntity fromDomain(TransferFee transferFee) {
    var entity = new TransferFeeEntity();
    entity.id = transferFee.getId().getValue();
    entity.name = transferFee.getName();
    entity.minDays = transferFee.getMinDays();
    entity.maxDays = transferFee.getMaxDays();
    entity.fixedFee = transferFee.getFixedFee();
    entity.percentageFee = transferFee.getPercentageFee();
    return entity;
  }

  public TransferFee toDomain() {
    return TransferFee.create(name, minDays, maxDays, fixedFee, percentageFee);
  }
}
