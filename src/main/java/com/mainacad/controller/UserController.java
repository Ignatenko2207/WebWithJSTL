package com.mainacad.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mainacad.dao.UserDAO;
import com.mainacad.model.User;
import com.mainacad.service.UserService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/user")
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        if(action.equals("login")){
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = UserService.getByLoginAndPassword(login, password);
            RequestDispatcher dispatcher;
            if (user!=null){
                dispatcher = req.getRequestDispatcher("/jsp/items.jsp");
                req.setAttribute("user", user);
            }
            else{
                dispatcher = req.getRequestDispatcher("/jsp/wrong-auth.jsp");
                req.setAttribute("errorMsg", "Login or password are wrong!");
            }
            dispatcher.forward(req, resp);
        } else if(action.equals("register")){
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
			} 
            else {
            	dispatcher = getServletContext().getRequestDispatcher("/jsp/items.jsp");
            	req.setAttribute("errorMsg", "Login or password are wrong!");
            }
            dispatcher.forward(req, resp);
        }
    }

}
