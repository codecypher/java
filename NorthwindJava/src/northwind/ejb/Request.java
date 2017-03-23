package northwind.ejb;

import java.util.List;

import javax.ejb.Remote;

import northwind.entity.Customer;
import northwind.entity.Employee;

@Remote
public interface Request {
	public List<Employee> getEmployees();
	public List<Customer> getCustomers();
}
