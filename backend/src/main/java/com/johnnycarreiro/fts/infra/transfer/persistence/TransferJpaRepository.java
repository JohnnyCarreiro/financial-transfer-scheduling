package com.johnnycarreiro.fts.infra.transfer.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransferJpaRepository extends JpaRepository<TransferEntity, String> {
  @Query("SELECT f FROM TransferFeeEntity f")
  List<TransferFeeEntity> findAllFees();

  @EntityGraph(attributePaths = "transferFee")
  Optional<TransferEntity> findById(String id);

  @Query("SELECT t FROM TransferEntity t JOIN FETCH t.transferFee WHERE t.id = :id")
  Optional<TransferEntity> findByIdWithFee(@Param("id") String id);
}
