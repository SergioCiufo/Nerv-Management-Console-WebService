package com.company.NervManagementConsoleREST.serviceSOAP;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.service.MissionService;

@WebService
public class MissionEventServiceSoap {
	private static final Logger logger = LoggerFactory.getLogger(MissionEventServiceSoap.class);
	private MissionService missionService = new MissionService();
	
	@WebMethod(operationName = "addMissionEvent")
	public Mission addMissionEvent(@WebParam(name = "mission") Mission mission) {
		try {
			missionService.addEventMission(mission);
			return mission;
		} catch (Exception e) {
			logger.error("Error while adding the event to the mission: ", mission, e);
			return null;
		}
	}
}
