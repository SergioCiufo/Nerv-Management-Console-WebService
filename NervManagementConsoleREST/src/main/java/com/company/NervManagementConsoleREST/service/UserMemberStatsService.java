package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.service.UserMemberStatsServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

//Dao Secondo Livello
//servizio Dao, qui insieriamo la logica di business legata al trattamento dei dati
//faranno chiamate al dao di primo livello
public class UserMemberStatsService {
    private UserMemberStatsServiceDao userMemberStatsServiceDao;
    private final Logger logger = LoggerFactory.getLogger(UserMemberStatsService.class);

    public UserMemberStatsService() {
        this.userMemberStatsServiceDao = new UserMemberStatsServiceDao();
    }

    public void createStatsForDefaultMembers(User user, List<Member> members) {
        for (Member member : members) {
            if (member.getIdMember() != null) {
                UserMembersStats stats = MemberStatsAddUtils.createStatsMembers(user, member);
                userMemberStatsServiceDao.saveStats(stats);
                member.setMemberStats(stats);
            } else {
                logger.error("Member ID is null for: " + member.getIdMember() + member.getName() + member.getSurname());
            }
        }
    }
    
    public UserMembersStats retrieveStatsByUserAndMember(User user, Member member) {
    	return userMemberStatsServiceDao.retrieveStatsByUserAndMember(user, member);
    }
    
    public void updateMembStatsStartSim(User user, Member member) {
		userMemberStatsServiceDao.updateMembStatsStartSim(user, member);
	}
    
    public void updateMembStatsCompletedSim(User user, Member member, UserMembersStats ums) {
    	userMemberStatsServiceDao.updateMembStatsCompletedSim(user, member, ums);
    }
    
    public void updateMembStatsCompletedMission(UserMembersStats ums) {
    	userMemberStatsServiceDao.updateMembStatsCompletedMission(ums);
    }
    
    public List<UserMembersStats> retrieveByUserId(int userId){
    	return userMemberStatsServiceDao.retrieveByUserId(userId);
    }
  
}