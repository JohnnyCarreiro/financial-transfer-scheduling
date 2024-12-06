
package com.johnnycarreiro.fts.configurations.use_cases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferUseCase;
import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;
import com.johnnycarreiro.fts.infra.transfer.JpaTransferRepository;
import com.johnnycarreiro.fts.infra.transfer.persistence.TransferJpaRepository;

@Configuration
public class TransferUseCaseConfig {

  private final JpaTransferRepository transferRepository;

  TransferUseCaseConfig(TransferJpaRepository jpaRepository) {
    this.transferRepository = new JpaTransferRepository(jpaRepository);
  }

  @Bean
  public TransferFeeCalculatorService transferFeeCalculatorService() {
    return new TransferFeeCalculatorService();
  }

  @Bean
  public CreateTransferUseCase createTransferUseCase(TransferFeeCalculatorService feeCalculatorService) {
    return new CreateTransferUseCase(feeCalculatorService, transferRepository);
  }
}
