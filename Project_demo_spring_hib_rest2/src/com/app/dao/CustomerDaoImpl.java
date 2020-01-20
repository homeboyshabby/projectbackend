package com.app.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.Bills;
import com.app.pojos.Customer;
import com.app.pojos.MenuItems;
import com.app.pojos.OrderDetails;
import com.app.pojos.OrderType;
import com.app.pojos.Orders;
import com.app.pojos.Reservation;
import com.app.pojos.User;

@Repository
@Transactional
public class CustomerDaoImpl implements ICustomerDao {
	@Autowired
	private SessionFactory sf;

	@Override
	public List<Customer> getCustomers() {
		String jpql = "select cust from Customer cust left outer join fetch cust.orders"
				+ " left outer join fetch cust.orderDetails";
		return sf.getCurrentSession().createQuery(jpql, Customer.class).getResultList();
	}	
	
	@Override
	public List<Customer> getCurrentCustomers() {
		String jpql = "select cust from Customer cust";
		return sf.getCurrentSession().createQuery(jpql, Customer.class).getResultList();
	}
	
	@Override
	public void addCustomer(Customer c) {
		sf.getCurrentSession().persist(c);
	}

//	@Override
//	public Customer getCustomersAllDetails(int cid) {
//		Customer cust = sf.getCurrentSession().load(Customer.class, cid);
//		//Hibernate.initialize(cust.getOrderDetails());
//		//System.out.println(cust);
//		return cust;
//	}
	
	public Customer getCustomersAllDetails(int cid) {
		String jpql = "select cust from Customer cust left outer join fetch cust.orders"
				+ " left outer join fetch cust.orderDetails where cust.id = :id";
		return sf.getCurrentSession().createQuery(jpql, Customer.class).setParameter("id", cid).getSingleResult();
	}

	@Override
	public Customer authenticateUser(String email, String password) throws Exception {
		String jpql = "select c from Customer c where c.email = :email and c.password = :password";
		return sf.getCurrentSession().createQuery(jpql, Customer.class).setParameter("email", email)
				.setParameter("password", password).getSingleResult();

	}

	public List<Orders> showAllOrders(int custId) {
		// System.out.println(custId);
		String jpql = "select o from Orders o where o.custId.id = :id";
		return sf.getCurrentSession().createQuery(jpql, Orders.class).setParameter("id", custId).getResultList();
	}

	@Override
	public Customer getMyDetails(int cid) {
		return sf.getCurrentSession().get(Customer.class, cid);
	}

	@Override
	public Boolean setMyDetails(Customer c) {
		try {
			String email = c.getEmail();
			String jpql = "select c from Customer c where c.email  = :email";
			Customer currentUser = sf.getCurrentSession().createQuery(jpql, Customer.class).setParameter("email", email)
					.getSingleResult();
			currentUser.setName(c.getName());
			currentUser.setPhoneNumber(c.getPhoneNumber());
			currentUser.setCustAddress(c.getAddress().getFlatNo(), c.getAddress().getBuildingName(),
					c.getAddress().getArea(), c.getAddress().getCity());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Boolean chnageMyPassword(String email, String oldPass, String newPass) {
		try {
			String jpql = "select c from Customer c where c.email = :email and c.password = :oldpass";
			Customer c = sf.getCurrentSession().createQuery(jpql, Customer.class).setParameter("email", email)
					.setParameter("oldpass", oldPass).getSingleResult();
			c.setPassword(newPass);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Reservation> getMyReservations(int custId) {
		String jpql = "select r from Reservation r where r.custId.id = :id";
		return sf.getCurrentSession().createQuery(jpql, Reservation.class).setParameter("id", custId).getResultList();
	}

	@Override
	public Boolean deleteMyReservation(int resId) {
		try {
			sf.getCurrentSession().remove(sf.getCurrentSession().get(Reservation.class, resId));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MenuItems getSelectedMenu(String menuName) {
		try
		{
			System.out.println(menuName);
			String jpql = "select m from MenuItems m where m.itemDesc = :name";
			return sf.getCurrentSession().createQuery(jpql,MenuItems.class).setParameter("name", menuName).getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void addMyReservations(Reservation r,int id) {
		r.setCustId(new Customer(id));
		sf.getCurrentSession().persist(r);
	}

	@Override
	public Orders getOrderId(int custId) {
		Orders order = new Orders(new Date(System.currentTimeMillis()),OrderType.valueOf("HOMEDELIVERY"));
		order.setCustId(new Customer(custId));
		System.err.println(order.toString());
		sf.getCurrentSession().persist(order);
		//String jpql = "select o from Orders o where o.custId.id = :id";
		String jpql = "select o from Orders o where o.orderId = (select MAX(o.orderId) from o) ";
		return sf.getCurrentSession().createQuery(jpql, Orders.class).getSingleResult();
	}

	@Override
	public void addOrderDetails(OrderDetails o,int id) {
		o.setCustId(new Customer(id));
		sf.getCurrentSession().persist(o);	
	}

	@Override
	public void generateBill(Bills b) {
		b.setBillDate(new Date(System.currentTimeMillis()));
		sf.getCurrentSession().persist(b);
	}

}
//"select cust from Customer cust join fetch cust.reservations r join fetch cust.orders o where cust.custId = :cid";
