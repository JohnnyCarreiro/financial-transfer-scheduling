package com.johnnycarreiro.fts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.johnnycarreiro.fts.configurations.WebServerConfig;

@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebServerConfig.class, args);
  }

}
