package net.javaguides.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.usermanagement.model.User;

public class UserDAO {

	private String JdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String JdbcUserName = "root";
	private String JdbcPassword = "3306";

	private static final String INSERT_USERS_SQL = "Insert into users" + "(email,name,country)VALUES" + "(?,?,?);";

	private static final String SELECT_USER_BY_ID = "Select id, name, email, country from users where id =?";

	private static final String SELECT_ALL_USERS = "Select * from users";

	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";

	private static final String UPDATE_USERS_SQL = "update users set name=?,email=?,country=? where id =?;";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(JdbcURL, JdbcUserName, JdbcPassword);

		} catch (Exception e) {

			System.out.println(e);
		}

		return connection;

	}

//Insert User demo
	public void insertUser(User user) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();

		} catch (Exception e) {

			System.out.println(e);
		}

	}

//Update 
	public boolean updatetUser(User user) throws SQLException {
		boolean rowUpdate;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());

			rowUpdate = statement.executeUpdate() > 0;

		}
		return rowUpdate;
	}

//Select user by id
	public User selectUser(int id) {
		User user = null;
		try (Connection connection = getConnection();
				PreparedStatement PreparStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

			PreparStatement.setInt(1, id);
			System.out.println(PreparStatement);
			ResultSet rs = PreparStatement.executeQuery();

			while (rs.next()) {

				String name = rs.getString("Name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, name, email, country);

			}
		} catch (Exception e) {

			System.out.println(e);
		}
		return user;
	}

	// Select user

	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement PreparStatement = connection.prepareStatement(SELECT_ALL_USERS)) {

			System.out.println(PreparStatement);
			ResultSet rs = PreparStatement.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("Name"));
				user.setEmail(rs.getString("email"));
				user.setCountry(rs.getString("country"));
				users.add(user);

			}
		} catch (Exception e) {

			System.out.println(e);
		}
		return users;
	}

	// Delete User

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

}
