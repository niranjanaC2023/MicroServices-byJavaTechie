package com.nt.niranjana.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.niranjana.order.entity.Order;

public interface IOrderRepository extends JpaRepository<Order, Integer> 
{

}
