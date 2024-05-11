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
 * Servlet implementation class OrderNowServlet
 */
@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			UserModel user = (UserModel)request.getSession().getAttribute("user");
			if(user != null) {
				
				int productId = Integer.parseInt(request.getParameter("id"));
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				
				if(productQuantity <= 0) {
					productQuantity = 1;
				}
				
				Order order = new Order();
				order.setId(productId);
				order.setUserId(user.getId());
				order.setQuantity(productQuantity);
				order.setDate(formatter.format(date));
				
				OrderDao od = new OrderDao(DBCon.getConnection());
				boolean result = od.insertOrder(order);
				
				if(result) {
					ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
					if(cart_list != null) {
						for(Cart c : cart_list) {
							if(c.getId() == productId) {
								cart_list.remove(cart_list.indexOf(c));
								break;
							}
						}
					}
					response.sendRedirect("orders.jsp");
				}else {
					out.println("Order Failed");
				}
				
			}else {
				response.sendRedirect("login.jsp");
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
