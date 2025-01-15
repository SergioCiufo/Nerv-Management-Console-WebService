package com.company.NervManagementConsoleREST.web.servlet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.SimulationSendOrCompleteService;
import com.company.NervManagementConsoleREST.utils.Costants;


public class SimulationServletTest {
	private SimulationServlet simulationServlet;

	//oggetto mock
	private SimulationSendOrCompleteService simulationSendOrCompleteService = mock(SimulationSendOrCompleteService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationServlet = new SimulationServlet();
	    }
		
		Field f = simulationServlet.getClass().getDeclaredField("simulationSendOrCompleteService");
		f.setAccessible(true);
		f.set(simulationServlet, simulationSendOrCompleteService);
	}

	@Test
	public void shouldSendRedirectToHome_whenAllOk() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMember = "idMember";
		String idSimulation = "idSimulation";

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER); //riga 26 simulationServlet
		doReturn(idMember).when(req).getParameter("memberSelect");
		doReturn(idSimulation).when(req).getParameter("simulationId");
		doReturn(user).when(simulationSendOrCompleteService).sendMemberSimulation(user, idMember, idSimulation);
		doReturn(session).when(req).getSession();
		doReturn(path).when(req).getContextPath();

		//test
		simulationServlet.doPost(req, resp);

		//results
		verify(session).setAttribute(Costants.KEY_SESSION_USER, user); //verifica che la sessione sia aggiornata
		verify(resp).sendRedirect(path +  "/jsp/private/Home.jsp"); //verifica il redirect
	}

	@Test
	public void shouldDispatcherToError_whenGenericExceptionReceived() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMember = "idMember";
		String idSimulation = "idSimulation";
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		RuntimeException runtimeException = new RuntimeException("test", null);
		
		//mocks
		doReturn(session).when(req).getSession();
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER); //riga 26 simulationServlet
		doReturn(idMember).when(req).getParameter("memberSelect");
		doReturn(idSimulation).when(req).getParameter("simulationId");
		doThrow(runtimeException).when(simulationSendOrCompleteService).sendMemberSimulation(user, idMember, idSimulation);
		doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Error.jsp");
	
		//test
		simulationServlet.doPost(req, resp);
		
		//results
		verify(simulationSendOrCompleteService).sendMemberSimulation(user, idMember, idSimulation);
		verify(dispatcher).forward(req, resp);
		
	}
}
