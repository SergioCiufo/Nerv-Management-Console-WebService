package com.company.NervManagementConsoleREST.dao.atomic;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.User;


public class UserDao implements DaoInterface<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public UserDao() {
		super();
	}

	@Override
	public void create(User ref, EntityManagerHandler entityManagerHandler) {
	    try {
	    	entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding user: " + ref + e.getMessage());
	        throw new DatabaseException("Unexpected error create user" + ref.getUsername(), e);
	    }
	}
	
	public List<User> retrieve(EntityManagerHandler entityManagerHandler){
	    try {
	    	return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM User ORDER BY userId ASC", User.class)
	    			.getResultList();
	    	
	    } catch (HibernateException e) {
	        logger.error("Error retrieving Users: " + e.getMessage());
	        throw new DatabaseException("Unexpected error during retrieval", e);
	    }
	}
	
	public User getUserById(int userId, EntityManagerHandler entityManagerHandler) {
		try {
	        return entityManagerHandler.getEntityManager()
	        .createQuery("SELECT u FROM User u WHERE u.idUser = :userId", User.class)
	        .setParameter("userId", userId)
	        .getSingleResult();
		} catch (NoResultException e) {
	        logger.error("No user found with id: " + userId);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving user: " + userId + e.getMessage());
	        throw new DatabaseException("Unexpected error during retrieval by userId" +userId, e);
	    }
	}

	public User getUserByUsernameAndPassword(String username, String password, EntityManagerHandler entityManagerHandler) {
	    try {
	        return entityManagerHandler.getEntityManager()
	    		    .createQuery("FROM User u WHERE LOWER(u.username) = :username AND u.password = :password", User.class)
	    		    .setParameter("username", username.toLowerCase())
	    		    .setParameter("password", password)
	    		    .getSingleResult();
	    }catch (NoResultException e) {
	        logger.error("No user found with username: " + username);
	        return null;
	    }catch (HibernateException e) {
	        logger.error("Error retrieving user: " + username + e.getMessage());
	        throw new DatabaseException("Unexpected error during retrieval username: "+ username, e);
	    }
	}

	public User getUserByUsername(String usernamePar, EntityManagerHandler entityManagerHandler) {
		try {
	        return entityManagerHandler.getEntityManager()
	                .createQuery("select x from User x where x.username=:parUser", User.class)
	                .setParameter("parUser", usernamePar)
	                .getSingleResult();
		} catch (NoResultException e) {
	        logger.error("No user found with username: " + usernamePar);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving user: " + usernamePar + e.getMessage());
	        throw new DatabaseException("Unexpected error during retrieval by username: " + usernamePar, e);
	    }
	}
	
	public void updateUser(User user, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.getEntityManager()
		    .createQuery("UPDATE User u " +
		                 "SET u.name = :name, " +
		                 "u.surname = :surname, " +
		                 "u.password = :password, " +
		                 "u.username = :username, " +
		                 "u.image = :image " +
		                 "WHERE u.idUser = :userId")
		    .setParameter("name", user.getName())
		    .setParameter("surname", user.getSurname())
		    .setParameter("password", user.getPassword())
		    .setParameter("username", user.getUsername())
		    .setParameter("image", user.getImage())
		    .setParameter("userId", user.getIdUser())
		    .executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating user by idUser: " + user.getIdUser() + e.getMessage());
	        throw new DatabaseException("Unexpected error during updating user: " + user.getIdUser(), e);
		}
	}
	
	public void removeUser(int userId, EntityManagerHandler entityManagerHandler) {
	    try {
	    	//prossima volta cascata sul model, anziché ammazzarsi così
	    	entityManagerHandler.getEntityManager()
            .createQuery("DELETE FROM UserMembersStats ums WHERE ums.user.idUser = :userId")
            .setParameter("userId", userId)
            .executeUpdate();
	    	
	    	entityManagerHandler.getEntityManager()
            .createQuery("DELETE FROM MissionArchive ma WHERE ma.user.idUser = :userId")
            .setParameter("userId", userId)
            .executeUpdate();
	    	
	    	entityManagerHandler.getEntityManager()
            .createQuery("DELETE FROM MissionParticipants ms WHERE ms.user.idUser = :userId")
            .setParameter("userId", userId)
            .executeUpdate();
	    	
	    	entityManagerHandler.getEntityManager()
            .createQuery("DELETE FROM SimulationParticipant sp WHERE sp.user.idUser = :userId")
            .setParameter("userId", userId)
            .executeUpdate();
	    	
	        entityManagerHandler.getEntityManager()
	        .createQuery("DELETE FROM User u " +
	                     "WHERE u.idUser = :userId")
	        .setParameter("userId", userId)
	        .executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing user for userId: " + userId, e);
	        throw new DatabaseException("Unexpected error during removing User: " + userId, e);
	    }
	}
		
}
