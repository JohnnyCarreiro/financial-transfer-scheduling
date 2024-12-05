package com.johnnycarreiro.fts.configurations.use_cases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferUseCase;
import com.johnnycarreiro.fts.domain.services.TransferFeeCalculatorService;
import com.johnnycarreiro.fts.infra.transfer.JpaTransferRepository;

@Configuration
public class TransferUseCaseConfig {

  private JpaTransferRepository transferRepository;
  private TransferFeeCalculatorService feeCalculatorService;

  // Setter Injection
  public void setTransferRepository(JpaTransferRepository transferRepository) {
    this.transferRepository = transferRepository;
  }

  public void setFeeCalculatorService(TransferFeeCalculatorService feeCalculatorService) {
    this.feeCalculatorService = feeCalculatorService;
  }

  @Bean
  public CreateTransferUseCase createTransferUseCase() {
    return new CreateTransferUseCase(feeCalculatorService, transferRepository);
  }

  @Bean
  public TransferFeeCalculatorService transferFeeCalculatorService() {
    return new TransferFeeCalculatorService();
  }
}
