package com.nt.niranjana.order.dto;

import com.nt.niranjana.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionalRequest
{
	private Order order;
	private Payment payment;
}
