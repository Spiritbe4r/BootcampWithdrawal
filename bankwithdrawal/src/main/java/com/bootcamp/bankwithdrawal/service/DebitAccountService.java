package com.bootcamp.bankwithdrawal.service;

import com.bootcamp.bankwithdrawal.dto.DebitAccountDTO;
import reactor.core.publisher.Mono;

public interface DebitAccountService {

  Mono<DebitAccountDTO> findByAccountNumber(String debitType, String accountNumber);

  Mono<DebitAccountDTO> updateDebit(String debitType, DebitAccountDTO account);
}
