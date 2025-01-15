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

import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;

public class MissionArchiveServiceRestTest {
	private MissionArchiveServiceRest missionArchiveServiceRest; 

	//oggetto mock
	private MissionArchiveService missionArchiveService = mock(MissionArchiveService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionArchiveServiceRest = new MissionArchiveServiceRest();
		}
		
		Field f = missionArchiveServiceRest.getClass().getDeclaredField("missionArchiveService");
		f.setAccessible(true);
		f.set(missionArchiveServiceRest, missionArchiveService);
	}

	@Test
	public void shouldReturnNoContent_whenGetArchiveMissionsIsCalledAndListIsEmpty() throws Exception {
		//parameters
		int userId = 1;
		List<MissionArchive> listMa = new ArrayList<MissionArchive>();

		//mock
		doReturn(listMa).when(missionArchiveService).retriveByUserId(userId);

		//test
		Response response = missionArchiveServiceRest.getArchiveMissions(userId);

		//result
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		assertNull(response.getEntity());
	}

	@Test
	public void shouldReturnOk_whenGetArchiveMissionsIsCalledAndListIsNotEmpty() throws Exception {
		//parameters
		int userId = 1;
		List<MissionArchive> listMa = new ArrayList<>();
		listMa.add(new MissionArchive());

		//mock
		doReturn(listMa).when(missionArchiveService).retriveByUserId(userId);

		//test
		Response response = missionArchiveServiceRest.getArchiveMissions(userId);

		//result
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertNotNull(response.getEntity());
		assertEquals(listMa, response.getEntity());
	}
}
