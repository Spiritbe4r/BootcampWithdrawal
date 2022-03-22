package com.bootcamp.bankwithdrawal.service;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WithdrawalService extends CrudService<Withdrawal,String> {


}
