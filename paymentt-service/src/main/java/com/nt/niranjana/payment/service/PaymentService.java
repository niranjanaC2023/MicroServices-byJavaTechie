package com.nt.niranjana.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nt.niranjana.payment.entity.Payment;

public interface PaymentService 
{
	public Payment savePaymentDataIntoDB(Payment payment) throws JsonProcessingException;

	public Payment findPaymentDetailsByUsingOrderID(int orderId) throws JsonProcessingException;

}
