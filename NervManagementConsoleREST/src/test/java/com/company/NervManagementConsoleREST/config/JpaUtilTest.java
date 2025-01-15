package com.company.NervManagementConsoleREST.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class JpaUtilTest {
	private JpaUtil jpaUtil;

	//oggetto mock
	private EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
	private EntityManager entityManager = mock(EntityManager.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, IllegalAccessException {
		//mock statico persistence
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			jpaUtil = new JpaUtil();
		}

		Field entityManagerFactoryField = jpaUtil.getClass().getDeclaredField("entityManagerFactory");
		entityManagerFactoryField.setAccessible(true);
		entityManagerFactoryField.set(jpaUtil, entityManagerFactory);

	}

	@Test
	public void shouldThrowExceptionInConstructor_whenPersistenceFactoryFails() {
		//mock statico
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {

			mockedPersistence.when(() -> Persistence.createEntityManagerFactory(anyString()))
			.thenThrow(new RuntimeException("test"));			
		}
		//test
		ExceptionInInitializerError exception = assertThrows(ExceptionInInitializerError.class, () -> {
			new JpaUtil();
		});

		//result
		assertNotNull(exception);
	}
	@Test
	public void shouldCreateEntityManagerFactory_whenConstructorIsCalled() throws Exception{
		//result
		assertNotNull(jpaUtil.getEntityManagerFactory());
	}

	@Test
	public void shouldReturnEntityManager_whenGetEntityManagerIsCalled()  {
		//mock
		doReturn(entityManager).when(entityManagerFactory).createEntityManager();

		//test
		assertNotNull(jpaUtil.getEntityManager());
		
		//result
		verify(entityManagerFactory, times(1)).createEntityManager();
	}

	@Test
	public void shouldCloseEntityManagerFactory_whenCloseEntityManagerFactoryIsCalled() throws Exception{
		//mock
		doNothing().when(entityManagerFactory).close();
		doReturn(true).when(entityManagerFactory).isOpen();

		//test
		jpaUtil.closeEntityManagerFactory();

		//result
		verify(entityManagerFactory, times(1)).close();
	}

	@Test
	public void shouldNotCloseEntityManagerFactory_whenEntityManagerFactoryIsClosed() throws Exception{
		//mock
		doReturn(false).when(entityManagerFactory).isOpen();

		//test
		jpaUtil.closeEntityManagerFactory();

		//result
		verify(entityManagerFactory, times(0)).close();
	}

	@Test
	public void shouldCloseEntityManagerFactory_whenEntityManagerFactoryIsOpen() {
		//mock
		doReturn(true).when(entityManagerFactory).isOpen();

		//test
		jpaUtil.closeEntityManagerFactory();

		//result
		assertNotNull(entityManagerFactory);
		verify(entityManagerFactory).isOpen();
		verify(entityManagerFactory, times(1)).close();
	}
}