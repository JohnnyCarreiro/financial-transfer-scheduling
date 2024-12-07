package com.johnnycarreiro.fts.infra.transfer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrnasferFeeJpaRepository extends JpaRepository<TransferFeeEntity, String> {

}
