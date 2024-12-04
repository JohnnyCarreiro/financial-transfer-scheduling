package com.johnnycarreiro.fts.core.domain;

import java.time.Instant;

public abstract class AggregateRoot<IDType, ID extends Identifier<IDType>> extends Entity<IDType, ID> {
  protected AggregateRoot(final ID id, final Instant createAt, final Instant updatedAt, Instant deletedAt) {
    super(id, createAt, updatedAt, deletedAt);
  }
}
