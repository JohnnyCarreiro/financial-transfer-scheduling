package com.johnnycarreiro.fts.infra.transfer.models;

import java.util.Map;

import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;

public class TransferPresenter {

  public static Map<String, Object> present(Transfer transfer) {
    return Map.of(
        "id", transfer.getId().getValue(),
        "sourceAccount", transfer.getSourceAccount().getValue(),
        "destinationAccount", transfer.getDestinationAccount().getValue(),
        "amount", transfer.getAmount(),
        "scheduledDate", transfer.getScheduledDate(),
        "transferDate", transfer.getTransferDate(),
        "status", transfer.getStatus(),
        "fee", Map.of(
            "fixedFee", transfer.getFixedFee(),
            "percentageFee", transfer.getPercentageFee()),
        "createdAt", transfer.getCreatedAt(),
        "updatedAt", transfer.getUpdatedAt());
  }
}
