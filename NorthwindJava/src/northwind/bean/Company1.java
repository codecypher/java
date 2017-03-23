package northwind.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Company1 extends Company {
	public Company1() {
		super("Northwind",
			new Customer("AALKI", "Company1", "Contact1"),
			new Customer("BBLKI", "Company2", "Contact2"),
			new Customer("CCLKI", "Company3", "Contact3"));
	}
}
