package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.utils.CalculateUtils;
import com.company.NervManagementConsoleREST.utils.LevelUpUtils;
import com.company.NervManagementConsoleREST.utils.MissionCodeGeneratorUtils;

public class MissionSendOrCompleteService {
	private MissionService missionService;
	private UserMemberStatsService userMemberStatsService;
	private MissionParticipantsService missionParticipantsService;
	private MissionArchiveService missionArchiveService;
	private final RetriveInformationService retriveInformationService;

	public MissionSendOrCompleteService() {
		super();
		this.missionService = new MissionService();
		this.userMemberStatsService = new UserMemberStatsService();
		this.missionParticipantsService = new MissionParticipantsService();
		this.missionArchiveService = new MissionArchiveService();
		this.retriveInformationService = new RetriveInformationService();
	}

	public User sendMembersMission(User user,  String idMissionString, List<String> idMembersString) throws SQLException {

		List<Integer>idMembers = new ArrayList<Integer>();
		if(idMembersString != null) {
			int idMembPars=0;
			for(String idMembString : idMembersString) {
				idMembPars = Integer.parseInt(idMembString);
				idMembers.add(idMembPars);
			}
		}

		int idMission = Integer.parseInt(idMissionString);
		LocalDateTime startTime = LocalDateTime.now();
		Mission mission = missionService.getMissionById(idMission);
		int duration = mission.getDurationTime();

		LocalDateTime endTime = startTime.plusMinutes(duration);

		Integer tactAbility =null;
		Integer synchRate =null;
		Integer suppAbility =null;

		List<MissionArchive> missionArch = missionArchiveService.retriveByUserIdAndIdMission(user, mission);
		String missionCode = MissionCodeGeneratorUtils.missionCodeGenerator(missionArch, idMission);

		for (Member memb : user.getMembers()) {
			memb.setMemberStats(userMemberStatsService.retrieveStatsByUserAndMember(user, memb));
			if (idMembers.contains(memb.getIdMember())) {
				UserMembersStats stats = memb.getMemberStats();
				if (stats != null) {
					tactAbility = stats.getTacticalAbility();
					synchRate = stats.getSynchronizationRate();
					suppAbility = stats.getSupportAbility();
					MissionArchive missionArchive = new MissionArchive(missionCode, mission, user, memb, startTime,
							endTime, suppAbility, synchRate, tactAbility, MissionResult.PROGRESS);
					missionArchiveService.addMissionArchive(missionArchive);
					MissionParticipants missPartecipants = new MissionParticipants(mission, user, memb);
					missionParticipantsService.startMission(missPartecipants);
					userMemberStatsService.updateMembStatsStartSim(user, memb);
				}
			}
		}
		user = retriveInformationService.retriveUserInformation(user);
		return user;

	}

	public User completeMission(User user, String idMissionString) throws SQLException {
			int idMission = Integer.parseInt(idMissionString);
			Mission mission = missionService.getMissionById(idMission);
			MissionArchive missionArchive = missionArchiveService.retriveByUserIdAndIdMissionResultProgress(user, mission);
			List<MissionParticipants> missionParticipants = missionParticipantsService.getMissionParticipantsByUserIdAndMissionId(user, mission);
			mission.setMissionParticipants(missionParticipants);
			//for ums che fa add con metodo ritira by user e member id
			List<UserMembersStats> ums= new ArrayList<UserMembersStats>();
			for (MissionParticipants mp : missionParticipants) {
				UserMembersStats umStats = userMemberStatsService.retrieveStatsByUserAndMember(user, mp.getMember());
				ums.add(umStats);
			}	
			
			Boolean result = null;
			result = missionResult(result, mission, idMission, ums);
			Integer newExp =mission.getExp();

			for (UserMembersStats uMemberStats : ums) {
				if(result==true) {
					uMemberStats=LevelUpUtils.levelUp(uMemberStats, newExp);
					uMemberStats.setStatus(true);						
					userMemberStatsService.updateMembStatsCompletedMission(uMemberStats);
				}else {
					uMemberStats.setStatus(true);
					userMemberStatsService.updateMembStatsCompletedMission(uMemberStats);
				}
			}

			if (result) {
				missionArchiveService.updateMissionResult(missionArchive, MissionResult.WIN);				
			}else {
				missionArchiveService.updateMissionResult(missionArchive, MissionResult.LOSE);
			}
			missionParticipantsService.removeParticipant(user, mission);
			
			user=retriveInformationService.retriveUserInformation(user);
			return user;
	}

	public Boolean missionResult(Boolean result, Mission mission, Integer idMission, List<UserMembersStats> ums) {
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

		result=CalculateUtils.calculateWinLoseProbability(mission.getSynchronizationRate(), mission.getSupportAbility(), mission.getTacticalAbility(), syncRateToAvg, tactAbilityToAvg, suppAbilityToAvg);
		return result;
	}

}
