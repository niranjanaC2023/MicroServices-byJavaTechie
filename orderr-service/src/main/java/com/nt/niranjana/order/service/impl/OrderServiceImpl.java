package com.nt.niranjana.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.niranjana.order.dto.Payment;
import com.nt.niranjana.order.dto.TransactionalRequest;
import com.nt.niranjana.order.dto.TransactionalResponse;
import com.nt.niranjana.order.entity.Order;
import com.nt.niranjana.order.exception.ResourceNotFounfException;
import com.nt.niranjana.order.repo.IOrderRepository;
import com.nt.niranjana.order.service.OrderService;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService
{
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private IOrderRepository orderRepo;
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;
	
	@Override
	public TransactionalResponse saveOrderDetailsIntoDB(TransactionalRequest request) throws JsonProcessingException 
	{
		String response = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		logger.info("OrderServiceImpl request",new ObjectMapper().writeValueAsString(request));
		
		//Rest call
		//Payment  paymentResponse = restTemplate.postForObject("http://localhost:2002/payment/doPayment", payment, Payment.class);
		
		//for microservice,here we are passing Order-service name which are registered with eureka instead of using localhost with port
		//Payment  paymentResponse = restTemplate.postForObject("http://PAYMENTT-SERVICE/payment/doPayment", payment, Payment.class);
		
		//here we are getting the URL from config-server .yml file
		Payment  paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
		
		logger.info("Payment srevice response from OrderServiceImpl rest call",new ObjectMapper().writeValueAsString(paymentResponse));
		response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successfully and order placed" : "order not placed";
		orderRepo.save(order);
		return new TransactionalResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
	}

	@Override
	public Order fetchOrderDetailsFromDB(Integer orderId) 
	{
		  Order order = orderRepo.findById(orderId).orElseThrow(()->new ResourceNotFounfException(""+orderId));
		  Payment  paymentResponse = restTemplate.getForObject("http://PAYMENTT-SERVICE/payment/"+order.getId(), Payment.class);
		  return order;
		 
	}

	

}
