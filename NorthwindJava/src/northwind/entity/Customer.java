package northwind.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the Customers database table.
 * @author jholmes
 */

@Entity
@Table(name="Customers")
//@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", 
    	query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerID", 
        query = "SELECT c FROM Customer c WHERE c.customerID = :customerID"),
    @NamedQuery(name = "Customer.findByCompanyName", 
        query = "SELECT c FROM Customer c WHERE c.companyName = :companyName"),
    @NamedQuery(name = "Customer.findByContactName", 
        query = "SELECT c FROM Customer c WHERE c.contactName = :contactName"),
    @NamedQuery(name = "Customer.findByContactTitle", 
        query = "SELECT c FROM Customer c WHERE c.contactTitle = :contactTitle"),
    @NamedQuery(name = "Customer.findByAddress", 
        query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByCity", 
        query = "SELECT c FROM Customer c WHERE c.city = :city"),
    @NamedQuery(name = "Customer.findByRegion", 
        query = "SELECT c FROM Customer c WHERE c.region = :region"),
    @NamedQuery(name = "Customer.findByPostalCode", 
        query = "SELECT c FROM Customer c WHERE c.postalCode = :postalCode"),
    @NamedQuery(name = "Customer.findByCountry", 
        query = "SELECT c FROM Customer c WHERE c.country = :country"),
    @NamedQuery(name = "Customer.findByPhone", 
        query = "SELECT c FROM Customer c WHERE c.phone = :phone")
})
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;

    //private Date lastUpdate;

	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
	@Column(name="CustomerID")
	private String customerID;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
	@Column(name="CompanyName")
	private String companyName;

    @Size(max = 30)
	@Column(name="ContactName")
	private String contactName;

    @Size(max = 30)
	@Column(name="ContactTitle")
	private String contactTitle;

    @Size(max = 60)
	@Column(name="Address")
	private String address;
    
    @Size(max = 15)
	@Column(name="City")
	private String city;

	@Size(max = 15)
	@Column(name="Region")
	private String region;

	@Size(max = 10)
	@Column(name="PostalCode")
	private String postalCode;

    @Size(max = 15)
	@Column(name="Country")
	private String country;

	@Size(max = 24)
	@Column(name="Phone")
	private String phone;

	@Size(max = 24)
	@Column(name="Fax")
	private String fax;

	private Collection<Customer> customers;

	public Customer() {
	}

    public Customer(String customerID) {
        this.customerID = customerID;
    }
    
	public String getCustomerID() {
		return this.customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return this.contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Collection<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}

/*
    @Temporal(TIMESTAMP)
    public Date getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
*/
	
/*
	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		//customer.setCustomer(this);
		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		//customer.setCustomer(null);
		return customer;
	}
*/
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "northwind.entity.Customers[ customerID=" + customerID + " ]";
    }
	
}