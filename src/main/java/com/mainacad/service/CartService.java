package com.mainacad.service;

import java.util.Date;

import com.mainacad.dao.CartDAO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import com.mainacad.model.Status;
import com.mainacad.model.User;

public class CartService {
	public static Cart updateStatus(Cart cart, Status status) {
		return CartDAO.updateStatus(cart, status);
	}
	
	public static Cart addItem(User user, Item item) {
		Cart cart = CartDAO.getByUserAndOpenStatus(user);

		if (cart == null) {
			Long currentTime = new Date().getTime();
			cart = new Cart(Status.OPEN, user, currentTime);
			CartDAO.save(cart);
		}
		
		Order order = OrderService.getByCartWithItem(cart, item);
		if(order == null) {
			order = new Order(item, cart, 1);
			OrderService.create(order);
		}
		else {OrderService.updateAmount(order, order.getAmount()+1);}		
		return cart;
	}
}
