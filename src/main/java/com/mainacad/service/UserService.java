package com.mainacad.service;

import com.mainacad.dao.UserDAO;
import com.mainacad.model.User;

public class UserService {

	public static User getByLoginAndPassword(String login, String password) {
		return UserDAO.getByLoginAndPassword(login, password);
	}
	
	public static User getById(Integer id) {
		return UserDAO.getById(id);
	}
	
	
}
