package com.company.NervManagementConsoleREST.web.servlet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.annotations.common.reflection.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Fields;


import com.company.NervManagementConsoleREST.exception.InvalidCredentialsException;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.LoginService;
import com.company.NervManagementConsoleREST.utils.Costants;

public class LoginServletTest {
	private LoginServlet loginServlet = new LoginServlet();
	
	 //oggetto mock
	private LoginService loginService = mock(LoginService.class);
	
	@BeforeEach
	public void setup() throws IllegalAccessException, NoSuchFieldException, SecurityException {
		//abbiamo aggirato e possiamo scrivere in un campo privato //private final LoginService loginService = new LoginService();
		//FieldUtils.writeField(loginServlet, "loginService", loginService);
	
		Field f = loginServlet.getClass().getDeclaredField("loginService");
        f.setAccessible(true);
        f.set(loginServlet, loginService);
	}
	
	@Test
	public void shouldSendRedirectToHome_whenAllOk() throws Exception {
		//parameters
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		String path = "localHost:8080";
		String username= "username";
		String password= "password";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		//mocks
		doReturn(username).when(request).getParameter(Costants.FORM_LOGIN_USERNAME);
		doReturn(password).when(request).getParameter(Costants.FORM_LOGIN_PASSWORD);
		doReturn(user).when(loginService).loginCheck(username, password);
		doReturn(session).when(request).getSession();
		doReturn(path).when(request).getContextPath();
		
		//test
		loginServlet.doPost(request, resp);
		
		//results		//se più di una volta, sennò togliere times //never se non viene mai chiamata
		verify(session, times(1)).setAttribute(Costants.KEY_SESSION_USER, user); //verifica che la richiesta è stata svolta una sola volta
		verify(resp).sendRedirect(path + "/jsp/private/Home.jsp");
	}
	
	@Test
	public void shouldSendRedirectToLogin_whenInvalidCredentialsExceptionReceived() throws Exception {
		//parameters
				HttpServletRequest request = mock(HttpServletRequest.class);
				HttpServletResponse resp = mock(HttpServletResponse.class);
				HttpSession session = mock(HttpSession.class);
				RequestDispatcher dispatcher = mock(RequestDispatcher.class);
				String path = "localHost:8080";
				String username= "username";
				String password= "password";
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				InvalidCredentialsException invalidCredentialEcException = new InvalidCredentialsException("test", null);
				
				//mocks
				doReturn(username).when(request).getParameter(Costants.FORM_LOGIN_USERNAME);
				doReturn(password).when(request).getParameter(Costants.FORM_LOGIN_PASSWORD);
				doThrow(invalidCredentialEcException).when(loginService).loginCheck(username, password);
				doReturn(dispatcher).when(request).getRequestDispatcher("/jsp/public/Login.jsp");
				
				
				//test
				//assertThrows(InvalidCredentialsException.class, () -> loginServlet.doPost(request, resp));
				loginServlet.doPost(request, resp);
				
				//results		//se più di una volta, sennò togliere times //never se non viene mai chiamata
				verify(request).setAttribute(Costants.ERROR_LOGIN, invalidCredentialEcException.getMessage());
				verify(dispatcher).forward(request, resp);
	}
	
	@Test
	public void shouldSendRedirectToError_whenGenericExceptionReceived() throws Exception {
		
		//parameters
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		String path = "localHost:8080";
		String username= "username";
		String password= "password";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Exception genericException = new Exception("test", null);
		
		//mocks
		doReturn(username).when(request).getParameter(Costants.FORM_LOGIN_USERNAME);
	    doReturn(password).when(request).getParameter(Costants.FORM_LOGIN_PASSWORD);
	    doThrow(genericException).when(loginService).loginCheck(username, password);
	    doReturn(dispatcher).when(request).getRequestDispatcher("/jsp/public/Error.jsp");
		
		//test
		//assertThrows(InvalidCredentialsException.class, () -> loginServlet.doPost(request, resp));
		loginServlet.doPost(request, resp);
		
		//results		//se più di una volta, sennò togliere times //never se non viene mai chiamata
		verify(dispatcher).forward(request, resp);
		
	}
	
}
