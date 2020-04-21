package com.fapp.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fapp.request.VendorDetails;

@FeignClient(name="http://vendorservice/vendor")
public interface VendorClient {

	@GetMapping("/sbinfo")
	public String batchinfo();
	
	@GetMapping("/{keyword}")
	public ResponseEntity<List<VendorDetails>> searchfood(@PathVariable("keyword") String keyword);
	
	
}
