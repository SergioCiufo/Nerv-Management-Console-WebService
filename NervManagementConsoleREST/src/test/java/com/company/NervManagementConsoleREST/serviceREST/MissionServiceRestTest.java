package com.company.NervManagementConsoleREST.serviceREST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.service.MissionService;

public class MissionServiceRestTest {

    private MissionServiceRest missionServiceRest;

    //oggetto mock
    private MissionService missionService = mock(MissionService.class);

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
    	
    	try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
    		missionServiceRest = new MissionServiceRest();
		}
    	
        Field f = missionServiceRest.getClass().getDeclaredField("missionService");
        f.setAccessible(true);
        f.set(missionServiceRest, missionService);
    }

    @Test
    public void shouldReturnMissions_whenGetMissionsIsCalled() throws Exception {
        //parameters
        List<Mission> missions = new ArrayList<Mission>();
        missions.add(new Mission());

        //mock
        doReturn(missions).when(missionService).retrieveMissions();

        //test
        List<Mission> response = missionServiceRest.getMissions();

        //result
        assertNotNull(response);
        assertEquals(missions, response);
    }

    @Test
    public void shouldReturnNoContent_whenAddMissionIsCalledAndMissionIsValid() throws Exception {
        //parameters
        Mission mission = new Mission();

        //mock
        doNothing().when(missionService).addMission(mission);

        //test
        Response response = missionServiceRest.addMission(mission);

        //result
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldReturnBadRequest_whenAddMissionIsCalledAndMissionFails() throws Exception {
        //parameters
        Mission mission = new Mission();

        //mock
        doThrow(new RuntimeException("Mission already exists")).when(missionService).addMission(mission);

        //test
        Response response = missionServiceRest.addMission(mission);

        //result
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldReturnNoContent_whenUpdateMissionIsCalledAndMissionIsValid() throws Exception {
        //parameters
        Mission mission = new Mission();

        //mock
        doNothing().when(missionService).updateMission(mission);

        //test
        Response response = missionServiceRest.updateMission(mission);

        //result
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldReturnBadRequest_whenUpdateMissionIsCalledAndMissionFails() throws Exception {
        // parameters
        Mission mission = new Mission();

        //mock
        doThrow(new RuntimeException("Mission not found")).when(missionService).updateMission(mission);

        //test
        Response response = missionServiceRest.updateMission(mission);

        //result
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
