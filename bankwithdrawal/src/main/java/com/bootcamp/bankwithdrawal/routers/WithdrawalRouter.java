package com.bootcamp.bankwithdrawal.routers;

import com.bootcamp.bankwithdrawal.handlers.WithdrawalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WithdrawalRouter {

  @Bean
  public RouterFunction<ServerResponse> routes(final WithdrawalHandler handler) {
    return route(GET("/api/retire"), handler::findAll)
          .andRoute(POST("/api/retire"), handler::createWithdraw);
  }
}
