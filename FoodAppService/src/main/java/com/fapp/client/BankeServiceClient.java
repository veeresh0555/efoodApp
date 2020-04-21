package com.fapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fapp.request.TransactionHistory;

@FeignClient(name = "http://ebankaccount/ebank")
public interface BankeServiceClient {

	@PostMapping("/payment")
	public ResponseEntity<TransactionHistory> paymentofOrder(@RequestBody TransactionHistory transhistory);
	
}
