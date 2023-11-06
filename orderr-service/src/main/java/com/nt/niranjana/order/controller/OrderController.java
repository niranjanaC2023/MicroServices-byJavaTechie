package com.nt.niranjana.order.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nt.niranjana.order.dto.TransactionalRequest;
import com.nt.niranjana.order.dto.TransactionalResponse;
import com.nt.niranjana.order.entity.Order;
import com.nt.niranjana.order.service.OrderService;
import com.nt.niranjana.order.service.impl.OrderServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/order")
public class OrderController 
{
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/bookOrder")
	@CircuitBreaker(name="bookOrderBreaker",fallbackMethod = "bookOrderFallbackMethod")
	public TransactionalResponse saveBookOrderDetails(@RequestBody TransactionalRequest request) throws JsonProcessingException
	{
		return orderService.saveOrderDetailsIntoDB(request);
	}
	//Creating fall back method for CircuitBreaker(CREATING FALL BACK METHOD FOR CIRCUITBREAKER)
	public TransactionalResponse bookOrderFallbackMethod(Exception ex)
	{
		logger.info("Fallback is excuted because bookOrderFallbackMethod service is down: "+ex.getMessage());
		TransactionalResponse tranResponse = new TransactionalResponse();
		tranResponse.setTransactionId(null);
		tranResponse.setMessage("Order can't placed,server down");
		tranResponse.setAmount(0000);
		return tranResponse;
	}
//---------------------------------------------------------------------------------------------------------------------
	@GetMapping("/fetchBookDetailsUsingOrderID/{orderId}")  
	//@HystrixCommand(fallbackMethod = "dummyFetchOrderDetails",commandProperties = { @HystrixProperty(name="circuitBreaker.enabled", value="true"),@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000")})
	//@CircuitBreaker(name="fetchBookOrderBreaker",fallbackMethod = "fetchBookOrderFallbackMethodCircuitBreaker")
	@Retry(name="fetchBookOrderRetry",fallbackMethod = "fetchBookOrderFallbackMethodRetry")
	public Order fetchBookOrderDetailsUsingOrderId(@PathVariable Integer orderId)
	{
		return orderService.fetchOrderDetailsFromDB(orderId);
	}
	
	//Creating fall back method for CircuirBreaker(CREATING FALL BACK METHOD FOR CIRCUITBREAKER)
	public Order fetchBookOrderFallbackMethodCircuitBreaker(Exception ex)
	{
		logger.info("Fallback is excuted because fetchBookOrderFallbackMethod service is down: "+ex.getMessage());
		Order order = new Order();
		order.setId(111111);
		order.setName("unable to fetch book details,service down");
		order.setPrice(11111111);
		order.setQuantity(111111111);
		return order;
	}
	
	
	//Creating fall back method for REtry(CREATING FALL BACK METHOD FOR CIRCUITBREAKER)
	private int attempt =1;
	public Order fetchBookOrderFallbackMethodRetry(Exception ex)
	{
		logger.info("Fallback is excuted because fetchBookOrderFallbackMethodRetry service is down: "+ex.getMessage());
		System.out.println("retry method called "+attempt+" times "+"at "+new Date());
		Order order = new Order();
		order.setId(111111);
		order.setName("unable to fetch book details,service down");
		order.setPrice(11111111);
		order.setQuantity(111111111);
		return order;
	}
}
