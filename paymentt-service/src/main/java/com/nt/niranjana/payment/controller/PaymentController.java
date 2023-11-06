package com.nt.niranjana.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nt.niranjana.payment.entity.Payment;
import com.nt.niranjana.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController 
{
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/doPayment")
	public Payment doPaymentDetails(@RequestBody Payment payment) throws JsonProcessingException
	{
		return paymentService.savePaymentDataIntoDB(payment);
	}
	
	@GetMapping("/{orderId}")
	public Payment findPaymentDetailsByUsingOrderID(@PathVariable int orderId) throws JsonProcessingException
	{
		return paymentService.findPaymentDetailsByUsingOrderID(orderId);
	}
}
