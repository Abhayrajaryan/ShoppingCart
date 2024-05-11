package com.abhay.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abhay.connection.DBCon;
import com.abhay.dao.UserDao;
import com.abhay.model.UserModel;

/**
 * Servlet implementation class Login
 */
@WebServlet("/user-login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try(PrintWriter out = response.getWriter()){
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			
			UserDao userDao = new UserDao(DBCon.getConnection());
			UserModel user = userDao.userLogin(email, password);
			
			if(user != null) {
				request.getSession().setAttribute("user", user);
				response.sendRedirect("index.jsp");
			}else {
				out.print("LOgin Failed");
			}
		}
	}

}
