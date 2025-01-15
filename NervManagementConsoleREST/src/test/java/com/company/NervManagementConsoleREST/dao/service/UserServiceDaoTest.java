package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.UserDao;
import com.company.NervManagementConsoleREST.model.User;

public class UserServiceDaoTest {
	private UserServiceDao userServiceDao;

	//oggetto mock
	private UserDao userDao = mock(UserDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userServiceDao = new UserServiceDao();
		}

		Field userDaoField = userServiceDao.getClass().getDeclaredField("userDao");
		userDaoField.setAccessible(true);
		userDaoField.set(userServiceDao, userDao);

		Field jpaUtilField = userServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(userServiceDao, jpaUtil);
	}

	@Test
	public void shouldSaveUser_whenAllOk() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		User user = new User();
		user.setUsername("username");

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(user).when(userDao).getUserByUsername(user.getUsername(), entityManagerHandler);
		doNothing().when(userDao).create(user, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		User result = userServiceDao.saveUser(user);

		//result
		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());

		verify(userDao, times(1)).create(user, entityManagerHandler);
		verify(userDao, times(1)).getUserByUsername(user.getUsername(), entityManagerHandler);
	}

	@Test
	public void shouldReturnUserList_whenAll() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<User> userList = new ArrayList<User>();
		User user = new User();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(userList).when(userDao).retrieve(entityManagerHandler);

		//test
		List<User> result = userServiceDao.getUserList();

		//result
		assertNotNull(result);
		assertEquals(userList, result);
		verify(userDao, times(1)).retrieve(entityManagerHandler);
	}

	@Test
	public void shouldReturnUserByUsernamePassword_whenAll() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";
		String password = "password";

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(user).when(userDao).getUserByUsernameAndPassword(username, password, entityManagerHandler);

		//test
		User result = userServiceDao.getUserByUsernameAndPassword(username, password);

		//result
		assertNotNull(result);
		assertEquals(user, result);
		verify(userDao, times(1)).getUserByUsernameAndPassword(username, password, entityManagerHandler);
	}

	@Test
	public void shouldReturnUserByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		int userId=1;

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(user).when(userDao).getUserById(userId, entityManagerHandler);

		//test
		User result = userServiceDao.getUserById(userId);

		//result
		assertNotNull(result);
		assertEquals(user, result);
		verify(userDao, times(1)).getUserById(userId, entityManagerHandler);
	}

	@Test
	public void shouldReturnUserByUsername_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username ="username";

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(user).when(userDao).getUserByUsername(username, entityManagerHandler);

		//test
		User result = userServiceDao.getUserByUsername(username);

		//result
		assertNotNull(result);
		assertEquals(user, result);
		verify(userDao, times(1)).getUserByUsername(username, entityManagerHandler);
	}
	
	@Test
	public void shouldUpdateUser_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(userDao).updateUser(user, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		userServiceDao.updateUser(user);

		//result
		verify(userDao, times(1)).updateUser(user, entityManagerHandler);
	    verify(entityManagerHandler, times(1)).beginTransaction();
	    verify(entityManagerHandler, times(1)).commitTransaction();
	}
	
	@Test
	public void shouldRemoveUser_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		int userId= 1;

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(userDao).removeUser(userId, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		userServiceDao.removeUser(userId);

		//result
		verify(userDao, times(1)).removeUser(userId, entityManagerHandler);
	    verify(entityManagerHandler, times(1)).beginTransaction();
	    verify(entityManagerHandler, times(1)).commitTransaction();
	}
}
