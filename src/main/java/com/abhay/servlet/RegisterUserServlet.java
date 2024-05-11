package com.abhay.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abhay.connection.DBCon;
import com.abhay.dao.UserDao;

/**
 * Servlet implementation class RegisterUserServlet
 */
@WebServlet("/register-user")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			String name = request.getParameter("name");
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			
			UserDao ud = new UserDao(DBCon.getConnection());
			ud.createUser(name, email, password);
			
			response.sendRedirect("login.jsp");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
