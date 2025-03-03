package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class RetriveInformationService {
	private MemberService memberService;
	private UserMemberStatsService userMemberStatsService;
	private SimulationService simulationService;
	private MissionService missionService;
	private MissionParticipantsService missionParticipantsService;
	private MissionArchiveService missionArchiveService;

	//non serve mettterlo nel dao //non ci sono problemi transazionali //chiamate in get
	public RetriveInformationService() {
		super();
		this.memberService=new MemberService();
		this.userMemberStatsService=new UserMemberStatsService();
		this.simulationService = new SimulationService();
		this.missionService = new MissionService();
		this.missionParticipantsService = new MissionParticipantsService();
		this.missionArchiveService = new MissionArchiveService();
	}
	
	public User retriveUserInformation(User user) throws SQLException {
			//entityManagerHandler.clear(); //non venivano aggiornate le informazioni "nuove" del db, si pulisce la cache
			List<Member> memberList = memberService.retrieveMembers();
			List<Simulation> simulationList = simulationService.retrieveSimulations();

			user.setMembers(memberList);
			user.setSimulations(simulationList);
			if(memberList != null) {
				for (Member member : memberList) {

					UserMembersStats stats = userMemberStatsService.retrieveStatsByUserAndMember(user, member);
					member.setMemberStats(stats);
				}
				user = retriveSimulationAndPartecipant(user);
				user = recoverUserMemberMission(user);
				user = recoverMemberStats(user);
			}

			return user;
	}
	
	public User retriveSimulationAndPartecipant(User user) throws SQLException {
			if (user.getParticipants() == null) {
				user.setParticipants(new ArrayList<>());
			}
			List<Simulation> simulations = simulationService.getSimulationAndParticipantsByUserId(user);

			if (simulations != null && !simulations.isEmpty()) {
				user.getParticipants().clear();
				for (Simulation simulation : simulations) {
					if (simulation.getSimulationParticipants() != null) {
						for (SimulationParticipant participant : simulation.getSimulationParticipants()) {
							user.getParticipants().add(participant);
						}
					}
				}
			} else {
				user.getParticipants().clear();
			}

			return user;
	}
	
	public User recoverUserMemberMission(User user) throws SQLException {
			List<Mission> mission = missionService.retrieveMissions();
			List<MissionArchive> missionArchive = new ArrayList<MissionArchive>();
			List<MissionParticipants> allMissionParticipants = new ArrayList<>();
			for(Mission m : mission) {
				List<MissionParticipants> missionParticipants = missionParticipantsService.getMissionParticipantsByUserIdAndMissionId(user, m);		
				allMissionParticipants.addAll(missionParticipants);	 
				m.setMissionParticipants(missionParticipants);
				List<MissionArchive> archives = missionArchiveService.retriveByUserIdAndIdMission(user, m);
				missionArchive.addAll(archives);	
			}		
			user.setMissionParticipants(allMissionParticipants);
			user.setMissions(mission);
			user.setMissionArchive(missionArchive);

			Map<String, List<MissionArchive>> missionArchiveMap = new LinkedHashMap<>();
			for(MissionArchive mArc : missionArchive) {
				String missionCode = mArc.getMissionCode();
				String[] parts = missionCode.split("-");
				String missionKey = parts[0];
				missionArchiveMap.putIfAbsent(missionKey, new ArrayList<>());
				missionArchiveMap.get(missionKey).add(mArc);
			}

			// Ordinamento delle chiavi e delle liste
			Map<String, List<MissionArchive>> orderKeyMap = new LinkedHashMap<>();
			missionArchiveMap.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(entry -> {
				List<MissionArchive> archives = entry.getValue();

				// Ordinamento della lista di MissionArchive per numero del tentativo
				archives.sort((m1, m2) -> {
					String code1 = m1.getMissionCode();
					String code2 = m2.getMissionCode();

					int attempt1 = Integer.parseInt(code1.substring(code1.lastIndexOf("-") + 1));
					int attempt2 = Integer.parseInt(code2.substring(code2.lastIndexOf("-") + 1));
					return Integer.compare(attempt1, attempt2);
				});
				orderKeyMap.put(entry.getKey(), archives);
			});
			user.setMissionArchiveResult(orderKeyMap);
			return user;		
	}
	
	public User recoverMemberStats(User user) throws SQLException{
			UserMembersStats ums = null;
			for(Member m : user.getMembers()) {
				ums = userMemberStatsService.retrieveStatsByUserAndMember(user, m);
				m.setMemberStats(ums);
			}
			return user;
	}
	
}
