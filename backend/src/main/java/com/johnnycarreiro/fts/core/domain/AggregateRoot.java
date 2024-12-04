package com.johnnycarreiro.fts.core.domain;

import java.time.Instant;

public abstract class AggregateRoot<ID extends Identifier<ID>> extends Entity<ID> {
  protected AggregateRoot(final ID id, final Instant createAt, final Instant updatedAt, Instant deletedAt) {
    super(id, createAt, updatedAt, deletedAt);
  }
}
