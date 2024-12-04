package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MemberDao;
import com.company.NervManagementConsoleREST.model.Member;

public class MemberServiceDao {
	private MemberDao memberDao;
	
	public MemberServiceDao() {
		this.memberDao = new MemberDao();
	}
	
	public List<Member> retrieveMembers() {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return memberDao.retrieve(entityManagerHandler);
		}
	}
	
    public Member retrieveByMemberId (int idMember) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		return memberDao.retrieveByMemberId(idMember, entityManagerHandler);
    	}
    }
    
}
