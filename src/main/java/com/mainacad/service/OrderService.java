package com.mainacad.service;

import java.util.List;

import com.mainacad.dao.OrderDAO;
import com.mainacad.dao.model.OrderDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;

public class OrderService {
	public static Order create(Order order) {
		return OrderDAO.save(order);
	}

	public static List<Order> getAllByCart(Cart cart) {
		return OrderDAO.getAllByCart(cart);
	}

	public static Order getByCartWithItem(Cart cart, Item item) {
		List<Order> orders = getAllByCart(cart);

		if (orders.isEmpty()) {
			return null;
		} else {
			for (Order each : orders) {
				if (each.getItem().equals(item)) {
					return each;
				}
			}
		}
		return null;
	}
	
	public static Order updateAmount(Order order, Integer amount) {
		return OrderDAO.updateAmount(order, amount); 
	}

	public static List<OrderDTO> getAllDTOByCard(Cart cart) {
		return OrderDAO.getAllDTOByCard(cart);
	}
}
