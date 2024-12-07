package com.johnnycarreiro.fts.infra.api.controllers;

import java.net.URI;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnnycarreiro.fts.application.transfer.create.CreateTransferUseCase;
import com.johnnycarreiro.fts.application.transfer.services.TransferService;
import com.johnnycarreiro.fts.domain.entities.transfer.Transfer;
import com.johnnycarreiro.fts.infra.transfer.models.TransferPresenter;
import com.johnnycarreiro.fts.infra.transfer.models.TransferRequest;
import com.johnnycarreiro.fts.core.domain.validation.ValidationHandler;

@RestController
@RequestMapping("/transfers")
public class TransferController {

  private final CreateTransferUseCase createTransferUseCase;
  // private final UpdateTransferUseCase updateTransferUseCase;
  private final TransferService transferService;

  public TransferController(CreateTransferUseCase createTransferUseCase,
      TransferService transferService) {
    this.createTransferUseCase = createTransferUseCase;
    this.transferService = transferService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody TransferRequest request) {
    var result = createTransferUseCase.execute(request.toCommand());

    final Function<Transfer, ResponseEntity<?>> onSuccess = success -> ResponseEntity
        .created(URI.create("/transfers"))
        .body(TransferPresenter.present(success));

    final Function<ValidationHandler, ResponseEntity<?>> onError = ValidationHandler -> ResponseEntity
        .unprocessableEntity().body(ValidationHandler);

    return result.fold(onSuccess, onError);
  }

  // @PutMapping("/{id}")
  // public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody
  // TransferRequest request) {
  // var result = updateTransferUseCase.execute(id, request.toCommand());
  // return result.isSuccess()
  // ? ResponseEntity.ok(TransferPresenter.present(result.getValue()))
  // : ResponseEntity.badRequest().body(result.getError());
  // }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable String id) {
    return transferService.findById(id)
        .map(transfer -> ResponseEntity.ok(TransferPresenter.present(transfer)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<?> listAll() {
    return ResponseEntity.ok(transferService.listAll().stream()
        .map(TransferPresenter::present)
        .toList());
  }
}
