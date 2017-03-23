package northwind.bean;

import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean
public class Customer {
	
	private String customerID, companyName, contactName, contactTitle, address;
	private String city, region, postalCode, country, phone, fax;
	
	public Customer() {}
	
	public Customer(String customerID, String companyName, String contactName) {
		this.customerID = customerID;
		this.companyName = companyName;
		this.contactName = contactName;
	}
	
	public Customer(String customerID, 
					String companyName, 
					String contactName,
					String contactTitle, 
					String address, 
					String city, 
					String region, 
					String postalCode, 
					String country, 
					String phone, 
					String fax) {
		this.customerID = customerID; 
		this.companyName  = companyName;
		this.contactName  = contactName;
		this.contactTitle = contactTitle;
		this.address = address;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
	}

	private final String dbUser = "nwinduser";
	private final String dbPassword = "nwinduser";
	private final String dbUrl = "jdbc/Northwind";

	// Get database connection
	private Connection getConnection() {
		Context ctx = null;
		DataSource ds = null;
		Connection conn = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(dbUrl);
			conn = ds.getConnection(dbUser, dbPassword);		
			if (ctx != null) ctx.close();		
		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException ne) {
			System.out.println("NamingException: " + ne.getMessage());
		}
		return(conn);
	}

	// @Id
	// @Column(nullable=false)
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactTitle() {
		return contactTitle;
	}
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	@Temporal(TIMESTAMP)
    public Date getLastUpdate() {
        return lastUpdate;
    }    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Temporal(DATE)
    public Date getRevisionDate() {
        return revisionDate;
    }
    
    @Column(name="VENDORNAME")
    public String getName() {
        return name;
    }  
    public void setName(String name) {
        this.name = name;
    }
	*/
	
    public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
}
