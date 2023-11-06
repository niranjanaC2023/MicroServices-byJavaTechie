package com.nt.niranjana.payment.service.impl;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.niranjana.payment.entity.Payment;
import com.nt.niranjana.payment.repo.IPaymentRepository;
import com.nt.niranjana.payment.service.PaymentService;
@Service
public class PaymentServiceImpl implements PaymentService
{
	private Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
	@Autowired
	private IPaymentRepository paymentRepo;

	@Override
	public Payment savePaymentDataIntoDB(Payment payment) throws JsonProcessingException 
	{
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		logger.info("Payment Service request:{} ",new ObjectMapper().writeValueAsString(payment));
		return paymentRepo.save(payment);
	}
	
	public String paymentProcessing()
	{
		//api should be third party(payPal,paytm)
		return new Random().nextBoolean() ? "success" : "false";
	}

	@Override
	public Payment findPaymentDetailsByUsingOrderID(int orderId) throws JsonProcessingException 
	{
		Payment payment = paymentRepo.findByOrderId(orderId);
		logger.info("Payment Service findPaymentDetailsByUsingOrderID method:{} ",new ObjectMapper().writeValueAsString(payment));
		return payment;
	}
}
