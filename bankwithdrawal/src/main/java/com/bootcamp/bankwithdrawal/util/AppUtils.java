package com.bootcamp.bankwithdrawal.util;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

  public static WithdrawalDto entityToDto(Withdrawal withdrawal) {
    WithdrawalDto accDto = new WithdrawalDto();
    BeanUtils.copyProperties(withdrawal, accDto);
    return accDto;
  }

  public static Withdrawal dtoToEntity(WithdrawalDto accDto) {
    Withdrawal withdrawal = new Withdrawal();
    BeanUtils.copyProperties(accDto, withdrawal);
    return withdrawal;
  }
}
