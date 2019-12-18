package com.mainacad.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mainacad.dao.ItemDAO;
import com.mainacad.dao.UserDAO;
import com.mainacad.dao.model.OrderDTO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.User;
import com.mainacad.service.CartService;
import com.mainacad.service.ItemService;
import com.mainacad.service.OrderService;
import com.mainacad.service.UserService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/user")
public class UserController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if (action.equals("login")) {
			String login = req.getParameter("login");
			String password = req.getParameter("password");

			User user = UserService.getByLoginAndPassword(login, password);
			RequestDispatcher dispatcher;
			if (user != null) {
				List<Item> items = ItemService.getAllAvailable();
				dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
				req.setAttribute("user", user);
				req.setAttribute("itemCollection", items);
			} else {
				dispatcher = req.getRequestDispatcher("/jsp/wrong-auth.jsp");
				req.setAttribute("errorMsg", "Login or password are wrong!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("register")) {
			String login = req.getParameter("login");
			String password = req.getParameter("password");
			String firstName = req.getParameter("fname");
			String lastName = req.getParameter("lname");
			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			User user = new User(login, password, firstName, lastName, email, phone);

			User savedUser = UserDAO.save(user);
			RequestDispatcher dispatcher;
			if (savedUser.getId() != null) {
				dispatcher = getServletContext().getRequestDispatcher("/jsp/items.jsp");
				req.setAttribute("user", savedUser);
			} else {
				dispatcher = getServletContext().getRequestDispatcher("/jsp/items.jsp");
				req.setAttribute("errorMsg", "Login or password are wrong!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("add-item-in-cart")) {
			Integer userIdSelected = Integer.valueOf(req.getParameter("userId"));
			Integer itemIdSelected = Integer.valueOf(req.getParameter("itemId"));

			User user = UserService.getById(userIdSelected);
			Item item = ItemService.getById(itemIdSelected);
			Cart cart = CartService.addItem(user, item);

			RequestDispatcher dispatcher;
			if (user != null) {
				dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
				req.setAttribute("cart", cart);
				List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart); ////
				List<Item> items = ItemService.getAllAvailable();
				req.setAttribute("user", user);
				req.setAttribute("itemCollection", items);
				req.setAttribute("orderDTOCollection", orderDTOS); //////
				
				
			} else {
                dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
				req.setAttribute("errorMsg", "Login or password are wrong!");
			}
			dispatcher.forward(req, resp);
		}
	}

}
