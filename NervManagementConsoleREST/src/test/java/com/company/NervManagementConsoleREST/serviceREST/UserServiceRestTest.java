package com.company.NervManagementConsoleREST.serviceREST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class UserServiceRestTest {
	private UserServiceRest userServiceRest;

	//oggetto mock
	private UserService userService = mock(UserService.class);
	private RegisterService registerService = mock(RegisterService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userServiceRest = new UserServiceRest();
	    }
		
		Field userServiceField = userServiceRest.getClass().getDeclaredField("userService");
		userServiceField.setAccessible(true);
		userServiceField.set(userServiceRest, userService);

		Field registerServiceField = userServiceRest.getClass().getDeclaredField("registerService");
		registerServiceField.setAccessible(true);
		registerServiceField.set(userServiceRest, registerService);

	}


	@Test
	public void shouldReturnUser_whenGetUserByIdIsCalled() throws SQLException {
		//parameters
		User user = new User();
		user.setIdUser(1);
		user.setUsername("Misato");

		//mock
		doReturn(user).when(userService).getUserById(1);

		//test
		User result = userServiceRest.getUser(1);

		//result
		assertNotNull(result);
		assertEquals("Misato", result.getUsername());
	}

	@Test
	public void shouldReturnUser_whenGetUserByUsernameIsCalled() {
		//parameters
		User user = new User();
		user.setIdUser(1);
		user.setUsername("Misato");

		//mock
		doReturn(user).when(userService).getUserByUsername("Misato");

		//test
		User result = userServiceRest.getUserByUsername("Misato");

		//result
		assertNotNull(result);
		assertEquals("Misato", result.getUsername());
	}

	@Test
	public void shouldReturnNoContent_whenAddUserIsCalled() throws SQLException {
		//parameters
		User user = new User();
		user.setUsername("Misato");

		//mock
		doNothing().when(registerService).register(user);

		//test
		Response response = userServiceRest.addUser(user);

		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnNoContent_whenUpdateUserIsCalled() {
		//parameters
		User user = new User();
		user.setIdUser(1);
		user.setUsername("Misato");

		//mock
		doNothing().when(userService).updateUser(user);

		//test
		Response response = userServiceRest.updateUser(user);

		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnNoContent_whenDeleteUserIsCalled() {
		//parameters
		int userId = 1;

		//mock
		doNothing().when(userService).removeUser(userId);

		//test
		Response response = userServiceRest.deleteUser(userId);

		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnUsers_whenGetUsersWithQueryParamsIsCalled() {
		//parameters
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setUsername("Misato");
		userList.add(user);

		//mock
		doReturn(userList).when(userService).getUsersbyNameAndOrSurname("Misato", null);

		//test
		Response response = userServiceRest.getUsers("Misato", null);

		//result
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertNotNull(response.getEntity());
	}


	@Test
	public void shouldReturnInternalServerError_whenGetUsersThrowsException() {
		//parameters
		doThrow(RuntimeException.class).when(userService).getUsersbyNameAndOrSurname("Misato", null);

		//test
		Response response = userServiceRest.getUsers("Misato", null);

		//result
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnInternalServerError_whenAddUserThrowsException() throws Exception {
		//parameters
		User user = new User();
		user.setUsername("Misato");

		//mock
		doThrow(RuntimeException.class).when(registerService).register(user);

		//test
		Response response = userServiceRest.addUser(user);

		//result
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnInternalServerError_whenUpdateUserThrowsException() {
		//parameters
		User mockUser = new User();
		mockUser.setIdUser(1);
		mockUser.setUsername("Misato");

		//mock
		doThrow(RuntimeException.class).when(userService).updateUser(mockUser);

		//test
		Response response = userServiceRest.updateUser(mockUser);

		//result
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReturnInternalServerError_whenDeleteUserThrowsException() {
		//parameters
		int userId = 1;

		//mock
		doThrow(RuntimeException.class).when(userService).removeUser(userId);

		//test
		Response response = userServiceRest.deleteUser(userId);

		//result
		assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
	}
}
