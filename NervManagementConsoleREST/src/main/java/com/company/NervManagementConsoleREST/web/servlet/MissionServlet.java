package com.company.NervManagementConsoleREST.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.MissionSendOrCompleteService;
import com.company.NervManagementConsoleREST.utils.Costants;

@WebServlet("/mission")
public class MissionServlet extends HttpServlet {
	
	private final MissionSendOrCompleteService missionSendOrCompleteService = new MissionSendOrCompleteService();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {		
			User user=(User)req.getSession().getAttribute(Costants.KEY_SESSION_USER);
			String idMissionString = req.getParameter("missionId");
			
			String [] selectedIdMembers = req.getParameterValues("memberSelect");
			if(selectedIdMembers != null) {
				ArrayList<String> selectedIdMembersListString = new ArrayList<>(Arrays.asList(selectedIdMembers));
				user=missionSendOrCompleteService.sendMembersMission(user, idMissionString, selectedIdMembersListString);
			}
			req.getSession().setAttribute(Costants.KEY_SESSION_USER, user);
			resp.sendRedirect(req.getContextPath() + "/jsp/private/Home.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/jsp/public/Error.jsp").forward(req, resp);
		}
	}
}