package com.bootcamp.bankwithdrawal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@EnableEurekaClient
@SpringBootApplication
public class BankwithdrawalApplication {

	@Value("${microservices-urls.api-account}")
	private String apiAccounts;

	@Bean
	public WebClient webClient(WebClient.Builder builder){
		return builder
				.baseUrl(apiAccounts)
				.defaultHeader(HttpHeaders.CONTENT_TYPE,
							MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BankwithdrawalApplication.class, args);
	}

}
