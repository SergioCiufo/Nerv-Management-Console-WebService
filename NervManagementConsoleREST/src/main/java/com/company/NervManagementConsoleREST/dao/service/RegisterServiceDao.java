package com.company.NervManagementConsoleREST.dao.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MemberDao;
import com.company.NervManagementConsoleREST.dao.atomic.UserDao;
import com.company.NervManagementConsoleREST.dao.atomic.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

public class RegisterServiceDao {
	private UserDao userDao;
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	private MemberStatsAddUtils memberStatsAddUtils;
	private JpaUtil jpaUtil;
	private final Logger logger = LoggerFactory.getLogger(RegisterServiceDao.class);
	
	public RegisterServiceDao() {
		super();
		this.userDao = new UserDao();
		this.memberDao = new MemberDao();
		this.userMemberStatsDao = new UserMemberStatsDao();
		this.memberStatsAddUtils = new MemberStatsAddUtils();
		this.jpaUtil = new JpaUtil();
	}
	
	public void registerUser(User newUser) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			List<Member> defaultMemberList = memberDao.retrieve(entityManagerHandler);
			userDao.create(newUser, entityManagerHandler);
			newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername(), entityManagerHandler).getIdUser());
			for (Member member : defaultMemberList) {
	            if (member.getIdMember() != null) {
	                UserMembersStats stats = memberStatsAddUtils.createStatsMembers(newUser, member);
	                userMemberStatsDao.create(stats, entityManagerHandler);
	                member.setMemberStats(stats);
	            }
			}
			entityManagerHandler.commitTransaction();
		}
	}
	
	
}
