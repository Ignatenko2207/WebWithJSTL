package com.mainacad.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mainacad.dao.CartDAO;
import com.mainacad.dao.ItemDAO;
import com.mainacad.dao.OrderDAO;
import com.mainacad.dao.UserDAO;
import com.mainacad.dao.model.OrderDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import com.mainacad.model.Status;
import com.mainacad.model.User;

public class OrderServiceTest {

	private static final Long CURRENT_TIME = new Date().getTime();
	private static List<Cart> carts;
	private static List<User> users;
	private static List<Item> items;
	private static List<Order> orders;

	@BeforeEach
	void setUp() {
		carts = new ArrayList<>();
		users = new ArrayList<>();
		items = new ArrayList<>();
		orders = new ArrayList<>();
	}

	@AfterEach
	void tearDown() {
		orders.forEach(it -> OrderDAO.delete(it.getId()));
		carts.forEach(it -> CartDAO.delete(it.getId()));
		users.forEach(it -> UserDAO.delete(it.getId()));
		items.forEach(it -> ItemDAO.delete(it.getId()));
	}

	@Test
	void getByCartWithItemTest() {
		User user = new User("login0", "pass0", "first_name0", "last_name0", "email0", "phone0");
		UserDAO.save(user);
		users.add(user);
		assertNotNull(user.getId());

		Cart cart = new Cart(Status.OPEN, user, CURRENT_TIME);
		Cart cart2 = new Cart(Status.OPEN, user, CURRENT_TIME);
		CartDAO.save(cart);
		CartDAO.save(cart2);
		carts.add(cart);
		carts.add(cart2);
		assertNotNull(cart.getId());
		assertNotNull(cart2.getId());

		Item item1 = new Item("name_5", "code_5", 50, 500);
		Item item2 = new Item("name_5", "code_5", 50, 500);
		ItemDAO.save(item1);
		ItemDAO.save(item2);
		items.add(item1);
		items.add(item2);
		assertNotNull(item1.getId());
		assertNotNull(item2.getId());

		Order order1 = new Order(item1, cart, 50);		
		Order order2 = new Order(item2, cart, 50);
		Order order3 = new Order(item2, cart2, 50);
		OrderDAO.save(order1);
		OrderDAO.save(order2);
		OrderDAO.save(order3);
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		assertNotNull(order1.getId());
		assertNotNull(order2.getId());
		assertNotNull(order3.getId());

		
		// TODO
		Integer targetOrderIdOk = OrderService.getByCartWithItem(cart, item1);
		Integer targetOrderIdNotOk = OrderService.getByCartWithItem(cart2, item1);
		
		assertEquals(order1.getId(), targetOrderIdOk);
		
		assertNotEquals(order1.getId(), targetOrderIdNotOk);
	}
}
