package com.company.NervManagementConsoleREST.web.servlet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.utils.Costants;

public class LogoutServletTest {
	private LogoutServlet logoutServlet;
	
	//non serve un oggetto mock per questa classe perché non utilizza alcun servizio o dipendenza esterna da simulare
	//in questo caso non serve neanche il @BeforeEach. Poiché non sta inizializzando nessun oggetto o dipendenza complessa
	
	@BeforeEach
	public void setup() {
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			logoutServlet = new LogoutServlet();
	    }
	}
	
	@Test
	public void shouldRemoveSession_whenAllOk() throws Exception {
		//parameters
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		String path = "localHost:8080";
		
		//mocks  //in questo blocco crei l'ambiente in cui il test agirà. Qui simuli il comportamento delle dipendenze esterne, come HttpServletRequest, HttpServletResponse e HttpSession, che il codice del servlet interagirà durante l'esecuzione del test.
		doReturn(session).when(req).getSession(); //otteniamo la sessione per poi rimuoverla nel result
		doReturn(path).when(req).getContextPath();
		
		//test
		logoutServlet.doPost(req, resp);
		
		//result
		verify(session).removeAttribute(Costants.KEY_SESSION_USER);
		verify(resp).sendRedirect(path + "/jsp/private/Home.jsp");
	}
	
	@Test
	public void shouldRedirectToError_whenIllegalStateExceptionReceived() throws Exception {
	    //parameters
	    HttpServletRequest req = mock(HttpServletRequest.class);
	    HttpServletResponse resp = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	    String path = "localHost:8080";
	    RuntimeException runtimeException = new RuntimeException("Session error");

	    //mocks
	    doReturn(session).when(req).getSession(); // prendi la sessione
	    doThrow(runtimeException).when(req).getSession(); // simula l'eccezione //meglio catchall a meno ché non mi serve un eccezione specifica che serve qualcosa (se c'è esigenza)
	    doReturn(dispatcher).when(req).getRequestDispatcher("/jsp/public/Error.jsp");

	    //test
	    logoutServlet.doPost(req, resp);

	    //results
	    verify(dispatcher).forward(req, resp);
	}
	
}
