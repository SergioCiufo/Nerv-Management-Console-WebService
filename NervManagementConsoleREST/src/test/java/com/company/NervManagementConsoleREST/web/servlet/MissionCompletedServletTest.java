package com.company.NervManagementConsoleREST.web.servlet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

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
import com.company.NervManagementConsoleREST.service.MissionSendOrCompleteService;
import com.company.NervManagementConsoleREST.utils.Costants;

public class MissionCompletedServletTest {
	private MissionCompletedServlet missionCompletedServlet; 

	//oggetto mock
	private MissionSendOrCompleteService missionSendOrCompleteService = mock(MissionSendOrCompleteService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionCompletedServlet = new MissionCompletedServlet();
	    }
		
		Field f = missionCompletedServlet.getClass().getDeclaredField("missionSendOrCompleteService");
		f.setAccessible(true);
		f.set(missionCompletedServlet, missionSendOrCompleteService);
	}

	@Test
	public void shouldSendRedirectToHome_whenAllOk() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMissionString = "idMissionString";

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER);
		doReturn(idMissionString).when(req).getParameter("missionId");
		doReturn(user).when(missionSendOrCompleteService).completeMission(user, idMissionString);
		doReturn(session).when(req).getSession();
		doReturn(path).when(req).getContextPath();

		//test
		missionCompletedServlet.doPost(req, resp);

		//results
		verify(session).setAttribute(Costants.KEY_SESSION_USER, user);
		verify(resp).sendRedirect(path + "/jsp/private/Home.jsp");

	}

	@Test
	public void shouldDispatcherToError_whenGenericExceptionReceived() throws Exception{
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMissionString = "idMissionString";
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		RuntimeException runtimeException = new RuntimeException();

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER);
		doReturn(idMissionString).when(req).getParameter("missionId");
		doReturn(user).when(missionSendOrCompleteService).completeMission(user, idMissionString);
		doReturn(session).when(req).getSession();
		doThrow(runtimeException).when(missionSendOrCompleteService).completeMission(user, idMissionString);
		doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Error.jsp");
		
		//test
		missionCompletedServlet.doPost(req, resp);
		
		//results
		verify(missionSendOrCompleteService).completeMission(user, idMissionString);
		verify(dispatcher).forward(req, resp);
	}
}
