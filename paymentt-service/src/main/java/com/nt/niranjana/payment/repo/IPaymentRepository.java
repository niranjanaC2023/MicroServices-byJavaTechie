package com.nt.niranjana.payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.niranjana.payment.entity.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Integer> 
{

	Payment findByOrderId(int orderId);

}
