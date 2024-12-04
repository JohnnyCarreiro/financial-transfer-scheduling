package com.johnnycarreiro.fts.domain.value_objects.transfer_status;

public enum Status {
  SCHEDULED("scheduled"),
  COMPLETED("completed"),
  CANCELLED("cancelled"),
  FAILED("failed");

  private final String value;

  Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Status fromString(String value) {
    for (Status status : Status.values()) {
      if (status.getValue().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid TransferStatus value: " + value + " valid values are: "
        + Status.SCHEDULED.getValue() + ", " + Status.COMPLETED.getValue() + ", " + Status.CANCELLED.getValue() + ", "
        + Status.FAILED.getValue());
  }
}
