package com.johnnycarreiro.fts.configurations;

import com.johnnycarreiro.fts.infra.transfer.persistence.TransferFeeEntity;
import com.johnnycarreiro.fts.infra.transfer.persistence.TrnasferFeeJpaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.UUID;

@Configuration
public class SeedConfig {

  private TrnasferFeeJpaRepository transferFeeRepository;
  private Environment environment;

  SeedConfig(TrnasferFeeJpaRepository transferFeeRepository, Environment environment) {
    this.transferFeeRepository = transferFeeRepository;
    this.environment = environment;
  }

  @Bean
  CommandLineRunner seedData() {
    return args -> {
      boolean isSeedEnabled = isSeedEnabled(args);
      String activeProfile = environment.getProperty("spring.profiles.active", "default");

      if (isSeedEnabled && (activeProfile.equalsIgnoreCase("dev") || activeProfile.equalsIgnoreCase("test"))) {
        System.out.println("Executing seed data...");
        // Adiciona os dados de seed
        TransferFeeEntity[] seedData = {
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d001").toString(), "Mesmo Dia", 0, 0,
                new BigDecimal("3.00"), new BigDecimal("0.025"), null, null, null),
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d002").toString(), "De 1 a 10 dias",
                1, 10, new BigDecimal("12.00"), new BigDecimal("0.00"), null, null, null),
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d003").toString(), "De 11 a 20 dias",
                11, 20, new BigDecimal("0.00"), new BigDecimal("0.082"), null, null, null),
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d004").toString(), "De 21 a 30 dias",
                21, 30, new BigDecimal("0.00"), new BigDecimal("0.069"), null, null, null),
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d005").toString(), "De 31 a 40 dias",
                31, 40, new BigDecimal("0.00"), new BigDecimal("0.047"), null, null, null),
            new TransferFeeEntity(UUID.fromString("c7b92261-8f3d-4f88-8d10-d8b790a6d006").toString(), "De 41 a 50 dias",
                41, 50, new BigDecimal("0.00"), new BigDecimal("0.017"), null, null, null)
        };

        // Salva os dados no banco
        for (TransferFeeEntity fee : seedData) {
          transferFeeRepository.save(fee);
        }

        System.out.println("Seed data inserted.");
      }
    };
  }

  private boolean isSeedEnabled(String[] args) {
    for (String arg : args) {
      if (arg.equals("--seed")) {
        return true;
      }
    }
    return false;
  }
}
