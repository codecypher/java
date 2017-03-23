package northwind.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import northwind.ejb.Request;
import northwind.entity.Customer;
import northwind.entity.Employee;

/**
 * Define Managed Bean
 * @author jholmes
 */

// If eager = "true" then managed bean is created before it is requested 
// for the first time; otherwise "lazy" initialization is used in which 
// bean will be created only when it is requested.

@ManagedBean(eager=true, name="northwindManager")
//@Named("northwindManager")
@SessionScoped
public class NorthwindManager implements Serializable {
	
	private static final long serialVersionUID = 2142383151318489373L;
	
    @EJB
    private Request request;
	
    private static final Logger logger = Logger.getLogger("northwind.web.NorthwindManager");
    
	protected String customerID;
	protected Customer customer = new Customer();
	//private Northwind lookup = new Northwind();

    private List<Employee> employees;
    private List<Customer> customers;

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        try {
            this.customers = request.getCustomers();
        } catch (Exception e) {
            logger.warning("Couldn't get customers. " + e.getMessage());
        }
        return customers;
    }

    /**
     * @return the employees
     */
    public List<Employee> getEmployees() {
        try {
            this.employees = request.getEmployees();
        } catch (Exception e) {
            logger.warning("Couldn't get employees. " + e.getMessage());
        }
        return employees;
    }
    
	public String getCustomerID() {
		return (customerID);
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID.trim();
		//if (this.customerID.isEmpty()) {
		//	this.customerID = "(none)";
		//}
	}

	public Customer getCustomer() {
		return (customer);
	}
	
	/*
	public String showCustomer() {
		customer = lookup.findCustomer(customerID);
		if (customer == null) {
			return ("unknown-customer");
		} else {
			return ("show-customer");
		}
	}
	
	public String saveCustomer() {
		String messageText = null;

		boolean success = lookup.saveCustomer(customer);
		
		if (success) {
			messageText = "Customer updated successfully";			
		} else {
			messageText = "Customer update failed!";						
		}

		if (messageText != null) {
			FacesMessage errorMessage = new FacesMessage(messageText);
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
		}

		return (null);
	}
	*/
}
