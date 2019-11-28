package com.tavisca.javatraining.jee.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.tavisca.javatraining.jee.db.DBUtil;
import com.tavisca.javatraining.jee.model.User;

public class UserDaoDB implements UserDao{
	
	public Optional<User> findByUsername(String username) throws SQLException, ClassNotFoundException{
		try(Connection conn = DBUtil.getConnection()){
			String usernameQuery = "SELECT username, password FROM users WHERE username=?";
			PreparedStatement statement = conn.prepareStatement(usernameQuery);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
				return Optional.of(new User(rs.getString("username"), rs.getString("password")));
		}
		return Optional.empty();
	}
	
	public boolean save(User user) throws ClassNotFoundException, SQLException {
		try(Connection conn = DBUtil.getConnection()){
			String usernameQuery = "INSERT INTO users(username, password) value(?,?)";
			PreparedStatement statement = conn.prepareStatement(usernameQuery);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			int rowsAffacted = statement.executeUpdate();
			if(rowsAffacted == 1)
				return true;
		}
		return false;
	}
}
