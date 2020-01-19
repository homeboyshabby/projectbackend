package com.app.dao;

import java.util.List;

import com.app.pojos.Customer;
import com.app.pojos.MenuItems;
import com.app.pojos.Orders;
import com.app.pojos.Reservation;
import com.sun.org.apache.xpath.internal.operations.Bool;

public interface ICustomerDao {
	Customer authenticateUser(String email,String password) throws Exception;
	List<Customer> getCustomers();
	Customer getCustomersAllDetails(int cid);
	void addCustomer(Customer c);
	List<Orders> showAllOrders(int custId);
	Customer getMyDetails(int cid);
	Boolean setMyDetails(Customer c);
	Boolean chnageMyPassword(String email,String oldPass,String newPass);
	List<Reservation> getMyReservations(int custId);
	Boolean deleteMyReservation(int resId);
	MenuItems getSelectedMenu(String menuName);
	void addMyReservations(Reservation r,int id);
}
