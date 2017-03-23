package northwind.web;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import northwind.ejb.CustomerFacade;
import northwind.entity.Customer;
import northwind.util.JsfUtil;
import northwind.util.PaginationHelper;

/**
 * Managed Beans
 * https://dzone.com/articles/comparing-jsf-beans-cdi-beans
 *
 * JSF managed beans
 * CDI managed beans
 * EJB managed beans
 * And even Servlets are managed beans because they are instantiated and destroyed
 * by a servlet container.
 *
 * JSF Managed Beans
 * In short, don’t use them if you are developing for Java EE 6 and using CDI.
 * They provide a simple mechanism for dependency injection and defining backing beans
 * for web pages, but they are far less powerful than CDI beans.
 *
 * EJBs predate CDI beans and are somewhat similar to CDI beans and in other ways very different.
 * Primarily, the differences between CDI beans and EJBs is that EJBs are:
 * - Transactional
 * - Remote or local
 * - Able to passivate stateful beans freeing up resources
 * - Able to make use of timers
 * - Can be asynchronous
 *
 * The two types of EJBs are called stateless and stateful.
 * Stateless EJBs can be thought of as thread safe single-use beans that don’t maintain
 * any state between two web requests. Stateful EJBs do hold state and can be created and
 * sit around for as long as they are needed until they are disposed of.
 *
 * CDI Beans
 * CDI is the bean management and dependency injection framework that was released as part of
 * Java EE 6 and it includes a complete, comprehensive managed bean facility.
 * CDI beans are more advanced and flexible than simple JSF managed beans.
 *
 * In general, you should use CDI beans unless you need the advanced functionality
 * available in the EJBs such as transactional functions.
 */

/**
 * Define CDI bean
 * @author jholmes
 */

@Named("customersController")
@SessionScoped
public class CustomersController implements Serializable {

    private int DEFAULT_PAGE_SIZE = 10;

    private static final Logger logger = Logger.getLogger("northwind.web.CustomersController");

    private Customer current;
    private DataModel items = null;
    @EJB
    private CustomerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int pageSize;

    public CustomersController() {
    }

    public int getPageSize() {
    	if (pageSize <= 0) {
    		pageSize = DEFAULT_PAGE_SIZE;
    	}
    	return pageSize;
    }

    public void setPageSize(String sPageSize) {
		try {
			pageSize = Integer.parseInt(sPageSize);
		} catch (Exception nfe) { // null or bad format
			pageSize = DEFAULT_PAGE_SIZE;
		}
    }

    public void editPageSize(String param) {
		try {
			pageSize = Integer.parseInt(param);
		} catch (Exception nfe) { // null or bad format
			pageSize = DEFAULT_PAGE_SIZE;
		}
        recreatePagination();
        recreateModel();
        //logger.warning("editPageSize: pageSize = " + pageSize);
    }

    public Customer getSelected() {
        if (current == null) {
            current = new Customer();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CustomerFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(getPageSize()) {
	            @Override
	            public int getItemsCount() {
	                return getFacade().count();
	            }

	            @Override
	            public DataModel createPageDataModel() {
	                return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
	            }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Customer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Customer();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Customer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Customer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomersDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Customer getCustomer(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Customer.class)
    public static class CustomersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomersController controller = (CustomersController) facesContext.getApplication().
        		getELResolver().
                getValue(facesContext.getELContext(), null, "customersController");
            return controller.getCustomer(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Customer) {
                Customer o = (Customer) object;
                return getStringKey(o.getCustomerID());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Customer.class.getName());
            }
        }

    }

}
