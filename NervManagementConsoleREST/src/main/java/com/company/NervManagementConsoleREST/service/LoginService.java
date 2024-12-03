package com.company.NervManagementConsoleREST.service;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.UserDao;
import com.company.NervManagementConsoleREST.exception.InvalidCredentialsException;
import com.company.NervManagementConsoleREST.model.User;

public class LoginService {
	private UserDao userDao;
	private final RetriveInformationService ris;
	
	public LoginService() {
		super();
		this.userDao= new UserDao();
		this.ris = new RetriveInformationService();
	}
	
	public User loginCheck(String username, String password) throws Exception {
		User user = null;
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			user = userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);
			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			user=ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		} 
	}
}
