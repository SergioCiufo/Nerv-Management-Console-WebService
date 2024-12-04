package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class UserMemberStatsServiceDao {
	private UserMemberStatsDao userMemberStatsDao;
	
	public UserMemberStatsServiceDao() {
		this.userMemberStatsDao = new UserMemberStatsDao();
	}
	
	public void saveStats(UserMembersStats stats) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.create(stats, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public UserMembersStats retrieveStatsByUserAndMember(User user, Member member) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);
		}
	}
	
	public void updateMembStatsStartSim(User user, Member member) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsStartSim(user, member, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMembStatsCompletedSim(User user, Member member, UserMembersStats ums) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMembStatsCompletedMission(UserMembersStats ums) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsCompletedMission(ums, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	 public List<UserMembersStats> retrieveByUserId(int userId) throws SQLException{
		 try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			 return userMemberStatsDao.retrieveByUserId(userId, entityManagerHandler);
		 }
	 }
}
