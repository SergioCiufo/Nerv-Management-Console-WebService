package com.company.NervManagementConsoleREST.web.servlet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;

public class RegisterServletTest {
	private RegisterServlet registerServlet;
	
	//oggetto mock
	private RegisterService registerService = mock(RegisterService.class);
	
	@BeforeEach
	public void setup() throws IllegalAccessError, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		//così possiamo usare il campo privato registerService
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			registerServlet = new RegisterServlet();
	    }
		
		Field f = registerServlet.getClass().getDeclaredField("registerService");
		f.setAccessible(true);
		f.set(registerServlet, registerService);
	}
	
	@Test
	public void shouldDispatcherToHome_WhenAllOk() throws Exception {
		
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		String name = "name";
		String surname = "surname";
		String username = "username";
		String password = "password";
		
		//mocks
		doReturn(name).when(req).getParameter("registerName");
		doReturn(surname).when(req).getParameter("registerSurname");
		doReturn(username).when(req).getParameter("registerUsername");
		doReturn(password).when(req).getParameter("registerPassword");
		doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Login.jsp");
		
		//test
		registerServlet.doPost(req, resp);
		
		//results
		verify(registerService).register(new User(name, surname, username, password));
		verify(dispatcher).forward(req, resp);
	}
	
	@Test
	public void shouldDispatcherToError_whenGenericExceptionReceived() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		String name = "name";
		String surname = "surname";
		String username = "username";
		String password = "password";
		Exception genericException = new Exception("test", null);
		
		//mocks
		doReturn(name).when(req).getParameter("registerName");
		doReturn(surname).when(req).getParameter("registerSurname");
		doReturn(username).when(req).getParameter("registerUsername");
		doReturn(password).when(req).getParameter("registerPassword");
		doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Error.jsp");
		
		//test
		registerServlet.doPost(req, resp);
				
		//results
		verify(registerService).register(new User(name, surname, username, password));
		verify(dispatcher).forward(req, resp);
	}
}
