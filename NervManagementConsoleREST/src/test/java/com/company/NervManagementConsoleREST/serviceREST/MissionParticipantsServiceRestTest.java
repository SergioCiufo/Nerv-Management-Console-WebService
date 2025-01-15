package com.company.NervManagementConsoleREST.serviceREST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
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

import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.service.MissionParticipantsService;

public class MissionParticipantsServiceRestTest {
	 private MissionParticipantsServiceRest missionParticipantsServiceRest;

	    //oggetto mock
	    private MissionParticipantsService missionParticipantsService = mock(MissionParticipantsService.class);

	    @BeforeEach
	    public void setup() throws NoSuchFieldException, IllegalAccessException {
	    	
	    	try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
	    		missionParticipantsServiceRest = new MissionParticipantsServiceRest();
			}
	    	
	        Field f = missionParticipantsServiceRest.getClass().getDeclaredField("missionParticipantsService");
	        f.setAccessible(true);
	        f.set(missionParticipantsServiceRest, missionParticipantsService);
	    }

	    @Test
	    public void shouldReturnNoContent_whenGetActiveMissionsIsCalledAndListIsEmpty() throws Exception {
	        //parameters
	        int userId = 1;
	        List<MissionParticipants> listMp = new ArrayList<MissionParticipants>();

	        //mock
	        doReturn(listMp).when(missionParticipantsService).getActiveMissionsByUserId(userId);

	        //test
	        Response response = missionParticipantsServiceRest.getActiveMissions(userId);

	        //result
	        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
	        assertNull(response.getEntity());
	    }

	    @Test
	    public void shouldReturnOk_whenGetActiveMissionsIsCalledAndListIsNotEmpty() throws Exception {
	        //parameters
	        int userId = 1;
	        List<MissionParticipants> listMp = new ArrayList<>();
	        listMp.add(new MissionParticipants());

	        //mock
	        doReturn(listMp).when(missionParticipantsService).getActiveMissionsByUserId(userId);

	        //test
	        Response response = missionParticipantsServiceRest.getActiveMissions(userId);

	        //result
	        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	        assertNotNull(response.getEntity());
	        assertEquals(listMp, response.getEntity());
	    }
}
