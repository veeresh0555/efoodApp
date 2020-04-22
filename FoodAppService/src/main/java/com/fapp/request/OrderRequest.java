package com.fapp.request;

public class OrderRequest {
	
	private long cid; 
	
	//private long oid;
	
	private String itmname;
	
	private double amount;
	
	private String vname;
	
	private String itmtype;

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getItmname() {
		return itmname;
	}

	public void setItmname(String itmname) {
		this.itmname = itmname;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public String getItmtype() {
		return itmtype;
	}

	public void setItmtype(String itmtype) {
		this.itmtype = itmtype;
	}
	
}
