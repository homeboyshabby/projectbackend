package com.app.dao;
import java.util.List;

import com.app.pojos.*;
public interface IWaiterDao {
	List<DiningTable> checkTableStatus();
	List<MenuItems> showMenu();
	void takeOrder();
}
