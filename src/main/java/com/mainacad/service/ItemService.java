package com.mainacad.service;

import java.util.List;

import com.mainacad.dao.ItemDAO;
import com.mainacad.model.Item;

public class ItemService {
	public static Item getById(Integer id) {
		return ItemDAO.getById(id);
	}
	
	public static List<Item> getAllAvailable() {
		return ItemDAO.getAllAvailable();
	}

}
