package com.nt.niranjana.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallBackController 
{

	@GetMapping("/orderFallBack")
	public Mono<String> orderServiceFallBack()
	{
		return Mono.just("Order Service is too long responding or server down,Please try again after some time");
	}
	
	@GetMapping("/paymentFallBack")
	public Mono<String> paymentServiceFallBack()
	{
		return Mono.just("Payment Service is too long responding or server down,Please try again after some time");
	}
}
