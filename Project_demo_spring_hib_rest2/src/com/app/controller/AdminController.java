package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IAdminDao;
import com.app.dao.ICustomerDao;
import com.app.pojos.Customer;
import com.app.pojos.Employee;
import com.app.pojos.MenuItems;
import com.app.pojos.Orders;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ICustomerDao custDao;
	@Autowired
	private IAdminDao adminDao;

	@GetMapping
	public ResponseEntity<?> getAllCustomers() {
		List<Customer> listOfCustomers = custDao.getCustomers();
		// System.out.println(listOfCustomers);
		if (listOfCustomers == null)
			return new ResponseEntity<String>("No Data Found", HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Customer>>(listOfCustomers, HttpStatus.OK);
	}

	@PostMapping
	public void registerNewCustomer(@RequestBody Customer c) {
		custDao.addCustomer(c);
	}

	@GetMapping("/getcust/{no}")
	public ResponseEntity<?> getCustomersDetails(@PathVariable int no) {
		List<Object> list = new ArrayList<Object>();
		Customer cust = custDao.getCustomersAllDetails(no);
		list.add(cust);
		list.add(cust.getOrderDetails());
		list.add(cust.getOrders());
		if (cust == null)
			return new ResponseEntity<String>("No Data Found", HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Object>>(list, HttpStatus.OK);
	}

	@GetMapping("/orderdetails")
	public ResponseEntity<?> getOnlineOrderDetails() {
		List<Orders> list = adminDao.getOnlineOrderStatus();
		if (list == null)
			return new ResponseEntity<String>("No Data Found", HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Orders>>(list, HttpStatus.OK);
	}

	@PostMapping("/addmenu")
	public ResponseEntity<?> addMenuItem(MenuItems m) {
		System.out.println(m);
		try {
			adminDao.addMenuItem(m);
			return new ResponseEntity<String>("Menu Added", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Menu Not Added", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/showemp")
	public ResponseEntity<?> showEmployees() {
		try {
			List<Employee> list = adminDao.showAllEmployees();
			if (list != null)
				return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
	@GetMapping("/getemp/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable int id) {
		try {
			Employee e = adminDao.getEmployee(id);
			if (e != null)
				return new ResponseEntity<Employee>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
}
