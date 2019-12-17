package com.mainacad.service;

import com.mainacad.dao.ItemDAO;
import com.mainacad.model.Item;

public class ItemService {
	public static Item getById(Integer id) {
		return ItemDAO.getById(id);
	}

}
