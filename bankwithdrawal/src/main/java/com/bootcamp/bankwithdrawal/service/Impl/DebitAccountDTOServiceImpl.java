package com.bootcamp.bankwithdrawal.service.impl;

import com.bootcamp.bankwithdrawal.dto.DebitAccountDTO;
import com.bootcamp.bankwithdrawal.service.DebitAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DebitAccountDTOServiceImpl implements DebitAccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DebitAccountDTOServiceImpl.class);

  private final WebClient webClient;

  @Override
  public Mono<DebitAccountDTO> findByAccountNumber(String debitType, String accountNumber) {
    if (debitType.equals("SAVING_ACCOUNT")) {
      return webClient
            .get()
            .uri("/{accountNumber}", accountNumber)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
            .doOnNext(c -> LOGGER.info("Account Response: Account ={}", c.getAccountNumber()))
            .doOnNext(c -> LOGGER.info("Account Response: Account Amounth={}", c.getAmount()));
    } else if (debitType.equals("CURRENT_ACCOUNT")) {
      return webClient
            .get()
            .uri("/{accountNumber}", accountNumber)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
            .doOnNext(c -> LOGGER.info("Account Response: Account Amounth={}", c.getAmount()));
    } else if (debitType.equals("FIXEDTERM_ACCOUNT")) {
      return webClient
            .get()
            .uri("/{accountNumber}", accountNumber)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
            .doOnNext(c -> LOGGER.info("Account Response: Account Amounth={}", c.getAmount()));
    } else return Mono.empty();
  }

  @Override
  public Mono<DebitAccountDTO> updateDebit(String debitType, DebitAccountDTO account) {

    LOGGER.info("Update Debit Function");
    LOGGER.info("Debit Type: {}", debitType);

    if(debitType.equals("SAVING_ACCOUNT")){
      return webClient
            .put()
            .uri("/{id}", account.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(account)
            .retrieve()
            .bodyToMono(DebitAccountDTO.class);
    }else if(debitType.equals("CURRENT_ACCOUNT")){
      return webClient
            .put()
            .uri("/{id}", account.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(account)
            .retrieve()
            .bodyToMono(DebitAccountDTO.class);
    }else if(debitType.equals("FIXEDTERM_ACCOUNT")){
      return webClient
            .put()
            .uri("/{id}", account.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(account)
            .retrieve()
            .bodyToMono(DebitAccountDTO.class);
    }else return Mono.empty();

  }
}
