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
import com.mainacad.model.Status;
import com.mainacad.model.User;
import com.mainacad.service.CartService;
import com.mainacad.service.ItemService;
import com.mainacad.service.OrderService;
import com.mainacad.service.UserService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/cart")
public class CartController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		RequestDispatcher dispatcher;
		Integer userIdSelected = Integer.valueOf(req.getParameter("userId"));
		User user = UserService.getById(userIdSelected);

		String action = req.getParameter("action");
		if (action.equals("do-cart-to-be-closed")) {
			Integer cartIdSelected = Integer.valueOf(req.getParameter("cartId"));
			Cart cart = CartService.getById(cartIdSelected);
			Cart cartStatus1 = CartService.updateStatus(cart, Status.TO_BE_CLOSED);
			if (cartStatus1 != null) {
				req.setAttribute("user", user);
				req.setAttribute("cart", cart);
				
				List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart);
				req.setAttribute("orderDTOCollection", orderDTOS);

				dispatcher = req.getRequestDispatcher("/jsp/cart-current.jsp");
			} else {
				req.setAttribute("user", user);
				dispatcher = req.getRequestDispatcher("/jsp/wrong-object-for-user.jsp");

				req.setAttribute("errorMsg", "Cart doesnot exist!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("do-cart-closed")) {
			Integer cartIdSelected = Integer.valueOf(req.getParameter("cartId"));
			Cart cart = CartService.getById(cartIdSelected);
			Cart cartStatus1 = CartService.updateStatus(cart, Status.CLOSED);
			if (cartStatus1 != null) {
				req.setAttribute("user", user);
				req.setAttribute("cart", cart);
				
				List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart);
				req.setAttribute("orderDTOCollection", orderDTOS);

				dispatcher = req.getRequestDispatcher("/jsp/cart-current.jsp");
			} else {
				req.setAttribute("user", user);
				dispatcher = req.getRequestDispatcher("/jsp/wrong-object-for-user.jsp");

				req.setAttribute("errorMsg", "Cart doesnot exist!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("open-cart-from-list")) {
			Integer cartIdSelected = Integer.valueOf(req.getParameter("cartId"));
			Cart cart = CartService.getById(cartIdSelected);
			if (cart != null) {
				req.setAttribute("user", user);
				req.setAttribute("cart", cart);
				
				List<OrderDTO> orderDTOS = OrderService.getAllDTOByCard(cart);
				req.setAttribute("orderDTOCollection", orderDTOS);

				dispatcher = req.getRequestDispatcher("/jsp/cart-current.jsp");
			} else {
				req.setAttribute("user", user);
				dispatcher = req.getRequestDispatcher("/jsp/wrong-object-for-user.jsp");

				req.setAttribute("errorMsg", "Cart doesnot exist!");
			}
			dispatcher.forward(req, resp);
		} else if (action.equals("all-carts")) { /// TODO
				req.setAttribute("user", user);
				List<Cart> carts = CartService.getAllByUserAndPeriod(user);
				req.setAttribute("cartCollection", carts);

				dispatcher = req.getRequestDispatcher("/jsp/carts.jsp");
				dispatcher.forward(req, resp);
		}

	}
}