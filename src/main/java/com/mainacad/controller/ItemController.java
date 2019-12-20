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
@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		RequestDispatcher dispatcher;
		Integer userIdSelected = Integer.valueOf(req.getParameter("userId"));
		User user = UserService.getById(userIdSelected);

		String action = req.getParameter("action");
		if (action.equals("add-item-in-cart")) {
			Integer itemIdSelected = Integer.valueOf(req.getParameter("itemId"));
			Item item = ItemService.getById(itemIdSelected);
			if (item != null) {
				Cart cart = CartService.addItem(user, item);
				req.setAttribute("cart", cart);

				List<Item> items = ItemService.getAllAvailable();
				req.setAttribute("itemCollection", items);

				req.setAttribute("user", user);

				dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
			} else {
				req.setAttribute("user", user);
				dispatcher = req.getRequestDispatcher("/jsp/wrong-object-for-user.jsp");

				req.setAttribute("errorMsg", "Item doesnot exist!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("open-current-cart")) {
			Cart cart = CartService.getByUserAndOpenStatus(user);
			req.setAttribute("cart", cart);

			req.setAttribute("user", user);

			List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart);
			req.setAttribute("orderDTOCollection", orderDTOS);

			dispatcher = req.getRequestDispatcher("/jsp/cart-current.jsp");
			dispatcher.forward(req, resp);
		} else if (action.equals("to-items")) {
			req.setAttribute("user", user);
			List<Item> items = ItemService.getAllAvailable();
			req.setAttribute("itemCollection", items);

			dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
			dispatcher.forward(req, resp);
		}
	}
}