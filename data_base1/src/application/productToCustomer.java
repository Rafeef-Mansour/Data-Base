package application;

public class productToCustomer {
	
	 private int CID ;
	 private int productID ;
	 
	public productToCustomer(int CID, int productID) {
		super();
		this.CID = CID;
		this.productID = productID;
	}

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	@Override
	public String toString() {
		return "productToCustomer [CID=" + CID + ", productID=" + productID + "]";
	}
	
	
	
	 
	 

}
