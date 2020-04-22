package com.fapp.service;

import java.util.List;

import com.fapp.model.OrderDetails;
import com.fapp.model.UserDetails;
import com.fapp.request.OrderRequest;

public interface FoodService {

	public UserDetails createUser(UserDetails user);

	public List<OrderDetails> getlatestOrders(long userid);

	//public OrderDetails orderfood(TransactionHistory transhistory,VendorDetails vendor, long userid) throws Exception;
	public UserDetails orderfood(OrderRequest ordRequest, long userid) throws Exception;

}
