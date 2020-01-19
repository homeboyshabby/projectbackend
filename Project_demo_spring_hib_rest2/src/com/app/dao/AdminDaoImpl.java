package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.MenuItems;
import com.app.pojos.Orders;
import com.app.pojos.UserType;

@Repository
@Transactional
public class AdminDaoImpl implements IAdminDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public List<Orders> getOnlineOrderStatus() {
		String jpql = "select o from Orders o";
		return sf.getCurrentSession().createQuery(jpql, Orders.class).getResultList();
	}

	@Override
	public Employee authEmployee(String email, String password) throws Exception {
		String jpql = "select e from Employee e where e.employeeEmail = :email and e.employeePassword = :password";
		return sf.getCurrentSession().createQuery(jpql, Employee.class).setParameter("email", email)
				.setParameter("password", password).getSingleResult();
	}

	@Override
	public void addMenuItem(MenuItems m) throws Exception {
		sf.getCurrentSession().persist(m);
	}

	@Override
	public List<Employee> showAllEmployees() throws Exception {
		String jpql = "select e from Employee e where e.role = '"+ UserType.valueOf("MANAGER") + "' or e.role= '"+ UserType.valueOf("WAITER") + "'";
		return sf.getCurrentSession().createQuery(jpql,Employee.class).getResultList();
	}

	@Override
	public Employee getEmployee(Integer id) throws Exception {
		return sf.getCurrentSession().get(Employee.class, id);
	}

	
}
