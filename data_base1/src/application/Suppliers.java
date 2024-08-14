package application;

public class Suppliers {

	private int ID;
	private String SupplierName;
	private int phoneNumber;
	private String CompanyName;
	private String Address;
	private String City;
	private String Country;
	private int productID;
	
	public Suppliers(int ID, String SupplierName, int phoneNumber, String companyName, String Address, String City,
			String Country, int productID) {
		super();
		this.ID = ID;
		this.SupplierName = SupplierName;
		this.phoneNumber = phoneNumber;
		this.CompanyName = companyName;
		this.Address = Address;
		this.City = City;
		this.Country = Country;
		this.productID = productID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String SupplierName) {
		this.SupplierName = SupplierName;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = Address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String Country) {
		this.Country = Country;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	@Override
	public String toString() {
		return "Suppliers [ID=" + ID + ", SupplierName=" + SupplierName + ", phoneNumber=" + phoneNumber
				+ ", CompanyName=" + CompanyName + ", Address=" + Address + ", City=" + City + ", Country=" + Country
				+ ", productID=" + productID + "]";
	}
	
	
	
	
	
	
}
