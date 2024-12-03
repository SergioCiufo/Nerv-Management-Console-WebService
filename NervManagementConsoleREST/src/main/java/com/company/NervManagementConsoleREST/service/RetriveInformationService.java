package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.dao.MemberDao;
import com.company.NervManagementConsoleREST.dao.MissionArchiveDao;
import com.company.NervManagementConsoleREST.dao.MissionDao;
import com.company.NervManagementConsoleREST.dao.MissionParticipantsDao;
import com.company.NervManagementConsoleREST.dao.SimulationDao;
import com.company.NervManagementConsoleREST.dao.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class RetriveInformationService {
	private MemberDao memberDao;
	private UserMemberStatsDao userMemberStatsDao;
	private SimulationDao simulationDao;
	private MissionDao missionDao;
	private MissionParticipantsDao missionParticipantsDao;
	private MissionArchiveDao missionArchiveDao;
	
	public RetriveInformationService() {
		super();
		this.memberDao=new MemberDao();
		this.userMemberStatsDao=new UserMemberStatsDao();
		this.simulationDao = new SimulationDao();
		this.missionDao = new MissionDao();
		this.missionParticipantsDao = new MissionParticipantsDao();
		this.missionArchiveDao = new MissionArchiveDao();
	}
	
	public User retriveUserInformation(User user, EntityManagerHandler entityManagerHandler) throws SQLException {
			entityManagerHandler.clear(); //non venivano aggiornate le informazioni "nuove" del db, si pulisce la cache
			List<Member> memberList = memberDao.retrieve(entityManagerHandler);
			List<Simulation> simulationList = simulationDao.retrieve(entityManagerHandler);

			user.setMembers(memberList);
			user.setSimulations(simulationList);
			if(memberList != null) {
				for (Member member : memberList) {

					UserMembersStats stats = userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);
					member.setMemberStats(stats);
				}
				user = retriveSimulationAndPartecipant(user, entityManagerHandler);
				user = recoverUserMemberMission(user, entityManagerHandler);
				user = recoverMemberStats(user, entityManagerHandler);
			}

			return user;
	}
	
	public User retriveSimulationAndPartecipant(User user, EntityManagerHandler entityManagerHandler) throws SQLException {
			if (user.getParticipants() == null) {
				user.setParticipants(new ArrayList<>());
			}
			List<Simulation> simulations = simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);

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
	
	public User recoverUserMemberMission(User user, EntityManagerHandler entityManagerHandler) throws SQLException {
			List<Mission> mission = missionDao.retrieve(entityManagerHandler);
			List<MissionArchive> missionArchive = new ArrayList<MissionArchive>();
			List<MissionParticipants> allMissionParticipants = new ArrayList<>();
			for(Mission m : mission) {
				List<MissionParticipants> missionParticipants = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, m, entityManagerHandler);		
				allMissionParticipants.addAll(missionParticipants);	 
				m.setMissionParticipants(missionParticipants);
				List<MissionArchive> archives = missionArchiveDao.retriveByUserIdAndIdMission(user, m, entityManagerHandler);
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
	
	public User recoverMemberStats(User user, EntityManagerHandler entityManagerHandler) throws SQLException{
			UserMembersStats ums = null;
			for(Member m : user.getMembers()) {
				ums = userMemberStatsDao.retrieveByUserAndMemberId(user, m.getIdMember(), entityManagerHandler);
				m.setMemberStats(ums);
			}
			return user;
	}
}
