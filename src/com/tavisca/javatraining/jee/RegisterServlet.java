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

import com.tavisca.javatraining.jee.model.User;
import com.tavisca.javatraining.jee.repository.UserDao;
import com.tavisca.javatraining.jee.repository.UserDaoDB;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao userDao = new UserDaoDB();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User(username, password);
		Optional<User> maybeUser;
		try {
			maybeUser = userDao.findByUsername(username);
			if(maybeUser.isPresent())
				response.getWriter().append(username + " is already present");
			else 	{
				userDao.save(user);
				response.sendRedirect("login.html");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			response.getWriter().append("BB messed up.");
			response.getWriter().flush();
		}
	}

}
