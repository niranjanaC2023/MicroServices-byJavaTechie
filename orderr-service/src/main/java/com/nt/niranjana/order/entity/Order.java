package com.nt.niranjana.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ORDERTABLE")
public class Order 
{
	@Id
	private int id;
	private String name;
	private int quantity;
	private double price;
	
	
	
}
