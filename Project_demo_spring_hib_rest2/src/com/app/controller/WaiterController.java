package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IWaiterDao;
import com.app.pojos.Customer;
import com.app.pojos.DiningTable;
import com.app.pojos.MenuItems;

@RestController
@CrossOrigin
@RequestMapping("/waiter")
public class WaiterController {
	@Autowired
	private IWaiterDao waiterDao;
	
	@GetMapping("/tables")
	public ResponseEntity<?> getAllCustomers()
	{
		List<DiningTable> listOfTables = waiterDao.checkTableStatus();
		if(listOfTables == null)
			return new ResponseEntity<String> ("No Data Found",HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<DiningTable>> (listOfTables,HttpStatus.OK);
	}
	@GetMapping("/menu")
	public ResponseEntity<?> getAllMenu()
	{
		List<MenuItems> menu = waiterDao.showMenu();
		if(menu == null)
			return new ResponseEntity<String> ("No Data Found",HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<MenuItems>> (menu,HttpStatus.OK);
	}
}
