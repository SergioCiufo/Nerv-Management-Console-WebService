package com.company.NervManagementConsoleREST.web.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.utils.Costants;

public class LoginFilterTest {
	private LoginFilter loginFilter = new LoginFilter();
	
	
	@Test
    public void shouldPassFilter_whenUserIsPresentInSession() throws Exception {
        //parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);

        //mocks
        doReturn(session).when(request).getSession();
        doReturn(new User()).when(session).getAttribute(Costants.KEY_SESSION_USER); 

        //test
        loginFilter.doFilter(request, response, chain);

        //result
        verify(chain).doFilter(request, response);
        verify(request, never()).getRequestDispatcher(anyString());
    }
	
	@Test
    public void shouldRedirectToLogin_whenUserIsNotInSession() throws Exception {
		//parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        //mocks
        doReturn(session).when(request).getSession();
        doReturn(null).when(session).getAttribute(Costants.KEY_SESSION_USER);
        doReturn(dispatcher).when(request).getRequestDispatcher("/jsp/public/Login.jsp");

        //test
        loginFilter.doFilter(request, response, chain);

        //result
        verify(dispatcher).forward(request, response);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    public void shouldRedirectToLogin_whenExceptionOccurs() throws Exception {
    	//parameters
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        RuntimeException runtimeException = new RuntimeException("test", null);
        
        //mocks
        doReturn(dispatcher).when(request).getRequestDispatcher("/jsp/public/Login.jsp");
        doThrow(runtimeException).when(chain).doFilter(request, response);

        //test
        loginFilter.doFilter(request, response, chain);

        //result
        verify(dispatcher).forward(request, response);
    }
}
