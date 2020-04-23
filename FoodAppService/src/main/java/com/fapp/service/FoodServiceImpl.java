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
import com.fapp.request.OrderRequest;
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
	public List<OrderDetails> getlatestOrders(long id) {
		Pageable top10=PageRequest.of(0, 10);
		List<UserDetails> lstOfrec=  userRepository.findByUsername(id, top10);
		UserDetails udetails=new UserDetails();
		lstOfrec.stream().forEach(lst->udetails.setOrderlist(lst.getOrderlist()));
		List<OrderDetails> orderdet=udetails.getOrderlist();
		orderdet.stream().forEach(orderlst->System.out.println(""+orderlst.getId()));
		return orderdet;
	}

		@Override
		public UserDetails orderfood(OrderRequest ordRequest, long userid) throws Exception {
		Optional<UserDetails> checkuserById=userRepository.findById(userid);
		if(checkuserById.isPresent()) {  //check user existence for place order
		ResponseEntity<List<VendorDetails>> foodlist= vendorclient.searchfood(ordRequest.getItmname());
			if(foodlist.getBody().size()>0) {
				System.out.println("Enter If loop1:=====");
				//makePayment before going to place order
				System.out.println("CID: "+ordRequest.getCid()+"\t Amount: "+ordRequest.getAmount());
				ResponseEntity<TransactionHistory> thistory=ebankclient.paymentofOrder(ordRequest.getCid(), ordRequest.getAmount());
				System.out.println("====getBody: "+thistory.getBody().getTransid());
				System.out.println("thistory.getBody.getTransid: "+thistory.getBody().getTransid());
				
				if(thistory.getBody().getTransid() !=0) {
					System.out.println("====Enter if loop2");
					UserDetails udet=checkuserById.get();
					List<OrderDetails> orderlist=udet.getOrderlist();
					OrderDetails uporder=new OrderDetails();
					udet.setId(userid);
					uporder.setAmount(ordRequest.getAmount());
					uporder.setItemname(ordRequest.getItmname());
					uporder.setItmtype(ordRequest.getItmtype());
					uporder.setOrderstatus("success"); 
					uporder.setVname(ordRequest.getVname());
					uporder.setUserdet(udet);
					oRepository.save(uporder);
					
					
					/*
					 * orderlist.stream().forEach(orderlst-> {
					 * orderlst.setAmount(ordRequest.getAmount());
					 * orderlst.setItemname(ordRequest.getItmname());
					 * orderlst.setItmtype(ordRequest.getItmtype());
					 * orderlst.setOrderstatus("success"); orderlst.setVname(ordRequest.getVname());
					 * orderlst.setUserdet(udet); }); udet.setOrderlist(orderlist);
					 */
					//logs the detailed order list
					
					
					orderlist.forEach(ordlst->System.out.println(
							"vendorName: "+ordlst.getVname()
							+"\t: Item Name: "+ordlst.getItemname()
							+"\t Item type: "+ordlst.getItmtype()
							+"\t orderstatus: "+ordlst.getOrderstatus()
							+"\t Orderdate: "+ordlst.getOrderdate()
							));
				return userRepository.save(udet);
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
