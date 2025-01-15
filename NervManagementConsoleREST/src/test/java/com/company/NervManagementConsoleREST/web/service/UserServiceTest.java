package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.UserService;

public class UserServiceTest {
	private UserService userService;
	
	//oggetto mock
	private UserServiceDao userServiceDao = mock(UserServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userService = new UserService();
	    }
		
		Field f = userService.getClass().getDeclaredField("userServiceDao");
		f.setAccessible(true);
		f.set(userService, userServiceDao);
	}
	
	@Test
	public void shouldCreateUser_whenAllOk() throws Exception{
		//parameters
		String name = "name";
		String surname = "surname";
		String username = "username";
		String password = "password";
		List<Member> memberList = new ArrayList<Member>();
		User user = new User(name, surname, username, password, memberList);
		
		//mock
		doReturn(user).when(userServiceDao).saveUser(user);
		
		//test
		User result = userService.createUser(name, surname, username, password, memberList);
		
		//result
		verify(userServiceDao).saveUser(user);
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	public void shouldReturnListUser_whenAllOk() throws Exception{
		//parameters
		List<User> userList = new ArrayList<User>();
		
		//mock
		doReturn(userList).when(userServiceDao).getUserList();
		
		//test
		List<User> result =userService.getUsersList();
		
		//result
		verify(userServiceDao).getUserList();
		assertNotNull(result);
		assertEquals(userList, result);
	}
	
	@Test
	public void shouldReturnUserByUsernamePassword_whenAllOk() throws Exception{
		//parameters
		String username ="username";
		String password ="password";
		User user = new User();
		
		//mocks
		doReturn(user).when(userServiceDao).getUserByUsernameAndPassword(anyString(), anyString());
		
		//test
		User result = userService.getUserByUsernameAndPassword(username, password);
		
		//result
		verify(userServiceDao).getUserByUsernameAndPassword(anyString(), anyString());
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	public void shouldReturnUserByUserId_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		int userId = 123;
		
		//mocks
		doReturn(user).when(userServiceDao).getUserById(anyInt());
		
		//test
		User result = userService.getUserById(userId);
		
		//result
		verify(userServiceDao).getUserById(anyInt());
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	public void shouldReturnUserByUsername_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		String username = "username";
		
		//mocks
		doReturn(user).when(userServiceDao).getUserByUsername(anyString());
		
		//test
		User result = userService.getUserByUsername(username);
		
		//result
		verify(userServiceDao).getUserByUsername(anyString());
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	//test di getUsersByNameAndOrSurname
	@Test
	public void shouldReturnUsersFilteredByName_whenNameIsProvided() throws Exception {
	    //parameters
	    List<User> userList = new ArrayList<>();

	    String name = "name";
	    String surname = null; // Solo il filtro `name`

	    //mock
	    doReturn(userList).when(userServiceDao).getUserList();

	    //test
	    List<User> result = userService.getUsersbyNameAndOrSurname(name, surname);

	    //results
	    verify(userServiceDao).getUserList();
	    assertNotNull(result);
	    }

	@Test
	public void shouldReturnUsersFilteredBySurname_whenSurnameIsProvided() throws Exception {
	    //parameters
	    List<User> userList = new ArrayList<>();
	    User user = new User("name", "surname", "username1", "password1");   
	    userList.add(user);

	    String name = null; // Solo il filtro `surname`
	    String surname = "surname";

	    //mocks
	    doReturn(userList).when(userServiceDao).getUserList();

	    //test
	    List<User> result = userService.getUsersbyNameAndOrSurname(name, surname);

	    //results
	    verify(userServiceDao).getUserList();
	    assertNotNull(result);
	    }

	@Test
	public void shouldReturnUsersFilteredByNameAndSurname_whenBothAreProvided() throws Exception {
	    //parameters
	    List<User> userList = new ArrayList<>();
	    User user = new User("name", "surname", "username", "password");
	    userList.add(user);

	    String name = "name";
	    String surname = "surname";

	    //mocks
	    doReturn(userList).when(userServiceDao).getUserList();

	    //test
	    List<User> result = userService.getUsersbyNameAndOrSurname(name, surname);

	    //results
	    verify(userServiceDao).getUserList();
	    assertNotNull(result);
	    }

	@Test
	public void shouldReturnAllUsers_whenNoFiltersAreProvided() throws Exception {
	    //parameters
	    List<User> userList = new ArrayList<>();
	    User user = new User("name", "surname", "username", "password");
	    userList.add(user);

	    String name = null;
	    String surname = null; // Nessun filtro

	    //mocks
	    doReturn(userList).when(userServiceDao).getUserList();

	    //test
	    List<User> result = userService.getUsersbyNameAndOrSurname(name, surname);

	    // Results
	    verify(userServiceDao).getUserList();
	    assertNotNull(result);
	    }
	//fine test getUsersByNameAndOrSurname;
	
	@Test
	public void shouldUpdateUser_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		
		//mocks
		doNothing().when(userServiceDao).updateUser(user); //prova con any
		
		//test
		userService.updateUser(user);
		
		//result
		verify(userServiceDao).updateUser(user);
	}
	
	@Test
	public void shouldRemoveUser_whenAllOk() throws Exception{
		//parameters
		int userId=123;
		
		//mocks
		doNothing().when(userServiceDao).removeUser(anyInt());
		
		//test
		userService.removeUser(userId);
		
		//result
		verify(userServiceDao).removeUser(anyInt());
	}
}
