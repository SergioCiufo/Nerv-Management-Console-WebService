package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.exception.InvalidCredentialsException;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.LoginService;
import com.company.NervManagementConsoleREST.service.RetriveInformationService;
import com.company.NervManagementConsoleREST.service.UserService;

public class LoginServiceTest {
	private LoginService loginService;

	//oggetto mock
	private UserService userService = mock(UserService.class);
	private RetriveInformationService retriveInformationService = mock(RetriveInformationService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			loginService = new LoginService();
	    }
		
		Field userServiceField = loginService.getClass().getDeclaredField("userService");
		userServiceField.setAccessible(true);
		userServiceField.set(loginService, userService);

		Field retriveInformationServiceField = loginService.getClass().getDeclaredField("ris");
		retriveInformationServiceField.setAccessible(true);
		retriveInformationServiceField.set(loginService, retriveInformationService);
	}

	@Test
	public void shouldReturnUser_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		String username = "username";
		String password =" password";
		user.setUsername(username);
		user.setPassword(password);

		//mocks
		doReturn(user).when(userService).getUserByUsernameAndPassword(username, password);
		doReturn(user).when(retriveInformationService).retriveUserInformation(user);

		//test
		User userFound = loginService.loginCheck(username, password); //se esce null non avrà trovato nessun utente

		//results
		assertNotNull(userFound); //verifichiamo se l'oggetto user non sia null, se l'oggetto è null, il test fallirà
		verify(userService).getUserByUsernameAndPassword(username, password);
		verify(retriveInformationService).retriveUserInformation(user);
	}

	@Test
	public void shouldThrowInvalidCredentialsException_whenUserNotFound() throws Exception{
		//parameters
		User user = new User();
		String username = "username";
		String password =" password";
		user.setUsername(username);
		user.setPassword(password);
		InvalidCredentialsException invalidCredentialsException = new InvalidCredentialsException("test", null);
		
		//mocks
		doReturn(null).when(userService).getUserByUsernameAndPassword(username, password);
		
		//test
		// verifica che il metodo loginCheck lanci un'eccezione InvalidCredentialsException
		// quando le credenziali fornite non corrispondono a un utente valido.
					//tipo di eccezione da lanciare		//lambda che rappresenta l'operazione da eseguire
		assertThrows(InvalidCredentialsException.class, () -> loginService.loginCheck(username, password));
		
		
		//results
		verify(userService).getUserByUsernameAndPassword(username, password);
		// Verifica che retriveInformationService non venga chiamato
		verify(retriveInformationService, never()).retriveUserInformation(any(User.class)); 
	}

}
