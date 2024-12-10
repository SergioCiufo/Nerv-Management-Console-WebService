package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.UserDao;
import com.company.NervManagementConsoleREST.model.User;

public class UserServiceDao {
    private UserDao userDao;

    public UserServiceDao() {
        this.userDao = new UserDao();
    }

    public User saveUser(User newUser) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		entityManagerHandler.beginTransaction();
	        userDao.create(newUser, entityManagerHandler);	
	        newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername(), entityManagerHandler).getIdUser());
	        entityManagerHandler.commitTransaction();
	        return newUser;
	    }
    }
    
    public List<User> getUsersList() throws SQLException{
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
        	return userDao.retrieve(entityManagerHandler);
    	}
    }
    
    public User getUserByUsernameAndPassword(String username, String password)throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		return userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);
    	}
    }
    
    public User getUserById(int userId) throws SQLException { //va fatto così? entity qui
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		return userDao.getUserById(userId, entityManagerHandler);
    	}
    }
    
    public User getUserByUsername(String username) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		return userDao.getUserByUsername(username, entityManagerHandler);
    	}
    }
    
    public List<User> getUserList() throws SQLException{
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		return userDao.retrieve(entityManagerHandler);
    	}
    }
    
    public void updateUser(User u) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		entityManagerHandler.beginTransaction();
    		userDao.updateUser(u, entityManagerHandler);
    		entityManagerHandler.commitTransaction();
    	}
    }
    
    public void removeUser(int userId) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		entityManagerHandler.beginTransaction();
    		userDao.removeUser(userId, entityManagerHandler);
    		entityManagerHandler.commitTransaction();
    	}
    }
    
    //mettere per il register
}