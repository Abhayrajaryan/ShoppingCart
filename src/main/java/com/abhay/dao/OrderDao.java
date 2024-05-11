package com.abhay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abhay.model.Order;
import com.abhay.model.Product;

public class OrderDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public OrderDao(Connection con) {
		this.con = con;
	}

	public boolean insertOrder(Order order) {
		boolean result = false;

		try {
			query = "INSERT INTO orders (p_id, u_id, o_quantity, o_date) VALUES (?, ?, ?, ?)";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, order.getId());
			pst.setInt(2, order.getUserId());
			pst.setInt(3, order.getQuantity());
			pst.setString(4, order.getDate());
			pst.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Order> userOrders(int id) {
		List<Order> list = new ArrayList<>();

		try {
			query = "select * from orders where u_id=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);

			rs = pst.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				ProductDao pd = new ProductDao(this.con);
				Product p = pd.getSingleProduct(rs.getInt("p_id"));
				
				order.setOrderId(rs.getInt("o_id"));
				order.setId(rs.getInt("p_id"));
				order.setName(p.getName());
				order.setCategory(p.getCategory());
				order.setPrice(p.getPrice()*rs.getInt("o_quantity"));
				order.setQuantity(rs.getInt("o_quantity"));
				order.setDate(rs.getString("o_date"));
				
				list.add(order);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void cancleOrder(int o_id) {
		try {
			query = "Delete from orders where o_id=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, o_id);
			
			pst.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
