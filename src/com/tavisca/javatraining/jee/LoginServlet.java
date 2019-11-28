package com.tavisca.javatraining.jee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tavisca.javatraining.jee.model.User;
import com.tavisca.javatraining.jee.repository.UserDao;
import com.tavisca.javatraining.jee.repository.UserDaoDB;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	@Override
	public void init() throws ServletException {
		super.init();
		getServletContext().setAttribute("userDao", new UserDaoDB());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao userDao = (UserDao) getServletContext().getAttribute("userDao");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Optional<User> maybeUser;
		try {
			maybeUser = userDao.findByUsername(username);
			HttpSession session = request.getSession();
			if(maybeUser.isPresent() &&
					maybeUser.get().getPassword().equals(password)) {
				session.setAttribute("user", maybeUser.get());
				session.removeAttribute("loginAttempts");
				response.sendRedirect( "home");
			}else {
				response.getWriter().append("Dude! please don't hack my app.\n"
						+ "I can remember you\nWarning: ");
				Integer attempts = (Integer) session.getAttribute("loginAttempts");
				if(attempts == null)
					attempts = 1;
				else if(attempts > 2) {
					response.sendRedirect("register.html");
					return;
				}
				else
					attempts += 1;
				session.setAttribute("loginAttempts", attempts);
				response.getWriter().append(String.valueOf(attempts));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.getWriter().append("BB messed up.");
			response.getWriter().flush();
		}
	}

}
