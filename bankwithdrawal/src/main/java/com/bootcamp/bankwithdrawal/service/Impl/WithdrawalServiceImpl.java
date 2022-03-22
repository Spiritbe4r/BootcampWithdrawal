package com.bootcamp.bankwithdrawal.service.impl;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.repository.WithdrawalRepository;
import com.bootcamp.bankwithdrawal.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {


  @Autowired
  private WithdrawalRepository withdrawalRepository;


  @Override
  public Flux<Withdrawal> getAll() {
    return withdrawalRepository.findAll();
  }

  @Override
  public Mono<Withdrawal> getById(String s) {
    return withdrawalRepository.findById(s);
  }

  @Override
  public Mono<Withdrawal> save(Withdrawal obj) {
    return withdrawalRepository.save(obj);
  }

  @Override
  public Mono<Withdrawal> update(Mono<Withdrawal> obj, String s) {
    return withdrawalRepository.findById(s)
          .doOnNext(e -> e.setId(s))
          .flatMap(withdrawalRepository::save);
  }

  @Override
  public Mono<Void> delete(String s) {
    return withdrawalRepository.deleteById(s);
  }
}
