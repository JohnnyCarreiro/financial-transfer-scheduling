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
        "fee", Map.of(
            "fixedFee", transfer.getTransferFee().getFixedFee(),
            "percentageFee", transfer.getTransferFee().getPercentageFee()));
  }
}
