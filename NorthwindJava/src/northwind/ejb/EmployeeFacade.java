/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package northwind.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import northwind.entity.Employee;

/**
 *
 * @author jholmes
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "NorthwindJPA")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
}
