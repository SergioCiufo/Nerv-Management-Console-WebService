package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.RegisterServiceDao;
import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;

public class RegisterServiceTest {
	private RegisterService registerService;
	
	//oggetto mock
	private RegisterServiceDao registerServiceDao = mock(RegisterServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			registerService = new RegisterService();
	    }
		
		Field f = registerService.getClass().getDeclaredField("registerServiceDao");
		f.setAccessible(true);
		f.set(registerService, registerServiceDao);
	}
	
	 @Test
	    public void shouldRegisterUser_whenAllOk() throws Exception {
	        //parameters
		 	User user = new User();

	        //mocks
	        doNothing().when(registerServiceDao).registerUser(user);

	        //test
	        registerService.register(user);

	        //verify
	        verify(registerServiceDao).registerUser(user);
	    }
}
