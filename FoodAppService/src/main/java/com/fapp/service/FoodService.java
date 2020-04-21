package com.fapp.service;

import java.util.List;

import com.fapp.model.OrderDetails;
import com.fapp.model.UserDetails;
import com.fapp.request.TransactionHistory;
import com.fapp.request.VendorDetails;

public interface FoodService {

	public UserDetails createUser(UserDetails user);

	public List<OrderDetails> getlatestOrders(long userid);

	public OrderDetails orderfood(TransactionHistory transhistory,VendorDetails vendor, long userid) throws Exception;

}
