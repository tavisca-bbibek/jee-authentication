package com.tavisca.javatraining.jee.repository;

import java.sql.SQLException;
import java.util.Optional;

import com.tavisca.javatraining.jee.model.User;

public interface UserDao {
	Optional<User> findByUsername(String username) throws SQLException, ClassNotFoundException;
	boolean save(User user) throws ClassNotFoundException, SQLException;
}
