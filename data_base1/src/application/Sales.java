package application;

import java.util.Date;

public class Sales {
	
	private int SID;
	//private Date sdate;
	
	private int productSold;
	private int SQuantity;
	private double totalCost;
	private int CID;
	private int EID;
	private int productID;
	
	
	public Sales(int SID, int productSold, int SQuantity, double totalCost,int CID, int EID, int productID) {
		super();
		this.SID = SID;
		this.productSold = productSold;
		this.SQuantity = SQuantity;
		this.totalCost = totalCost;
		this.CID=CID;
		this.EID=EID;
		this.productID=productID;
	}


	public int getSID() {
		return SID;
	}


	public void setSID(int SID) {
		this.SID = SID;
	}


	public int getProductSold() {
		return productSold;
	}


	public void setProductSold(int productSold) {
		this.productSold = productSold;
	}


	public int getSQuantity() {
		return SQuantity;
	}


	public void setSQuantity(int SQuantity) {
		this.SQuantity = SQuantity;
	}


	public double getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	


	public int getCID() {
		return CID;
	}


	public void setCID(int CID) {
		this.CID = CID;
	}


	public int getEID() {
		return EID;
	}


	public void setEID(int EID) {
		this.EID = EID;
	}


	public int getProductID() {
		return productID;
	}


	public void setProductID(int productID) {
		this.productID = productID;
	}


	@Override
	public String toString() {
		return "Sales [SID=" + SID + ", productSold=" + productSold + ", SQuantity=" + SQuantity + ", totalCost="
				+ totalCost + ", CID=" + CID + ", EID=" + EID + ", productID=" + productID + "]";
	}


	
	
	
	

}
