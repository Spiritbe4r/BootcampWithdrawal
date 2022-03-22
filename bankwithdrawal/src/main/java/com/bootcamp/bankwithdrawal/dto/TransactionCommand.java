package com.bootcamp.bankwithdrawal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCommand {

  private String typeOfTransaction;

  private String idNumber;

  private double transactionAmount;

  private String typeOfAccount;

  private String clientIdNumber;

  private String transactionDescription;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dateOperation = LocalDateTime.now();
}
