package application;

public class Customer {
	private int customerID;
	private String name;
	private String email;
	private int phoneNumber;
	private String address;

	public Customer(int customerID, String name, String email, int phoneNumber, String address) {
		this.customerID = customerID;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}


	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", address=" + address + "]";
	}
}
