package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.dao.service.MemberServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;


public class MemberService {
    private MemberServiceDao memberServiceDao;

    public MemberService() {
        this.memberServiceDao = new MemberServiceDao();
    }

    public List<Member> retrieveMembers() throws SQLException {
    	return memberServiceDao.retrieveMembers();
    }
    
    public Member retrieveByMemberId (int idMember) throws SQLException {
    	return memberServiceDao.retrieveByMemberId(idMember);
    }
    
    
    /*
    public List<Member> retrieveMembersWithStats(User user, EntityManagerHandler entityManagerHandler) throws SQLException {
        List<Member> members = memberDao.retrieve(entityManagerHandler);
        for (Member member : members) {
            UserMembersStats stats = userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);
            member.setMemberStats(stats);
        }
        return members;
    }
    */
    
}