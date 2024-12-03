package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.MemberDao;
import com.company.NervManagementConsoleREST.dao.UserDao;
import com.company.NervManagementConsoleREST.dao.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

import org.slf4j.Logger;

public class RegisterService {
	private UserDao userDao;
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
	
	public RegisterService() {
		super();
		this.userDao= new UserDao();
		this.memberDao=new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
	}
	
	public void register(String name, String surname, String username, String password) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			List<Member> defaultMembers = memberDao.retrieve(entityManagerHandler);
			User newUser = new User(name, surname, username, password, defaultMembers);
			userDao.create(newUser, entityManagerHandler);
			newUser.setIdUser(userDao.getUserByUsername(newUser.getUsername(), entityManagerHandler).getIdUser());

			for (Member member : defaultMembers) {
				if (member.getIdMember() != null) {
					UserMembersStats stats = MemberStatsAddUtils.createStatsMembers(newUser, member);
					userMemberStatsDao.create(stats, entityManagerHandler);
					member.setMemberStats(stats);
				} else {
					logger.error("Member ID is null for: " + member.getIdMember() + member.getName() + member.getSurname());
				}
			}
			newUser.setMembers(defaultMembers);
			entityManagerHandler.commitTransaction();
		}
	}
	
}
