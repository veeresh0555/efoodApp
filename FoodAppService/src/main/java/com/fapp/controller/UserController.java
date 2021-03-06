package com.fapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fapp.client.VendorClient;
import com.fapp.model.OrderDetails;
import com.fapp.model.UserDetails;
import com.fapp.request.OrderRequest;
import com.fapp.request.VendorDetails;
import com.fapp.service.FoodService;

@RestController
@RequestMapping("/foodapp")
public class UserController {

	@Autowired
	Environment env;
	
	@Autowired
	FoodService foodservice;
	
	@Autowired
	VendorClient vclient;
	
	@GetMapping("/foodappInfo")
	public String foodAppinfo() {
		String port=env.getProperty("local.server.port");
		return "Food App Running on "+port;
	}
	
	@PostMapping("/creatuser")
	public ResponseEntity<UserDetails> saveOrUpdate(UserDetails user) {
		UserDetails reguser=foodservice.createUser(user);
		return new ResponseEntity<UserDetails>(reguser,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{keyword}")
	public ResponseEntity<List<VendorDetails>> searchfood(@PathVariable("keyword") String keyword){
		return vclient.searchfood(keyword);
	}
	
	@GetMapping("/top10records")
	public ResponseEntity<List<OrderDetails>> top10Orders(@RequestParam("id")long id){
		List<OrderDetails> lstOfOrders=foodservice.getlatestOrders(id);
		return new ResponseEntity<List<OrderDetails>>(lstOfOrders,new HttpHeaders(),HttpStatus.OK);
	}
	
	
	@PostMapping("/orderfood/{userid}")
		public ResponseEntity<UserDetails> foodOrder(@RequestBody OrderRequest ordRequest,@PathVariable("userid") long userid) throws Exception{
		UserDetails foodorder=foodservice.orderfood(ordRequest,userid);
		return new ResponseEntity<UserDetails>(foodorder,new HttpHeaders(),HttpStatus.OK);
	}

	
	
	
	
	
	
}
