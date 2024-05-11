package com.abhay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.abhay.model.UserModel;

public class UserDao {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public UserDao(Connection con) {
		this.con = con;
	}
	
	public UserModel userLogin(String email, String password) {
		UserModel user = null;
		
		try {
			query = "select * from users where email = ? AND password = ?";
			pst = this.con.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, password);
			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
		
		return user;
	}
	
	public void createUser(String name, String email, String password) {
		UserModel user = null;
		
		try {
			query = "insert into users (name, email, password) values (?, ?, ?);";
			pst = this.con.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, email);
			pst.setString(3, password);
			
			pst.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
