package com.bootcamp.bankwithdrawal.service;


import com.bootcamp.bankwithdrawal.dto.TransactionCommand;
import reactor.core.publisher.Mono;

public interface TransactionDTOService {

  Mono<TransactionCommand> saveTransaction(TransactionCommand transactionDto);
}
