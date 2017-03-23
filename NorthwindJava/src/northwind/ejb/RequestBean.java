package northwind.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import northwind.entity.Customer;
import northwind.entity.Employee;

/**
 * Define a stateless session bean (EJB)
 * @author neo
 */

@Stateless(mappedName="RequestBean")
public class RequestBean implements Request {

    @PersistenceContext(unitName="NorthwindJPA")
    private EntityManager em;

    private static final Logger logger = Logger.getLogger("northwind.ejb.RequestBean");
   
    public List<Customer> getCustomers() {
        try {
            List<Customer> customers = (List<Customer>) em.createNamedQuery("Customer.findAll").getResultList();
            return customers;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Employee> getEmployees() {
        try {
            List<Employee> employees = (List<Employee>) em.createNamedQuery("Employee.findAll").getResultList();
            return employees;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
