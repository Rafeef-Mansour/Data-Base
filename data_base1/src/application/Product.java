package application;

public class Product {
	
	private int productID;
	private String pname;
	private String brand;
	private int size;
	private double price;
	private String pcolor;
	private String style;
	private int quantity;
	
	
	
	
	public Product(int productID, String pname, String brand, int size, double price, String pcolor, String style,
			int quantity) {
		super();
		this.productID = productID;
		this.pname = pname;
		this.brand = brand;
		this.size = size;
		this.price = price;
		this.pcolor = pcolor;
		this.style = style;
		this.quantity = quantity;
	}


	public int getProductID() {
		return productID;
	}


	public void setProductID(int productID) {
		this.productID = productID;
	}


	public String getPname() {
		return pname;
	}


	public void setPname(String pname) {
		this.pname = pname;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getPcolor() {
		return pcolor;
	}


	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "Product [productID=" + productID + ", pname=" + pname + ", brand=" + brand + ", size=" + size
				+ ", price=" + price + ", pcolor=" + pcolor + ", style=" + style + ", quantity=" + quantity + "]";
	}
	
	
	
	
	
	
	
	

}
