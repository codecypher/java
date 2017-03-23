package northwind.bean;

public class Company {
	private String companyName;
	private Customer[] customers;

	public Company(String companyName, Customer... customers) {
		this.companyName = companyName;
		this.customers = customers;
	}

	public String getCompanyName() {
		return (companyName);
	}

	public Customer[] getCustomers() {
		return (customers);
	}
}
