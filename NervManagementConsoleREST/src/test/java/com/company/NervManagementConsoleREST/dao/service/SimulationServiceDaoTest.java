package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.SimulationDao;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationServiceDaoTest {
	private SimulationServiceDao simulationServiceDao;

	//oggetto mock
	private SimulationDao simulationDao = mock(SimulationDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationServiceDao = new SimulationServiceDao();
		}

		Field simulationDaoField = simulationServiceDao.getClass().getDeclaredField("simulationDao");
		simulationDaoField.setAccessible(true);
		simulationDaoField.set(simulationServiceDao, simulationDao);

		Field jpaUtilField = simulationServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(simulationServiceDao, jpaUtil);
	}

	@Test
	public void shouldReturnSimulationList_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		List<Simulation> simulationList = new ArrayList<Simulation>();
		Simulation simulation = new Simulation();
		simulationList.add(simulation);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doReturn(simulationList).when(simulationDao).retrieve(entityManagerHandler);

		//test
		List<Simulation> result = simulationServiceDao.retrieveSimulations();

		//result
		assertNotNull(result);
		assertEquals(simulationList, result);
		verify(simulationDao, times(1)).retrieve(entityManagerHandler);
	}

	@Test
	public void shouldReturnSimulationListByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		List<Simulation> simulationList = new ArrayList<Simulation>();
		Simulation simulation = new Simulation();
		simulationList.add(simulation);

		User user = new User();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doReturn(simulationList).when(simulationDao).getSimulationAndParticipantsByUserId(user, entityManagerHandler);

		//test
		List<Simulation> result = simulationServiceDao.getSimulationAndParticipantsByUserId(user);

		//result
		assertNotNull(result);
		assertEquals(simulationList, result);
		verify(simulationDao, times(1)).getSimulationAndParticipantsByUserId(user, entityManagerHandler);
	}

	@Test
	public void shouldReturnSimulationBySimulationId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();

		int simulationId=1;
		
		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doReturn(simulation).when(simulationDao).retrieveBySimulationId(simulationId, entityManagerHandler);

		//test
		Simulation result = simulationServiceDao.retrieveBySimulationId(simulationId);

		//result
		assertNotNull(result);
		assertEquals(simulation, result);
		verify(simulationDao, times(1)).retrieveBySimulationId(simulationId, entityManagerHandler);
	}
}
