package com.bootcamp.bankwithdrawal.handlers;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.dto.DebitAccountDTO;
import com.bootcamp.bankwithdrawal.dto.TransactionCommand;
import com.bootcamp.bankwithdrawal.service.DebitAccountService;
import com.bootcamp.bankwithdrawal.service.TransactionDTOService;
import com.bootcamp.bankwithdrawal.service.WithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class WithdrawalHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalHandler.class);
  @Autowired
  private DebitAccountService accountService;

  @Autowired
  private WithdrawalService service;

  @Autowired
  private TransactionDTOService transactionService;

  /**
   * create withdraw mono.
   *
   * @param request the request
   * @return the mono
   */
  public Mono<ServerResponse> createWithdraw(ServerRequest request) {
    Mono<Withdrawal> monoWithdraw = request.bodyToMono(Withdrawal.class);

    return monoWithdraw
          .flatMap(withdrawRequest -> accountService.findByAccountNumber(
                      withdrawRequest.getTypeOfAccount(), withdrawRequest.getAccountNumber())
                .filter(account -> account.getAmount() >= withdrawRequest.getAmount())
                .flatMap(account -> {
                  //Validando que no esté en el límite de movimientos por mes
                  if (account.getMaxLimitMovementPerMonth() >= account.getMovementPerMonth()) {
                    account.setMovementPerMonth(account.getMovementPerMonth() + 1);
                    account.setAmount(account.getAmount() - withdrawRequest.getAmount());
                  } else if (account.getMaxLimitMovementPerMonth()
                        < account.getMovementPerMonth()) {
                    LOGGER.info("La comisión es de: " + account.getCommission());
                    account.setAmount(account.getAmount()
                          - withdrawRequest.getAmount() - account.getCommission());
                  }
                  LOGGER.info("El ID del débito es" + account.getId());
                  return accountService.updateDebit(account.getTypeOfAccount(), account);
                })
                .flatMap(ope -> {
                  TransactionCommand transaction = new TransactionCommand();
                  transaction.setTypeOfAccount(ope.getTypeOfAccount());
                  transaction.setTypeOfTransaction("WITHDRAW");
                  transaction.setClientIdNumber(ope.getClientIdNumber());
                  transaction.setTransactionAmount(withdrawRequest.getAmount());
                  transaction.setIdNumber(withdrawRequest.getAccountNumber());
                  return transactionService.saveTransaction(transaction);
                })
                .flatMap(trans -> accountService.findByAccountNumber(trans.getTypeOfAccount(),
                      trans.getIdNumber()))
                .flatMap(debit -> {
                  if (debit.getMaxLimitMovementPerMonth() < debit.getMovementPerMonth()) {
                    TransactionCommand commission = new TransactionCommand();
                    commission.setTypeOfAccount(debit.getTypeOfAccount());
                    commission.setTypeOfTransaction("COMMISSION");
                    commission.setClientIdNumber(debit.getClientIdNumber());
                    commission.setTransactionAmount(debit.getCommission());
                    commission.setIdNumber(withdrawRequest.getAccountNumber());
                    return transactionService.saveTransaction(commission);
                  } else {
                    return Mono.just(DebitAccountDTO.builder().build());
                  }
                })
                .flatMap(withdraw -> service.save(withdrawRequest)))
          .flatMap(c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
          .switchIfEmpty(ServerResponse.notFound().build());
  }

  /**
   * find all withdraw mono.
   *
   * @param request the request
   * @return the mono
   */
  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
          .body(service.getAll(), Withdrawal.class);
  }

}
