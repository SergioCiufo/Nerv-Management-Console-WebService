package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

public class UserMemberStatsServiceDao {
	private UserMemberStatsDao userMemberStatsDao;
	private JpaUtil jpaUtil;
	private MemberStatsAddUtils memberStatsAddUtils;
	private final Logger logger = LoggerFactory.getLogger(UserMemberStatsDao.class);
	
	public UserMemberStatsServiceDao() {
		this.userMemberStatsDao = new UserMemberStatsDao();
		this.jpaUtil = new JpaUtil();
		this.memberStatsAddUtils = new MemberStatsAddUtils();
	}
	
	public void saveStats(User user, List<Member> members) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			for (Member member : members) {
	            if (member.getIdMember() != null) {
	                UserMembersStats stats = memberStatsAddUtils.createStatsMembers(user, member);
	                userMemberStatsDao.create(stats, entityManagerHandler);
	                member.setMemberStats(stats);
	            }
	        }
			entityManagerHandler.commitTransaction();
		}
	}
	
	public UserMembersStats retrieveStatsByUserAndMember(User user, Member member) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);
		}
	}
	
	public void updateMembStatsStartSim(User user, Member member) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsStartSim(user, member, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMembStatsCompletedSim(User user, Member member, UserMembersStats ums){
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMembStatsCompletedMission(UserMembersStats ums) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			userMemberStatsDao.updateMembStatsCompletedMission(ums, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	 public List<UserMembersStats> retrieveByUserId(int userId) {
		 try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			 return userMemberStatsDao.retrieveByUserId(userId, entityManagerHandler);
		 }
	 }
}
