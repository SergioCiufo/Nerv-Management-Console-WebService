package com.company.NervManagementConsoleREST.service;

import com.company.NervManagementConsoleREST.dao.service.RegisterServiceDao;
import com.company.NervManagementConsoleREST.model.User;

import java.sql.SQLException;


public class RegisterService {
	private RegisterServiceDao registerServiceDao;

	public RegisterService() {
		this.registerServiceDao = new RegisterServiceDao();

	}
	
	public void register(User newUser) throws SQLException {
		registerServiceDao.registerUser(newUser);
	}

}
