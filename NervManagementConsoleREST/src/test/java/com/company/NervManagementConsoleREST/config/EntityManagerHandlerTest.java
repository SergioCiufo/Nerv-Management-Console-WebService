package com.company.NervManagementConsoleREST.config;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;

public class EntityManagerHandlerTest {

	//oggetto mock
	private EntityManager entityManager = mock(EntityManager.class);
	private EntityTransaction entityTransaction = mock(EntityTransaction.class);

	private EntityManagerHandler entityManagerHandler = new EntityManagerHandler(entityManager);
	
	@Test
	public void shouldBeginTransaction_whenTransactionIsNotActive() {
		//parameters
		doReturn(entityTransaction).when(entityManager).getTransaction();
		doReturn(false).when(entityTransaction).isActive();

		//test
		entityManagerHandler.beginTransaction();

		//results
		verify(entityTransaction, times(1)).begin();
	}

	@Test
	public void shouldNotBeginTransaction_whenTransactionIsActive() {
		//parameters
		doReturn(entityTransaction).when(entityManager).getTransaction();
		doReturn(true).when(entityTransaction).isActive();

		//test
		entityManagerHandler.beginTransaction();

		//results
		verify(entityTransaction, times(0)).begin();
	}

	@Test
	public void shouldCommitTransaction_whenTransactionIsActive() {
		//parameters
		doReturn(entityTransaction).when(entityManager).getTransaction();
		doReturn(true).when(entityTransaction).isActive();

		//test
		entityManagerHandler.commitTransaction();

		//results
		verify(entityTransaction, times(1)).commit();
	}

	@Test
	public void shouldNotCommitTransaction_whenTransactionIsNotActive() {
		//parameters
		doReturn(entityTransaction).when(entityManager).getTransaction();
		doReturn(false).when(entityTransaction).isActive();

		//test
		entityManagerHandler.commitTransaction();

		//results
		verify(entityTransaction, times(0)).commit();
	}

	@Test
	public void shouldPersistEntity_whenEntityIsNotNull() {
		//parameters
		Object entity = new Object();

		//test
		entityManagerHandler.persist(entity);

		//results
		verify(entityManager, times(1)).persist(entity);
	}

	@Test
	public void shouldRemoveEntity_whenEntityIsNotNull() {
		//parameters
		Object entity = new Object();

		//test
		entityManagerHandler.remove(entity);

		//results
		verify(entityManager, times(1)).remove(entity);
	}

	@Test
	public void shouldClear_whenEntityManagerIsOpen() {
		//parameters
		doReturn(true).when(entityManager).isOpen();

		//test
		entityManagerHandler.clear();

		//results
		verify(entityManager, times(1)).clear();
	}

	@Test
	public void shouldNotClear_whenEntityManagerIsNotOpen() {
		//parameters
		doReturn(false).when(entityManager).isOpen();

		//test
		entityManagerHandler.clear();

		//results
		verify(entityManager, times(0)).clear();
	}

	@Test
	public void shouldClose_whenEntityManagerIsOpen() {
		//parameters
		doReturn(true).when(entityManager).isOpen();

		//test
		entityManagerHandler.close();

		//results
		verify(entityManager, times(1)).close();
	}

	@Test
	public void shouldNotClose_whenEntityManagerIsNotOpen() {
		//parameters
		doReturn(false).when(entityManager).isOpen();

		//test
		entityManagerHandler.close();

		//results
		verify(entityManager, times(0)).close();
	}

	@Test
	public void shouldReturnEntityManager_whenGetEntityManagerIsCalled() {
		//test
		EntityManager result = entityManagerHandler.getEntityManager();

		//results
		assertEquals(entityManager, result); //se restituisce lo stesso del mock
	}
}