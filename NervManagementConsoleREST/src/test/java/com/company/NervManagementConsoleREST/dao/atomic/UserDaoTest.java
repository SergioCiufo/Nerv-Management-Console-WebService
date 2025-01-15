package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.User;

public class UserDaoTest {
	private UserDao userDao = new UserDao();

	@Test
	public void shouldCreateUser_whenAllOk() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		//mocks
		doNothing().when(entityManagerHandler).persist(user);

		//test
		userDao.create(user, entityManagerHandler);

		//results
		verify(entityManagerHandler, times(1)).persist(user);
	}

	@Test
	public void shouldThrowDatabaseException_whenPersistFails() {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		//mock
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(user);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.create(user, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(user);
	}

	@Test
	public void shouldRetrieveListUser_whenAllOk() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<User> userList = new ArrayList<User>();
		User user = new User();
		userList.add(user);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM User ORDER BY userId ASC", User.class);
		doReturn(userList).when(query).getResultList();

		//test
		List<User> result = userDao.retrieve(entityManagerHandler);

		//results
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM User ORDER BY userId ASC", User.class);
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveFails() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM User ORDER BY userId ASC", User.class);

		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.retrieve(entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}

	@Test
	public void shouldReturnUserById_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		int userId= 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doReturn(user).when(query).getSingleResult();

		//test
		User result = userDao.getUserById(userId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenNoUserIdFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		int userId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class);
		doReturn(query).when(query).setParameter("userId", userId);

		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		User result = userDao.getUserById(userId, entityManagerHandler);

		//result
		assertNull(result);

		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getSingleResult();

	}

	@Test
	public void shouldThrowDatabaseException_whenNoUserIdFound() throws Exception {
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.getUserById(userId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}

	@Test
	public void shouldReturnUserByUsernamePassword_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";
		String password = "password";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class);
		doReturn(query).when(query).setParameter("username", username.toLowerCase());
		doReturn(query).when(query).setParameter("password", password);
		doReturn(user).when(query).getSingleResult();

		//test
		User result = userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);

		//result
		assertNotNull(result);

		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class);
		verify(query, times(1)).setParameter("username", username.toLowerCase());
		verify(query, times(1)).setParameter("password", password);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenNoUsernamePasswordFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";
		String password = "password";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class);
		doReturn(query).when(query).setParameter("username", username.toLowerCase());
		doReturn(query).when(query).setParameter("password", password);

		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		User result = userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);

		//result
		assertNull(result);

		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class);
		verify(query, times(1)).setParameter("username", username.toLowerCase());
		verify(query, times(1)).setParameter("password", password);
		verify(query, times(1)).getSingleResult();

	}

	@Test
	public void shouldThrowDatabaseException_whenNoUsernamePasswordFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";
		String password = "password";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class);
		doReturn(query).when(query).setParameter("username", username.toLowerCase());
		doReturn(query).when(query).setParameter("password", password);

		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.getUserByUsernameAndPassword(username, password, entityManagerHandler);
		});

		//result
		assertNotNull(exception);

	}

	@Test
	public void shouldReturnUserByUsername_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("select x from User x where x.username=:parUser", User.class);
		doReturn(query).when(query).setParameter("parUser", username);
		doReturn(user).when(query).getSingleResult();

		//test
		User result = userDao.getUserByUsername(username, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManager, times(1)).createQuery("select x from User x where x.username=:parUser", User.class);
		verify(query, times(1)).setParameter("parUser", username);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenNoUsernameFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("select x from User x where x.username=:parUser", User.class);
		doReturn(query).when(query).setParameter("parUser", username);
		doReturn(user).when(query).getSingleResult();

		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		User result = userDao.getUserByUsername(username, entityManagerHandler);

		//result
		assertNull(result);

		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("select x from User x where x.username=:parUser", User.class);
		verify(query, times(1)).setParameter("parUser", username);
		verify(query, times(1)).getSingleResult();

	}

	@Test
	public void shouldThrowDatabaseException_whenNoUsernameFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		String username = "username";

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("select x from User x where x.username=:parUser", User.class);
		doReturn(query).when(query).setParameter("parUser", username);
		doReturn(user).when(query).getSingleResult();

		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.getUserByUsername(username, entityManagerHandler);
		});

		//result
		assertNotNull(exception);

	}

	@Test
	public void shouldUpdateUser_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		user.setIdUser(1);
		user.setName("name");
		user.setSurname("surname");
		user.setPassword("password");
		user.setUsername("username");
		user.setImage(null);

		EntityManager entityManager = mock(EntityManager.class);
		Query query = mock(Query.class); //query invece di typedQuery perché l'update non restituisce typedQuery

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE User u " +
				"SET u.name = :name, " +
				"u.surname = :surname, " +
				"u.password = :password, " +
				"u.username = :username, " +
				"u.image = :image " +
				"WHERE u.idUser = :userId");
		doReturn(query).when(query).setParameter("name", user.getName());
		doReturn(query).when(query).setParameter("surname", user.getSurname());
		doReturn(query).when(query).setParameter("password", user.getPassword());
		doReturn(query).when(query).setParameter("username", user.getUsername());
		doReturn(query).when(query).setParameter("image", user.getImage());
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(1).when(query).executeUpdate();

		//test
		userDao.updateUser(user, entityManagerHandler);

		//result
		verify(query, times(1)).setParameter("name", user.getName());
		verify(query, times(1)).setParameter("surname", user.getSurname());
		verify(query, times(1)).setParameter("password", user.getPassword());
		verify(query, times(1)).setParameter("username", user.getUsername());
		verify(query, times(1)).setParameter("image", user.getImage());
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).executeUpdate();
	}

	@Test
	public void shouldThrowDatabaseException_whenTryToUpdateUser() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		user.setIdUser(1);
		user.setName("name");
		user.setSurname("surname");
		user.setPassword("password");
		user.setUsername("username");
		user.setImage(null);

		EntityManager entityManager = mock(EntityManager.class);
		Query query = mock(Query.class); //query invece di typedQuery perché l'update non restituisce typedQuery

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE User u " +
				"SET u.name = :name, " +
				"u.surname = :surname, " +
				"u.password = :password, " +
				"u.username = :username, " +
				"u.image = :image " +
				"WHERE u.idUser = :userId");
		doReturn(query).when(query).setParameter("name", user.getName());
		doReturn(query).when(query).setParameter("surname", user.getSurname());
		doReturn(query).when(query).setParameter("password", user.getPassword());
		doReturn(query).when(query).setParameter("username", user.getUsername());
		doReturn(query).when(query).setParameter("image", user.getImage());
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(1).when(query).executeUpdate();

		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.updateUser(user, entityManagerHandler);
		});

		//result
		assertNotNull(exception);

	}

	@Test
	public void shouldRemoveUser_whenAllOk() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		Query query1 = mock(Query.class); //userMembersStats
		Query query2 = mock(Query.class); //missionArchive
		Query query3 = mock(Query.class); //missionParticipants
		Query query4 = mock(Query.class); //simulationParticipant
		Query query5 = mock(Query.class); //user

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();

		doReturn(query1).when(entityManager).createQuery("DELETE FROM UserMembersStats ums WHERE ums.user.idUser = :userId");
		doReturn(query2).when(entityManager).createQuery("DELETE FROM MissionArchive ma WHERE ma.user.idUser = :userId");
		doReturn(query3).when(entityManager).createQuery("DELETE FROM MissionParticipants ms WHERE ms.user.idUser = :userId");
		doReturn(query4).when(entityManager).createQuery("DELETE FROM SimulationParticipant sp WHERE sp.user.idUser = :userId");
		doReturn(query5).when(entityManager).createQuery("DELETE FROM User u WHERE u.idUser = :userId");

		doReturn(query1).when(query1).setParameter("userId", userId);
		doReturn(query2).when(query2).setParameter("userId", userId);
		doReturn(query3).when(query3).setParameter("userId", userId);
		doReturn(query4).when(query4).setParameter("userId", userId);
		doReturn(query5).when(query5).setParameter("userId", userId);

		doReturn(1).when(query1).executeUpdate();
		doReturn(1).when(query2).executeUpdate();
		doReturn(1).when(query3).executeUpdate();
		doReturn(1).when(query4).executeUpdate();
		doReturn(1).when(query5).executeUpdate();

		//test
		userDao.removeUser(userId, entityManagerHandler);

		//result
		verify(query1, times(1)).setParameter("userId", userId);
		verify(query2, times(1)).setParameter("userId", userId);
		verify(query3, times(1)).setParameter("userId", userId);
		verify(query4, times(1)).setParameter("userId", userId);
		verify(query5, times(1)).setParameter("userId", userId);

		verify(query1, times(1)).executeUpdate();
		verify(query2, times(1)).executeUpdate();
		verify(query3, times(1)).executeUpdate();
		verify(query4, times(1)).executeUpdate();
		verify(query5, times(1)).executeUpdate();
	}

	@Test
	public void shouldThrowDatabaseException_whenTryToRemoveUser() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		Query query1 = mock(Query.class); //userMembersStats
		Query query2 = mock(Query.class); //missionArchive
		Query query3 = mock(Query.class); //missionParticipants
		Query query4 = mock(Query.class); //simulationParticipant
		Query query5 = mock(Query.class); //user

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();

		doReturn(query1).when(entityManager).createQuery("DELETE FROM UserMembersStats ums WHERE ums.user.idUser = :userId");
		doReturn(query2).when(entityManager).createQuery("DELETE FROM MissionArchive ma WHERE ma.user.idUser = :userId");
		doReturn(query3).when(entityManager).createQuery("DELETE FROM MissionParticipants ms WHERE ms.user.idUser = :userId");
		doReturn(query4).when(entityManager).createQuery("DELETE FROM SimulationParticipant sp WHERE sp.user.idUser = :userId");
		doReturn(query5).when(entityManager).createQuery("DELETE FROM User u WHERE u.idUser = :userId");

		doReturn(query1).when(query1).setParameter("userId", userId);
		doReturn(query2).when(query2).setParameter("userId", userId);
		doReturn(query3).when(query3).setParameter("userId", userId);
		doReturn(query4).when(query4).setParameter("userId", userId);
		doReturn(query5).when(query5).setParameter("userId", userId);

		doReturn(1).when(query1).executeUpdate();
		doReturn(1).when(query2).executeUpdate();
		doReturn(1).when(query3).executeUpdate();
		doReturn(1).when(query4).executeUpdate();
		doReturn(1).when(query5).executeUpdate();
		
		//se fallisce l'ultimo fallisce tutto, quindi un solo doThrow
		doThrow(new HibernateException("test")).when(query5).executeUpdate();
		
		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userDao.removeUser(userId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);

	}
}
