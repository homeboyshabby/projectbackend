package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.DiningTable;
import com.app.pojos.MenuItems;

@Repository
@Transactional
public class WaiterDaoImpl implements IWaiterDao {
	@Autowired
	private SessionFactory sf;
	@Override
	// a = table available
	//na = table not available
	public List<DiningTable> checkTableStatus() {
		String jpql = "select d from DiningTable d";
		return sf.getCurrentSession().createQuery(jpql,DiningTable.class).getResultList();
	}
	@Override
	public List<MenuItems> showMenu() {
		String jpql = "select i from MenuItems i";
		return sf.getCurrentSession().createQuery(jpql,MenuItems.class).getResultList();
	}
	@Override
	public void takeOrder() {
		// TODO Auto-generated method stub
		
	}

}
