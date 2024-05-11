package com.abhay.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abhay.connection.DBCon;
import com.abhay.dao.OrderDao;
import com.abhay.model.Cart;
import com.abhay.model.Order;
import com.abhay.model.UserModel;

/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet("/check-out")
public class CheckOutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			OrderDao od = new OrderDao(DBCon.getConnection());
			
			ArrayList<Cart> cartList = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			UserModel user = (UserModel) request.getSession().getAttribute("user");
			
			if(cartList != null && user != null) {
				for(Cart c : cartList) {
					Order order = new Order();
					order.setId(c.getId());
					order.setUserId(user.getId());
					order.setQuantity(c.getQuantity());
					order.setDate(formatter.format(date));
					
					boolean result = od.insertOrder(order);
					if(!result) break;
				}
				
				cartList.clear();
				response.sendRedirect("orders.jsp");
				
			}else {
				if(user == null) {
					response.sendRedirect("login.jsp");
				}else {
					response.sendRedirect("cart.jsp");
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
