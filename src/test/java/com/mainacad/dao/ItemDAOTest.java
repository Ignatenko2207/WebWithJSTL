package com.mainacad.dao;

import com.mainacad.dao.model.ItemDTO;
import com.mainacad.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {
    private static final Long CURRENT_TIME = new Date().getTime();
    List<Item> items;
    List<User> users;
    List<Cart> carts;
    List<Order> orders;

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
    void saveTest() {
        Item item = new Item("name_0", "code_0", 1, 10);
        items.add(item);

        ItemDAO.save(item);
        assertNotNull(item.getId());
    }

    @Test
    void getAllTest() {
        Item item1 = new Item("name_1", "code_1", 10, 100);
        Item item2 = new Item("name_2", "code_2", 20, 200);
        items.add(item1);
        items.add(item2);
        ItemDAO.save(item1);
        ItemDAO.save(item2);

        List<Item> targetItems = ItemDAO.getAll();
        assertTrue(targetItems.size() >= 2);

        int count = 0;
        for (Item each:targetItems){
            if ((item1.getId()).equals(each.getId())) {count++;}
            if ((item2.getId()).equals(each.getId())) {count++;}
        }
        assertEquals(2,count);
    }

    @Test
    void getAllAvailableTest() {
        Item item3 = new Item("name_3", "code_3", 30, 300);
        Item item4 = new Item("name_4", "code_4", 40, 0);
        items.add(item3);
        items.add(item4);
        ItemDAO.save(item3);
        ItemDAO.save(item4);

        List<Item> targetItems = ItemDAO.getAllAvailable();
        assertTrue(targetItems.size() >= 1);

        boolean isInCollectionItem3 = false;
        boolean isInCollectionItem4 = false;
        for (Item each:targetItems){
            if ((item3.getId()).equals(each.getId())) {isInCollectionItem3 = true;}
            if ((item4.getId()).equals(each.getId())) {isInCollectionItem4 = true;}
        }
        assertTrue(isInCollectionItem3);
        assertTrue(!isInCollectionItem4);
    }

    @Test
    void getAllByUserAndPeriodTest() {
        Item item1 = new Item("name_1", "code_3", 30, 300);
        Item item2 = new Item("name_2", "code_4", 40, 0);
        Item item3 = new Item("name_2", "code_4", 40, 0);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        ItemDAO.save(item1);
        ItemDAO.save(item2);
        ItemDAO.save(item3);
        assertNotNull(item1.getId());
        assertNotNull(item2.getId());
        assertNotNull(item3.getId());

        User userOk = new User("login_item1", "pass0", "first_name0", "last_name0", "email0", "phone0");
        User userNotOk = new User("login_item2", "pass0", "first_name0", "last_name0", "email0", "phone0");
        UserDAO.save(userOk);
        UserDAO.save(userNotOk);
        users.add(userOk);
        users.add(userNotOk);
        assertNotNull(userOk.getId());
        assertNotNull(userNotOk.getId());

        Long periodFrom = CURRENT_TIME - 100;
        Long periodTo = CURRENT_TIME;
        Long timeOk = CURRENT_TIME - 50;
        Long timeNotOk = CURRENT_TIME - 200;

        Cart cartOk = new Cart(Status.CLOSED, userOk, timeOk);
        Cart cartNotOk1 = new Cart(Status.CLOSED, userNotOk, timeNotOk);
        Cart cartNotOk2 = new Cart(Status.OPEN, userOk, timeNotOk);
        Cart cartNotOk3 = new Cart(Status.CLOSED, userOk, timeNotOk);
        CartDAO.save(cartOk);
        CartDAO.save(cartNotOk1);
        CartDAO.save(cartNotOk2);
        CartDAO.save(cartNotOk3);
        carts.add(cartOk);
        carts.add(cartNotOk1);
        carts.add(cartNotOk2);
        carts.add(cartNotOk3);
        assertNotNull(cartOk.getId());
        assertNotNull(cartNotOk1.getId());
        assertNotNull(cartNotOk2.getId());
        assertNotNull(cartNotOk3.getId());

        Order orderOk1 = new Order(item1, cartOk, 50);
        Order orderOk2 = new Order(item2, cartOk, 50);
        Order orderNotOk1 = new Order(item1, cartNotOk1, 50);
        Order orderNotOk2 = new Order(item2, cartNotOk2, 50);
        Order orderNotOk3 = new Order(item3, cartNotOk2, 50);
        Order orderNotOk4 = new Order(item1, cartNotOk3, 50);
        OrderDAO.save(orderOk1);
        OrderDAO.save(orderOk2);
        OrderDAO.save(orderNotOk1);
        OrderDAO.save(orderNotOk2);
        OrderDAO.save(orderNotOk3);
        OrderDAO.save(orderNotOk4);
        orders.add(orderOk1);
        orders.add(orderOk2);
        orders.add(orderNotOk1);
        orders.add(orderNotOk2);
        orders.add(orderNotOk3);
        orders.add(orderNotOk4);
        assertNotNull(orderOk1.getId());
        assertNotNull(orderOk2.getId());
        assertNotNull(orderNotOk1.getId());
        assertNotNull(orderNotOk2.getId());
        assertNotNull(orderNotOk3.getId());
        assertNotNull(orderNotOk4.getId());

        List<ItemDTO> itemDTOS = ItemDAO.getAllByUserAndPeriod(userOk, periodFrom, periodTo);
        assertTrue(itemDTOS.size() >= 2);

        boolean isInCollectionItem1 = false;
        boolean isInCollectionItem2 = false;
        boolean isInCollectionItem3 = false;
        for (ItemDTO each:itemDTOS){
            if ((item1.getId()).equals(each.getId())) {isInCollectionItem1 = true;}
            if ((item2.getId()).equals(each.getId())) {isInCollectionItem2 = true;}
            if ((item3.getId()).equals(each.getId())) {isInCollectionItem3 = true;}
        }
        assertTrue(isInCollectionItem1);
        assertTrue(isInCollectionItem2);
        assertTrue(!isInCollectionItem3);
    }

    @Test
    void getByIdTest() {
        Item item = new Item("name_5", "code_5", 50, 500);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Item targetItem = ItemDAO.getById(item.getId());
        assertNotNull(targetItem);
        assertAll("Should equal all fields",
                ()->assertEquals(targetItem.getName(), item.getName()),
                ()->assertEquals(targetItem.getCode(), item.getCode()),
                ()->assertEquals(targetItem.getPrice(), item.getPrice()),
                ()->assertEquals(targetItem.getAvailability(), item.getAvailability()));
    }

    @Test
    void updateTest() {
        Item item = new Item("name_6", "code_6", 60, 600);
        ItemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        item.setName("name_new");

        Item targetItem = ItemDAO.update(item);
        assertNotNull(targetItem);
        assertEquals("name_new", targetItem.getName());
    }

    @Test
    void getAndDeleteTest() {
        Item item = new Item("name_7", "code_7", 70, 700);
        ItemDAO.save(item);
        assertNotNull(item.getId());

        Item targetItem = ItemDAO.getById(item.getId());
        assertNotNull(targetItem);

        ItemDAO.delete(item.getId());

        Item deletedItem = ItemDAO.getById(targetItem.getId());
        assertNull(deletedItem);
    }
}


//        select * from orders
//        select * from carts
//        select * from users
//        select * from items
//
//
//        delete from orders
//        delete from carts
//        delete from users
//        delete from items