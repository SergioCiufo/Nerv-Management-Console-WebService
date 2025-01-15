package com.company.NervManagementConsoleREST.web.servlet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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

public class MissionServletTest {
	private MissionServlet missionServlet; 

	//oggetto mock
	private MissionSendOrCompleteService missionSendOrCompleteService = mock(MissionSendOrCompleteService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionServlet = new MissionServlet();
	    }
		
		Field f = missionServlet.getClass().getDeclaredField("missionSendOrCompleteService");
		f.setAccessible(true);
		f.set(missionServlet, missionSendOrCompleteService);
	}

	@Test //se entra nell'if
	public void shouldSendRedirectToHome_whenAllOk() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMissionString = "idMissionString";
		String[] selectedIdMembers = {"test1", "test2", "test3"}; //è un array di Stringhe vedi riga 28
		List<String> selectedIdMembersListString = Arrays.asList(selectedIdMembers); //vedi riga 29

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER);
		doReturn(idMissionString).when(req).getParameter("missionId");
		doReturn(selectedIdMembers).when(req).getParameterValues("memberSelect");
		doReturn(user).when(missionSendOrCompleteService).sendMembersMission(user, idMissionString, selectedIdMembersListString);
		doReturn(session).when(req).getSession();
		doReturn(path).when(req).getContextPath();

		//test
		missionServlet.doPost(req, resp);

		//results
		verify(session).setAttribute(Costants.KEY_SESSION_USER, user);
		verify(resp).sendRedirect(path + "/jsp/private/Home.jsp");

	}

	@Test //se non entra nell'if (selectedIdMembers è null)
	public void shouldSendRedirectToHome_whenNoMembersSelected() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMissionString = "idMissionString";
		String[] selectedIdMembers = null; //nel caso fosse null

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER);
		doReturn(idMissionString).when(req).getParameter("missionId");
		doReturn(selectedIdMembers).when(req).getParameterValues("memberSelect");
		doReturn(session).when(req).getSession();
		doReturn(path).when(req).getContextPath();
		
		//test
		missionServlet.doPost(req, resp);
		
		//results
	    verify(session).setAttribute(Costants.KEY_SESSION_USER, user);
	    verify(resp).sendRedirect(path + "/jsp/private/Home.jsp");

	}

	@Test
	public void shouldDispatcherToError_whenGenericExceptionReceived() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		User user = new User();
		String path = "localhost:8080";
		String idMissionString = "idMissionString";
		String[] selectedIdMembers = {"selectedIdMembers"}; //è un array di Stringhe vedi riga 28
		List<String> selectedIdMembersListString = Arrays.asList(selectedIdMembers); //vedi riga 29
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		RuntimeException runtimeException = new RuntimeException();

		//mocks
		doReturn(user).when(session).getAttribute(Costants.KEY_SESSION_USER);
		doReturn(idMissionString).when(req).getParameter("missionId");
		doReturn(selectedIdMembers).when(req).getParameterValues("memberSelect");
		doReturn(user).when(missionSendOrCompleteService).sendMembersMission(user, idMissionString, selectedIdMembersListString);
		doReturn(session).when(req).getSession();
		doThrow(runtimeException).when(missionSendOrCompleteService).sendMembersMission(user, idMissionString, selectedIdMembersListString);
		doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Error.jsp");

		//test
		missionServlet.doPost(req, resp);

		//results
		verify(missionSendOrCompleteService).sendMembersMission(user, idMissionString, selectedIdMembersListString);
		verify(dispatcher).forward(req, resp);
	}
}
