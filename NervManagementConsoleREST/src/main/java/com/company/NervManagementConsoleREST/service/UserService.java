package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;

public class UserService {
    private UserServiceDao userServiceDao;

    public UserService() {
        this.userServiceDao = new UserServiceDao();
    }
    
    public User createUser(String name, String surname, String username, String password,
    					List<Member> defaultMembers) {
    	User newUser = new User(name, surname, username, password, defaultMembers);
        return userServiceDao.saveUser(newUser);
    }
    
    public List<User> getUsersList(){
    	return userServiceDao.getUserList();
    }

    public User getUserByUsernameAndPassword(String username, String password) {
    	User user =userServiceDao.getUserByUsernameAndPassword(username, password);
    	return user;
    }
    
    public User getUserById(int userId) {
    	User user = userServiceDao.getUserById(userId);
    	return user;
    }
    
    public User getUserByUsername(String username) {
    	User user = userServiceDao.getUserByUsername(username);
    	return user;
    }
    
    public List<User> getUsersbyNameAndOrSurname(String name, String surname) {
    		List<User> listUsers = userServiceDao.getUserList();
    		if(name != null) {
    			listUsers = listUsers.stream()
    					.filter(user -> user.getName().equalsIgnoreCase(name))
    					 .collect(Collectors.toList());
    		}
    		if (surname != null) {
                listUsers = listUsers.stream()
                    .filter(user -> user.getSurname().equalsIgnoreCase(surname))
                    .collect(Collectors.toList());
            }
    		return listUsers;
    }
    
    public void updateUser(User u){
    	userServiceDao.updateUser(u);
    }
    
    public void removeUser(int userId){
    	userServiceDao.removeUser(userId);
    }
    
}