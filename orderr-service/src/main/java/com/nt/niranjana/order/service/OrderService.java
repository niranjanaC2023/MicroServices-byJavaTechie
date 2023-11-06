package com.nt.niranjana.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nt.niranjana.order.dto.TransactionalRequest;
import com.nt.niranjana.order.dto.TransactionalResponse;
import com.nt.niranjana.order.entity.Order;


public interface OrderService 
{
	public TransactionalResponse saveOrderDetailsIntoDB(TransactionalRequest request) throws JsonProcessingException;

	public Order fetchOrderDetailsFromDB(Integer orderId);

}
