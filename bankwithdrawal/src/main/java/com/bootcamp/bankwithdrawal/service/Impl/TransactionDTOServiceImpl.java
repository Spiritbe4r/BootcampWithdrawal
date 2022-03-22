package com.bootcamp.bankwithdrawal.service.impl;

import com.bootcamp.bankwithdrawal.dto.TransactionCommand;
import com.bootcamp.bankwithdrawal.service.TransactionDTOService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TransactionDTOServiceImpl implements TransactionDTOService {


  @Value("${microservices-urls.api-transaction}")
  private String apiTransaction;

  private final WebClient transactionWebClient;

  public TransactionDTOServiceImpl() {
    this.transactionWebClient = WebClient.builder()
          .baseUrl(apiTransaction)
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .build();
  }

  @Override
  public Mono<TransactionCommand> saveTransaction(TransactionCommand transaction) {
    return transactionWebClient.post()
          .body(Mono.just(transaction), TransactionCommand.class)
          .retrieve()
          .bodyToMono(TransactionCommand.class);
  }
}
