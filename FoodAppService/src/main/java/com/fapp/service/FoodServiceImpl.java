package com.fapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fapp.client.BankeServiceClient;
import com.fapp.client.VendorClient;
import com.fapp.exception.RecordsNotFoundException;
import com.fapp.model.OrderDetails;
import com.fapp.model.UserDetails;
import com.fapp.repository.OrderRepository;
import com.fapp.repository.UserRepository;
import com.fapp.request.TransactionHistory;
import com.fapp.request.VendorDetails;

@Service
@Transactional
public class FoodServiceImpl implements FoodService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository oRepository;

	@Autowired
	VendorClient vendorclient;
	
	@Autowired
	BankeServiceClient ebankclient;
	
	@Override
	public UserDetails createUser(UserDetails user) {
		Optional<UserDetails> findByUid=userRepository.findById(user.getId());
		if(findByUid.isPresent()) {
			UserDetails updateuser=findByUid.get();
			updateuser.setId(user.getId());
			updateuser.setGender(user.getGender());
			updateuser.setAge(user.getAge());
			updateuser.setUname(user.getUname());
			return userRepository.save(updateuser);
		}else {
			return userRepository.save(user);
		}
	}

	@Override
	public List<OrderDetails> getlatestOrders(long userid) {
		Pageable top10=PageRequest.of(0, 10);
		List<UserDetails> lstOfrec=  userRepository.findByUsername(userid, top10);
		UserDetails udetails=new UserDetails();
		lstOfrec.stream().forEach(lst->udetails.setOrderlist(lst.getOrderlist()));
		List<OrderDetails> orderdet=udetails.getOrderlist();
		orderdet.stream().forEach(orderlst->System.out.println(""+orderlst.getId()));
		return orderdet;
	}

	@Override
	public OrderDetails orderfood(TransactionHistory transhistory,VendorDetails vendor, long userid) throws Exception {
		Optional<UserDetails> checkuserById=userRepository.findById(userid);
		if(checkuserById.isPresent()) {  //check user existence for place order
		ResponseEntity<List<VendorDetails>> foodlist= vendorclient.searchfood(vendor.getItemname());
			if(foodlist.getBody().size()>0) {
				//makePayment before going to place order
				ResponseEntity<TransactionHistory> thistory=ebankclient.paymentofOrder(transhistory);
				if(thistory.getBody().getTransid() !=0) {
				OrderDetails uo=new OrderDetails();
				uo.setAmount(vendor.getPrice());
				uo.setItemname(vendor.getItemname());
				uo.setItmtype(vendor.getItmtype());
				uo.setOrderstatus("ordered");
				uo.setVname(vendor.getVname());
				return oRepository.save(uo);
				}else {
					throw new RecordsNotFoundException("payment failed check your payment service");
				}
			}else {
				System.out.println("Please select food");
				throw new RecordsNotFoundException("Please select food");
			}
		}else {
			System.out.println("User Not Registered");
			throw new RecordsNotFoundException("User Not Registered/User Not Found");
		}
	}
	
	
	
	
	
}
