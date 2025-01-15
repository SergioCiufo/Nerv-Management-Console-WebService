package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.SimulationParticipantsServiceDao;
import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.SimulationParticipantsService;

public class SimulationParticipantsServiceTest {
	private SimulationParticipantsService simulationParticipantsService;
	
	//oggetto mock
	private SimulationParticipantsServiceDao simulationParticipantsServiceDao = mock(SimulationParticipantsServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationParticipantsService = new SimulationParticipantsService();
	    }
		
		Field f = simulationParticipantsService.getClass().getDeclaredField("simulationParticipantsServiceDao");
		f.setAccessible(true);
		f.set(simulationParticipantsService, simulationParticipantsServiceDao);
	}
	
	@Test
	public void shouldCreateParticipant_whenAllOk() throws Exception{
		//parameters
		SimulationParticipant simulationParticipant = new SimulationParticipant();
		
		//mocks
		doNothing().when(simulationParticipantsServiceDao).createParticipant(simulationParticipant);
		
		//test
		simulationParticipantsService.createParticipant(simulationParticipant);
		
		///verify
		verify(simulationParticipantsServiceDao).createParticipant(simulationParticipant);
	}
	
	@Test
	public void shouldReturnSimulationParticipanbyUserAndMemberId_whenAllOk() throws Exception{
		//parameters
		SimulationParticipant simulationParticipant = new SimulationParticipant();
		User user = new User();
		Member member = new Member();
		
		//mocks
		doReturn(simulationParticipant).when(simulationParticipantsServiceDao).getParticipantbyUserAndMemberId(user, member);
		
		//test
		SimulationParticipant result = simulationParticipantsService.getParticipantbyUserAndMemberId(user, member);
		
		//results
		verify(simulationParticipantsServiceDao).getParticipantbyUserAndMemberId(user, member);
		assertNotNull(result);
		assertEquals(simulationParticipant, result);
		
	}
	
	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		Simulation simulation = new Simulation();
		
		//mocks
		doNothing().when(simulationParticipantsServiceDao).removeParticipant(user, simulation);
		
		//test
		simulationParticipantsService.removeParticipant(user, simulation);
		
		//results
		verify(simulationParticipantsServiceDao).removeParticipant(user, simulation);
	}
}
