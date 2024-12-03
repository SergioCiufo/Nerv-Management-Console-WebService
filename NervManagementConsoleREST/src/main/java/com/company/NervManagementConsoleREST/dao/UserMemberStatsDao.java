package com.company.NervManagementConsoleREST.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class UserMemberStatsDao implements DaoInterface<UserMembersStats> {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public UserMemberStatsDao() {
		super();
	}
	
	@Override
	public void create(UserMembersStats ref, EntityManagerHandler entityManagerHandler) throws SQLException {
	    try {
	    	entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding member to user: " + ref.getUser().getIdUser() + e.getMessage());
	        throw e;
	    }
	}
	
	public List<UserMembersStats> retrieve(EntityManagerHandler entityManagerHandler){
		try {
	    	return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM UserMembersStats ORDER BY idMemberStats ASC", UserMembersStats.class)
	    			.getResultList();
	    	
	    } catch (HibernateException e) {
	        logger.error("Error retrieving UserMembersStats: " + e.getMessage());
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}
	
	public List<UserMembersStats> retrieveByUserId(int userId, EntityManagerHandler entityManagerHandler)throws SQLException {
		try {
			return entityManagerHandler.getEntityManager()
					.createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class)
					.setParameter("userId", userId)
					.getResultList();
	    }catch (NoResultException e) {
	        logger.error("No stats found with userId: " + userId);
	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}

	public UserMembersStats retrieveByUserAndMember(User user, Member member, EntityManagerHandler entityManagerHandler)throws SQLException {
		try {
			return entityManagerHandler.getEntityManager()
					.createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class)
					.setParameter("userId", user.getIdUser())
					.setParameter("memberId", member.getIdMember())
					.getSingleResult();
	    }catch (NoResultException e) {
	        logger.error("No stats found with username: " + user.getIdUser() + " member " + member.getIdMember());
	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
	}
	
    public UserMembersStats retrieveByUserAndMemberId(User user, Integer memberId, EntityManagerHandler entityManagerHandler)throws SQLException  {
		try {
			return entityManagerHandler.getEntityManager()
					.createQuery("FROM UserMembersStats ums "
							+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class)
					.setParameter("userId", user.getIdUser())
					.setParameter("memberId", memberId)
					.getSingleResult();
	    }catch (NoResultException e) {
	        logger.error("No stats found with username: " + user.getIdUser() + " member " + memberId);
	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Unexpected error during retrieval", e);
	    }
    }

    public void updateMembStatsStartSim (User user, Member member, EntityManagerHandler entityManagerHandler) throws SQLException {
    	try {
    		entityManagerHandler.getEntityManager()
    		.createQuery("UPDATE UserMembersStats ums "
    				+ "SET ums.status = :status "
    				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId")
    		.setParameter("status", false)
    		.setParameter("userId", user.getIdUser())
    		.setParameter("memberId", member.getIdMember())
    		.executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating member stats, idUser: " + user.getIdUser() + " memberId: " + member.getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }
    
    public void updateMembStatsCompletedSim (User user, Member member, UserMembersStats ums, EntityManagerHandler entityManagerHandler) throws SQLException {
    	try {
    		entityManagerHandler.getEntityManager()
    		.createQuery("UPDATE UserMembersStats ums "
    				+ "SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
                    "ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
                    "ums.supportAbility = :suppAbility "
    				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId")
    		.setParameter("status", true)
    		.setParameter("exp", ums.getExp())
    		.setParameter("levelPg", ums.getLevel())
    		.setParameter("sincRate", ums.getSynchronizationRate())
    		.setParameter("tactAbility", ums.getTacticalAbility())
    		.setParameter("suppAbility", ums.getSupportAbility())
    		.setParameter("userId", user.getIdUser())
    		.setParameter("memberId", member.getIdMember())
    		.executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating stats member, idUser: " + user.getIdUser() + " memberId: " + member.getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }

    public void updateMembStatsCompletedMission(UserMembersStats uMemberStats, EntityManagerHandler entityManagerHandler) throws SQLException {
        try {
            entityManagerHandler.getEntityManager().merge(uMemberStats);
        } catch (HibernateException e) {
        	logger.error("Error updating stats member, idUser: " + uMemberStats.getUser().getIdUser() + "memberid: " + uMemberStats.getMember().getIdMember() + e.getMessage());
	        throw new SQLException("Error updating member stats", e);
		}
    }
    
}
