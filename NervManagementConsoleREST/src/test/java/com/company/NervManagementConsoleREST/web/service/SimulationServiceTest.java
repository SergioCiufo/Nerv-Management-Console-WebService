package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.SimulationServiceDao;
import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.SimulationService;

public class SimulationServiceTest {
	private SimulationService simulationService;

	//oggetto mock
	private SimulationServiceDao simulationServiceDao = mock(SimulationServiceDao.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationService = new SimulationService();
	    }
		
		Field f = simulationService.getClass().getDeclaredField("simulationServiceDao");
		f.setAccessible(true);
		f.set(simulationService, simulationServiceDao);
	}

	@Test
	public void shouldReturnListSimulation_whenAllOk()throws Exception {
		//parameters
		List<Simulation> simulationList = new ArrayList<Simulation>();

		//mocks
		doReturn(simulationList).when(simulationServiceDao).retrieveSimulations();

		//test
		List<Simulation> result = simulationService.retrieveSimulations();

		//result
		verify(simulationServiceDao).retrieveSimulations();
		assertNotNull(result);
		assertEquals(simulationList, result);
	}

	@Test
	public void shouldReturnListSimulationByUserId_whenAllOk()throws Exception{
		//parameters
		List<Simulation> simulationList = new ArrayList<Simulation>();
		User user = new User();

		//mocks
		doReturn(simulationList).when(simulationServiceDao).getSimulationAndParticipantsByUserId(user);

		//test
		List<Simulation> result = simulationService.getSimulationAndParticipantsByUserId(user);

		//result
		verify(simulationServiceDao).getSimulationAndParticipantsByUserId(user);
		assertNotNull(result);
		assertEquals(simulationList, result);
	}
	
	@Test
	public void shouldReturnSimulationBySimulationId_whenAllOk() throws Exception{
		//parameters
		Simulation simulation = new Simulation();
		
		//mocks
		doReturn(simulation).when(simulationServiceDao).retrieveBySimulationId(anyInt());
		
		//test
		Simulation result = simulationService.retrieveBySimulationId(123);
		
		//result
		verify(simulationServiceDao).retrieveBySimulationId(anyInt());
		assertNotNull(result);
		assertEquals(simulation, result);
	}
}
