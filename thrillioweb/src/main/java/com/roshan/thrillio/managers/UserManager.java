package com.roshan.thrillio.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.roshan.thrillio.constants.Gender;
import com.roshan.thrillio.constants.UserType;
import com.roshan.thrillio.dao.UserDao;
import com.roshan.thrillio.entities.User;
import com.roshan.thrillio.util.StringUtil;

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao=new UserDao();
	private UserManager() {}

	public static UserManager getInstance() {
		return instance;
	}	
	public User createUser(long id,String email,String password,String firstName,String lastName,Gender gender,UserType userType) {
		User user=new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		
		return user;
	}
	public List<User> getUsers() {
		return dao.getUsers();
	}

	public User getUser(long userId) {
		// TODO Auto-generated method stub
		return dao.getUser(userId);
	}

	public long authenticate(String email, String password) {
		return dao.authenticate(email,StringUtil.encodePassword(password));
		
	}
}
