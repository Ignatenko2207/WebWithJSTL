package com.mainacad.dao;

import com.mainacad.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {
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
    void save() {
        User user = new User("login0", "pass0", "first_name0", "last_name0", "email0", "phone0");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(Status.OPEN, user, CURRENT_TIME);
        CartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order = new Order(item, cart, 50);
        OrderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());
    }

    @Test
    void getAllByCart() {
        User user = new User("login0", "pass0", "first_name0", "last_name0", "email0", "phone0");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(Status.OPEN, user, CURRENT_TIME);
        Cart cartNotOk = new Cart(Status.OPEN, user, CURRENT_TIME);
        CartDAO.save(cart);
        CartDAO.save(cartNotOk);
        carts.add(cart);
        carts.add(cartNotOk);
        assertNotNull(cart.getId());
        assertNotNull(cartNotOk.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order1 = new Order(item, cart, 50);
        Order order2 = new Order(item, cart, 50);
        Order order3 = new Order(item, cartNotOk, 50);
        OrderDAO.save(order1);
        OrderDAO.save(order2);
        OrderDAO.save(order3);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        assertNotNull(order1.getId());
        assertNotNull(order2.getId());
        assertNotNull(order3.getId());

        List<Order> targetOrders = OrderDAO.getAllByCart(cart);
        assertTrue(targetOrders.size() >= 2);

        int count = 0;
        for (Order each:targetOrders){
            if ((order1.getId()).equals(each.getId())) {count++;}
            if ((order2.getId()).equals(each.getId())) {count++;}
            if ((order3.getId()).equals(each.getId())) {count++;}
        }
        assertEquals(2,count);

    }

    @Test
    void getById() {
        User user = new User("login0", "pass0", "first_name0", "last_name0", "email0", "phone0");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(Status.OPEN, user, CURRENT_TIME);
        CartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order = new Order(item, cart, 50);
        OrderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());

        Order targetOrder = OrderDAO.getById(order.getId());
        assertNotNull(targetOrder);
        assertNotNull(targetOrder.getItem());
        assertNotNull(targetOrder.getItem().getId());
        assertNotNull(targetOrder.getCart());
        assertNotNull(targetOrder.getCart().getId());
    }

    @Test
    void getAndDelete() {
        User user = new User("login0", "pass0", "first_name0", "last_name0", "email0", "phone0");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = new Cart(Status.OPEN, user, CURRENT_TIME);
        CartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = new Item("name_5", "code_5", 50, 500);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order = new Order(item, cart, 50);
        OrderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());

        Order targetOrder = OrderDAO.getById(order.getId());
        assertNotNull(targetOrder);

        OrderDAO.delete(order.getId());

        Order deletedOrder = OrderDAO.getById(targetOrder.getId());
        assertNull(deletedOrder);
    }
}