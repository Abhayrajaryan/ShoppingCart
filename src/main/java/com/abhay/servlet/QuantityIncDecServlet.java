package com.abhay.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abhay.model.Cart;

@WebServlet("/quantity-inc-dec")
public class QuantityIncDecServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;characterset=UTF-8");
		
		try(PrintWriter out = response.getWriter();){
			String action = request.getParameter("action");
			int id = Integer.parseInt(request.getParameter("id"));
			
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			if(action != null && id>=1) {
				for(Cart c: cart_list) {
					if(c.getId() == id) {
						if(action.equals("inc")) {
							c.setQuantity(c.getQuantity()+1);
							response.sendRedirect("cart.jsp");
						}else {
							if(c.getQuantity()>1) {
								c.setQuantity(c.getQuantity()-1);
								response.sendRedirect("cart.jsp");
							}else {
								response.sendRedirect("remove-from-cart?id="+c.getId());
							}
						}
					}
				}
			}
		}
		
	}

}
