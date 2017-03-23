
package northwind.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The persistent class for the Employees database table.
 * @author jholmes
 */

@Entity
@Table(name = "Employees", catalog = "Northwind", schema = "dbo")
//@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", 
        query = "SELECT e FROM Employee e") ,
    @NamedQuery(name = "Employee.findByEmployeeID", 
        query = "SELECT e FROM Employee e WHERE e.employeeID = :employeeID"),
    @NamedQuery(name = "Employee.findByLastName", 
        query = "SELECT e FROM Employee e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "Employee.findByFirstName", 
        query = "SELECT e FROM Employee e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "Employee.findByBirthDate", 
        query = "SELECT e FROM Employee e WHERE e.birthDate = :birthDate"),
    @NamedQuery(name = "Employee.findByHireDate", 
        query = "SELECT e FROM Employee e WHERE e.hireDate = :hireDate"),
    @NamedQuery(name = "Employee.findByAddress", 
        query = "SELECT e FROM Employee e WHERE e.address = :address"),
    @NamedQuery(name = "Employee.findByCity", 
        query = "SELECT e FROM Employee e WHERE e.city = :city"),
    @NamedQuery(name = "Employee.findByRegion", 
        query = "SELECT e FROM Employee e WHERE e.region = :region"),
    @NamedQuery(name = "Employee.findByPostalCode", 
        query = "SELECT e FROM Employee e WHERE e.postalCode = :postalCode"),
    @NamedQuery(name = "Employee.findByCountry", 
        query = "SELECT e FROM Employee e WHERE e.country = :country"),
    @NamedQuery(name = "Employee.findByHomePhone", 
        query = "SELECT e FROM Employee e WHERE e.homePhone = :homePhone")
})
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Basic(optional = false)
    @NotNull
	@Column(name="EmployeeID")
	private Integer employeeID;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
	@Column(name="FirstName")
	private String firstName;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
	@Column(name="LastName")
	private String lastName;

    @Size(max = 30)
	@Column(name="Title")
	private String title;

    @Size(max = 25)
	@Column(name="TitleOfCourtesy")
	private String titleOfCourtesy;
    
	@Column(name="BirthDate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date birthDate;

	@Column(name="HireDate")
    @Temporal(TemporalType.DATE)
	private Date hireDate;

	@Size(max = 60)
	@Column(name="Address")
	private String address;

	@Size(max = 15)
	@Column(name="City")
	private String city;

	@Size(max = 15)
	@Column(name="Country")
	private String country;

	@Size(max = 15)
	@Column(name="Region")
	private String region;

	@Size(max = 10)
	@Column(name="PostalCode")
	private String postalCode;

	@Column(name="HomePhone")
    //@Temporal(TemporalType.TIMESTAMP)
	private String homePhone;

    @Size(max = 4)
	@Column(name="Extension")
	private String extension;

    @Size(max = 1073741823)
	@Column(name="Notes")
	private String notes;

	@Lob
	@Column(name="Photo")
	private byte[] photo;

	@Size(max = 255)
	@Column(name="PhotoPath")
	private String photoPath;

	// bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="reportsTo")
	private Collection<Employee> employees;

	// bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="ReportsTo", referencedColumnName = "EmployeeID")
	private Employee reportsTo;
    
	public Employee() {
	}

    public Employee(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public Employee(Integer employeeID, String lastName, String firstName) {
        this.employeeID = employeeID;
        this.lastName = lastName;
        this.firstName = firstName;
    }

	public Integer getEmployeeID() {
		return this.employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleOfCourtesy() {
		return this.titleOfCourtesy;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		this.titleOfCourtesy = titleOfCourtesy;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
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

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }
    
/*    
	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
*/
    
	public Collection<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

/*
	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		//employee.setEmployee(this);
		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		//employee.setEmployee(null);
		return employee;
	}
*/
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeID != null ? employeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeID == null && other.employeeID != null) || 
            (this.employeeID != null && !this.employeeID.equals(other.employeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "northwind.entity.Employees[ employeeID=" + employeeID + " ]";
    }
    
}