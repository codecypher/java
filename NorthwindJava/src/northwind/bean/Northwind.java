/**
 * JDBC Tutorial
 * http://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
 */

package northwind.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean
public class Northwind {

	List<Customer> customers;

	private final String dbUser = "nwinduser";
	private final String dbPassword = "nwinduser";

	// Need to create NorthwindPool under JDBC Connection Pools.
	// Need to create jdbc/Northwind under JDBC Resources on server.
	private final String dbUrl = "jdbc/Northwind";

	// Get database connection
	public Connection getConnection() {
		Context ctx = null;
		DataSource ds = null;
		Connection conn = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(dbUrl);
			conn = ds.getConnection(dbUser, dbPassword);
			
			// Show some driver info
			//DatabaseMetaData dbMetaData = conn.getMetaData();
			//String productName = dbMetaData.getDatabaseProductName();
			//System.out.println("Database: " + productName);
			//String productVersion = dbMetaData.getDatabaseProductVersion();
			//System.out.println("Version: " + productVersion);		

			if (ctx != null) ctx.close();		
		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException ne) {
			System.out.println("NamingException: " + ne.getMessage());
		}
		return(conn);
	}

	// Find Customer by customerID
	public Customer findCustomer(String customerID) {
		int numRows = 0;
		Customer customer = null;
		
		try {
			// Get connection 
			Connection conn = getConnection();
		
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
													   ResultSet.CONCUR_READ_ONLY);
			
			// Send query to database and store results.
			String query = "SELECT * FROM Customers WHERE CustomerID = '" + customerID + "'";
			ResultSet rs = statement.executeQuery(query);
			
			// Get number of rows
		    rs.last();
		    numRows = rs.getRow();
		    rs.beforeFirst();

		    rs.next();
		    if (numRows == 1) {
				customer = new Customer(
					rs.getString("CustomerID"),
					rs.getString("CompanyName"),
					rs.getString("ContactName"),
					rs.getString("ContactTitle"),
					rs.getString("Address"),
					rs.getString("City"),
					rs.getString("Region"),
					rs.getString("PostalCode"),
					rs.getString("Country"),
					rs.getString("Phone"),
					rs.getString("Fax"));
			}			
			// Close the connection
			conn.close();			
		} catch (SQLException e) {
			System.err.printf("findCustomer: %s %s%n", e.getErrorCode(), e.getMessage());
		}
		return (customer);
	}

	// Get list of all Customers in database
	public List<Customer> getCustomers() {
		try {
			int numRows = 0;

			System.out.println("Calling getCustomers");

			// Get connection 
			Connection conn = getConnection();

			// Statement statement = conn.createStatement();  // creates forward-only resultset
			
			// Create scrollable resultset
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
													   ResultSet.CONCUR_READ_ONLY);
			// Send query to database and store results.
			String query = "SELECT * FROM Customers";
			ResultSet rs = statement.executeQuery(query);
		
			// Get number of rows
		    rs.last();
		    numRows = rs.getRow();
		    rs.beforeFirst();

		    // Get column names.
			// getHeader(rs, data, numRows);
					
			// Get rows of data
			customers = getCustomerRows(rs);
			
			// Close the connection
			conn.close();
		} catch (SQLException e) {
			System.err.printf("getCustomers: %s %s%n" + e.getErrorCode() + e.getMessage());
		}
		return (customers);
	}

	// Get list of column names
	protected void getHeader(ResultSet rs, String[][] data, int numRows) throws SQLException {
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		int numColumns = resultSetMetaData.getColumnCount();
		data = new String[numRows][numColumns];
		// Column index starts at 1 (a la SQL), not 0 (a la Java).
		for (int i = 1; i <= numColumns; i++) {
			data[0][i] = resultSetMetaData.getColumnName(i);
			System.out.print(resultSetMetaData.getColumnName(i));
		}
	}

	protected List<Customer> getCustomerRows(ResultSet rs) throws SQLException {
		List<Customer> customers = new ArrayList();

		// Step through each row in the result set and print cells
		while (rs.next()) {			
			//ResultSetMetaData resultSetMetaData = rs.getMetaData();
			//int columnCount = resultSetMetaData.getColumnCount();
			// Column index starts at 1
			//for (int j = 1; j <= columnCount; j++) {
			//	rs.getString(j);
			//}
			//System.out.println("CustomerID " + rs.getString("CustomerID"));

			Customer customer = new Customer(
				rs.getString("CustomerID"),
				rs.getString("CompanyName"),
				rs.getString("ContactName"),
				rs.getString("ContactTitle"),
				rs.getString("Address"),
				rs.getString("City"),
				rs.getString("Region"),
				rs.getString("PostalCode"),
				rs.getString("Country"),
				rs.getString("Phone"),
				rs.getString("Fax"));
			customers.add(customer);
			
			//out.printf("  <td>%d</td>", resultSet.getInt("id"));
			//out.printf("  <td>%s</td>", resultSet.getString("firstname"));
		}
		return (customers);
	}

	// Save Customer to database
	public boolean saveCustomer(Customer customer) {
		try {
			// Get connection 
			Connection conn = getConnection();

			// create forward-only resultset
			Statement statement = conn.createStatement();
			
			// Send query to database and store results.
			String query = "UPDATE Customers SET CompanyName = '" + customer.getCompanyName() + "', " +
				"ContactName ='" + customer.getContactName() + "', " +
				"ContactTitle ='" + customer.getContactTitle() + "', " +
				"Address ='" + customer.getAddress() + "', " +
				"City ='" + customer.getCity() + "', " +
				"Region ='" + customer.getRegion() + "', " +
				"PostalCode ='" + customer.getPostalCode() + "', " +
				"Country ='" + customer.getCountry() + "', " +
				"Phone ='" + customer.getPhone() + "', " +
				"Fax ='" + customer.getFax() + "' " +
				"WHERE CustomerID = '" + customer.getCustomerID() + "'";
			int result = statement.executeUpdate(query);
			
			// Close the connection
			conn.close();			
		} catch (SQLException e) {
			System.err.printf("saveCustomer: %s %s%n", e.getErrorCode(), e.getMessage());
			return (false);  // failure
		}
		return (true);  // success
	}

}
