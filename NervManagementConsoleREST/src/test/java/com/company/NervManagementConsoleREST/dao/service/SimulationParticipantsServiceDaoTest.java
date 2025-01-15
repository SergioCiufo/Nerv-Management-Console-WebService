package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.SimulationParticipantsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationParticipantsServiceDaoTest {
	private SimulationParticipantsServiceDao simulationParticipantsServiceDao;

	//oggetto mock
	private SimulationParticipantsDao simulationParticipantsDao = mock(SimulationParticipantsDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationParticipantsServiceDao = new SimulationParticipantsServiceDao();
		}

		Field simulationParticipantsDaoField = simulationParticipantsServiceDao.getClass().getDeclaredField("simulationParticipantsDao");
		simulationParticipantsDaoField.setAccessible(true);
		simulationParticipantsDaoField.set(simulationParticipantsServiceDao, simulationParticipantsDao);

		Field jpaUtilField = simulationParticipantsServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(simulationParticipantsServiceDao, jpaUtil);
	}

	@Test
	public void shouldCreateParticipant_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		SimulationParticipant simulationParticipant = new SimulationParticipant();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(simulationParticipantsDao).createParticipant(simulationParticipant, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		simulationParticipantsServiceDao.createParticipant(simulationParticipant);

		//result
		verify(simulationParticipantsDao, times(1)).createParticipant(simulationParticipant, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}

	@Test
	public void shouldReturnSimulationParticipantByUserAndMember_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Member member = new Member();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(simulationParticipant).when(simulationParticipantsDao).getParticipantbyUserAndMemberId(user, member, entityManagerHandler);

		//test
		SimulationParticipant result = simulationParticipantsServiceDao.getParticipantbyUserAndMemberId(user, member);

		//result
		assertNotNull(result);
		assertEquals(simulationParticipant, result);
		verify(simulationParticipantsDao, times(1)).getParticipantbyUserAndMemberId(user, member, entityManagerHandler);
	}

	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();

		User user = new User();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(simulationParticipantsDao).removeParticipant(user, simulation, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		simulationParticipantsServiceDao.removeParticipant(user, simulation);
		
		//result
		verify(simulationParticipantsDao, times(1)).removeParticipant(user, simulation, entityManagerHandler);
	    verify(entityManagerHandler, times(1)).beginTransaction();
	    verify(entityManagerHandler, times(1)).commitTransaction();
	}
}
