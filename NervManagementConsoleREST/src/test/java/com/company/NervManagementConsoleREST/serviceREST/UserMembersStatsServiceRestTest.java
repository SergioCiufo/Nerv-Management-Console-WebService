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

import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;

public class UserMembersStatsServiceRestTest {
	private UserMembersStatsServiceRest userMembersStatsServiceRest;
	
	//oggetto mock
	private UserMemberStatsService userMemberStatsService = mock(UserMemberStatsService.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userMembersStatsServiceRest = new UserMembersStatsServiceRest();
		}
		
		Field f = userMembersStatsServiceRest.getClass().getDeclaredField("userMemberStatsService");
		f.setAccessible(true);
		f.set(userMembersStatsServiceRest, userMemberStatsService);
	}
	
	@Test
	public void shouldReturnNoContent_whenGetUserMembersIsCalledAndListIsEmpty() throws Exception{
		//parameters
		int userId =1;
		List<UserMembersStats> listUsm = new ArrayList<UserMembersStats>();
		
		//mock
		doReturn(listUsm).when(userMemberStatsService).retrieveByUserId(userId);
		
		//test
		Response response = userMembersStatsServiceRest.getUserMembers(userId);
		
		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
	}
	
	@Test
    public void shouldReturnOk_whenGetUserMembersIsCalledAndListIsNotEmpty() throws Exception {
        //parameters
        int userId = 1;
        List<UserMembersStats> listUsm = new ArrayList<UserMembersStats>();
        listUsm.add(new UserMembersStats());
        
        //mock
        doReturn(listUsm).when(userMemberStatsService).retrieveByUserId(userId);

        //test
        Response response = userMembersStatsServiceRest.getUserMembers(userId);

        //result
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(listUsm, response.getEntity());
    }
}
