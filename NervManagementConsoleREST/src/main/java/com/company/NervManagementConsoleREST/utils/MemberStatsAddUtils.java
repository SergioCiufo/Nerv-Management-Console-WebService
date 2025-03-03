package com.company.NervManagementConsoleREST.utils;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class MemberStatsAddUtils {
	 public static UserMembersStats createStatsMembers(User user, Member member) {
	        switch (member.getName()) {
	            case "Rei":
	                return new UserMembersStats(true, 0, 1, 25, 25, 50, user, member);
	            case "Asuka":
	                return new UserMembersStats(true, 0, 1, 30, 50, 25, user, member);
	            case "Shinji":
	                return new UserMembersStats(true, 0, 1, 30, 30, 30, user, member);
	            default:
	                return new UserMembersStats(true, 0, 1, 0, 0, 0, user, member);
	        }
	    }
}
