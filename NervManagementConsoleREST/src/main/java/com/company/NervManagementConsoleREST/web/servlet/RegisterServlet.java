package com.company.NervManagementConsoleREST.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private final RegisterService registerService = new RegisterService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
		
			String name = req.getParameter("registerName");
			String surname = req.getParameter("registerSurname");
			String username = req.getParameter("registerUsername");
			String password = req.getParameter("registerPassword");
			User newUser = new User(name, surname, username, password);
			registerService.register(newUser);
			req.getRequestDispatcher("/jsp/public/Login.jsp").forward(req, resp);
		} catch (Exception e) { // si potrebbe gestire l'eccezione "username già esistente blabla" 
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}
