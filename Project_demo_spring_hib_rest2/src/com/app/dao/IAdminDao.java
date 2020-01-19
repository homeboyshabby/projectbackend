package com.app.dao;
import java.util.List;

import com.app.pojos.*;
public interface IAdminDao {
	List<Orders> getOnlineOrderStatus();
	Employee authEmployee(String email,String password) throws Exception;
	void addMenuItem(MenuItems m) throws Exception;
	List<Employee> showAllEmployees() throws Exception;
	Employee getEmployee(Integer id) throws Exception;
}
