package com.company.NervManagementConsoleREST.serviceSOAP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;
import com.company.NervManagementConsoleREST.service.UserService;

public class UserServiceSoapTest {
	private UserServiceSoap userServiceSoap;

	//oggetto mock
	private UserService userService = mock(UserService.class);
	private RegisterService registerService = mock(RegisterService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userServiceSoap = new UserServiceSoap();
	    }
		
		Field userServiceField = userServiceSoap.getClass().getDeclaredField("userService");
		userServiceField.setAccessible(true);
		userServiceField.set(userServiceSoap, userService);

		Field registerServiceField = userServiceSoap.getClass().getDeclaredField("registerService");
		registerServiceField.setAccessible(true);
		registerServiceField.set(userServiceSoap, registerService);

	}

	@Test
	public void shouldReturnUsersList_whenUsersListIsCalled() throws SQLException {
		//parameters
		List<User> expectedUsers = Arrays.asList(new User(), new User());
		
		//mock
		doReturn(expectedUsers).when(userService).getUsersList();

		//test
		List<User> users = userServiceSoap.usersList();

		//result
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals(expectedUsers, users);
	}

	@Test
	public void shouldReturnCreated_whenAddUserIsCalledSuccessfully() throws Exception {
		//parameters
		User user = new User();

		//mock
		doNothing().when(registerService).register(user);

		//test
		Response response = userServiceSoap.addUser(user);

		//result
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnBadRequest_whenAddUserFails() throws Exception {
		//parameters
		User user = new User();

		//mock
		doThrow(new RuntimeException("Error during registration")).when(registerService).register(user);

		//test
		Response response = userServiceSoap.addUser(user);

		//result
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnOk_whenUpdateUserIsCalledSuccessfully() {
		//parameters
		User user = new User();

		//mock
		doNothing().when(userService).updateUser(user);

		//test
		Response response = userServiceSoap.updateUser(user);

		//result
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnBadRequest_whenUpdateUserFails() {
		//parameters
		User user = new User();

		//mock
		doThrow(new RuntimeException("Error during update")).when(userService).updateUser(user);

		//test
		Response response = userServiceSoap.updateUser(user);

		//result
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnNoContent_whenDeleteUserIsCalledSuccessfully() {
		//parameters
		int userId = 1;

		//mock
		doNothing().when(userService).removeUser(userId);

		//test
		Response response = userServiceSoap.deleteUser(userId);

		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnNotFound_whenDeleteUserFails() {
		//parameters
		int userId = 1;

		//mock
		doThrow(new RuntimeException("User not found")).when(userService).removeUser(userId);

		//test
		Response response = userServiceSoap.deleteUser(userId);

		//result
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
