package com.company.NervManagementConsoleREST.service;

import com.company.NervManagementConsoleREST.exception.InvalidCredentialsException;
import com.company.NervManagementConsoleREST.model.User;

public class LoginService {
	private UserService userService;
	private final RetriveInformationService ris;
	
	public LoginService() {
		super();
		this.userService= new UserService();
		this.ris = new RetriveInformationService();
	}
	
	public User loginCheck(String username, String password) throws Exception {
		User user = null;
			user = userService.getUserByUsernameAndPassword(username, password);
			if (user == null) {
				throw new InvalidCredentialsException("Invalid Credentials", null);
			}
			user=ris.retriveUserInformation(user);
			return user;
	} 

}
