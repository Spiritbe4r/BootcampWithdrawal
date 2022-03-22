package com.bootcamp.bankwithdrawal.dto;


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
public class DebitAccountDTO {

  private String id;

  private double amount;

  private String clientIdNumber;

  private String typeOfAccount;

  private String accountNumber;

  private int maxLimitMovementPerMonth;

  private int movementPerMonth;

  private double commission;

}

