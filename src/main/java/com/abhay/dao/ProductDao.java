package com.abhay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abhay.model.Cart;
import com.abhay.model.Product;

public class ProductDao {

	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public ProductDao(Connection con) {
		this.con = con;
	}

	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<Product>();

		try {
			query = "select * from products";
			pst = this.con.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				Product p = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("category"),
						rs.getDouble("price"), rs.getString("image"));
				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return list;
	}

	public Product getSingleProduct(int id) {
		Product p = null;

		try {
			query = "select * from products where id=?;";
			pst = this.con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				p = new Product(); 
				p.setId(rs.getInt("id"));
				p.setCategory(rs.getString("category"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				p.setImage(rs.getString("image"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
		List<Cart> products = new ArrayList<>();

		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					query = "Select * from products where id = ?";
					pst = this.con.prepareStatement(query);
					pst.setInt(1, item.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						Cart row = new Cart();
						row.setId(rs.getInt("id"));
						row.setName(rs.getString("name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getDouble("price"));
						row.setQuantity(item.getQuantity());
						products.add(row);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	public double getTotalCartPrice(List<Cart> cartList) {
		double sum = 0;

		try {
			for (Cart c : cartList) {
				sum += c.getPrice() * c.getQuantity();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sum;
	}

}
