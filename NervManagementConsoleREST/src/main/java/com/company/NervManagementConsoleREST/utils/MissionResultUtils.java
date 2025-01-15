package com.company.NervManagementConsoleREST.utils;

import java.util.ArrayList;
import java.util.List;

import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class MissionResultUtils {
	public Boolean missionResult(Mission mission, Integer idMission, List<UserMembersStats> ums) {
		Boolean result = null;
		List<Integer>syncRateToAvg = new ArrayList<Integer>();
		List<Integer>tactAbilityToAvg = new ArrayList<Integer>();
		List<Integer>suppAbilityToAvg = new ArrayList<Integer>();

		for(MissionParticipants miss : mission.getMissionParticipants()) {
			if(miss.getMission().getMissionId().equals(idMission)) {

				for (UserMembersStats uMemberStats : ums) {
					if (uMemberStats.getMember().getIdMember().equals(miss.getMember().getIdMember())) {
						Integer syncRate = uMemberStats.getSynchronizationRate();
						Integer tactAbility = uMemberStats.getTacticalAbility();
						Integer suppAbility = uMemberStats.getSupportAbility();
						syncRateToAvg.add(syncRate);
						tactAbilityToAvg.add(tactAbility);
						suppAbilityToAvg.add(suppAbility);
					}
				}
			}
		}
		CalculateUtils calculateUtils = new CalculateUtils();
		result=calculateUtils.calculateWinLoseProbability(mission.getSynchronizationRate(), mission.getSupportAbility(), mission.getTacticalAbility(), syncRateToAvg, tactAbilityToAvg, suppAbilityToAvg);
		return result;
	}
}
