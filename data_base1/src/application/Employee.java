package application;

public class Employee {

	private int EID;
	private String EName;
	private String 	email;
	private int phoneNumber;
	private String JobTitle;
	private String Address;
	private String City;
	private String Country;
	
	public Employee(int EID, String EName, String email, int phoneNumber, String jobTitle, String Address, String City,
			String Country) {
		super();
		this.EID = EID;
		this.EName = EName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.JobTitle = jobTitle;
		this.Address = Address;
		this.City = City;
		this.Country = Country;
	}

	public int getEID() {
		return EID;
	}

	public void setEID(int EID) {
		this.EID = EID;
	}

	public String getEName() {
		return EName;
	}

	public void setEName(String EName) {
		this.EName = EName;
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

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.JobTitle = jobTitle;
	}

	

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
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

	@Override
	public String toString() {
		return "Employee [EID=" + EID + ", EName=" + EName + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", JobTitle=" + JobTitle + ", Adress=" + Address + ", City=" + City + ", Country=" + Country + "]";
	}
	
	
	
	
	
	
	
	

}
