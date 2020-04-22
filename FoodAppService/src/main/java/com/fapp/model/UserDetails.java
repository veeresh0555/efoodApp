package com.fapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String uname;
	
	private String gender;
	
	private String age;
	
	@OneToMany(mappedBy = "userdet",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	List<OrderDetails> orderlist;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<OrderDetails> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<OrderDetails> orderlist) {
		this.orderlist = orderlist;
	}
	
	
}
