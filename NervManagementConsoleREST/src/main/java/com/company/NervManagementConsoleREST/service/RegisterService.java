package com.company.NervManagementConsoleREST.service;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;

import java.sql.SQLException;
import java.util.List;


public class RegisterService {
	private UserService userService;
	private MemberService memberService;
	private UserMemberStatsService userMemberStatsService;

	public RegisterService() {
		this.userService = new UserService();
		this.memberService = new MemberService();
		this.userMemberStatsService = new UserMemberStatsService();
	}
	
	public void register(String name, String surname, String username, String password) throws SQLException {

			List<Member> defaultMembers = memberService.retrieveMembers();

			User newUser = userService.createUser(name, surname, username, password, defaultMembers);

			userMemberStatsService.createStatsForDefaultMembers(newUser, defaultMembers);
	}

}
