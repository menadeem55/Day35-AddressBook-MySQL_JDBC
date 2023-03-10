package com.bridgelabz.jdbc1.Day35_Addressbook_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {
	Connection connection;

	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Drivers loaded!!");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?", "root", "rootroot");
			System.out.println("connection Established!!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public List<Contacts> retrieveData() {
		ResultSet resultSet = null;
		List<Contacts> addressBookList = new ArrayList<Contacts>();
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from addressbook_table;");
			int count = 0;
			while (resultSet.next()) {
				Contacts contactInfo = new Contacts();
				contactInfo.setFirstName(resultSet.getString("firstName"));
				contactInfo.setLastName(resultSet.getString("lastname"));
				contactInfo.setAddress(resultSet.getString("address"));
				contactInfo.setCity(resultSet.getString("city"));
				contactInfo.setState(resultSet.getString("state"));
				contactInfo.setZip(resultSet.getInt("zip"));
				contactInfo.setPhoneNumber(resultSet.getString("PhoneNumber"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setDateAdded(resultSet.getDate("dateadded").toLocalDate());
				contactInfo.setBookName(resultSet.getString("bookNamed"));

				addressBookList.add(contactInfo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return addressBookList;

	}

	public void updateCityByZip(String address, String city, String state, int zip) {
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			String query = "Update addressbook_table set address=" + "'" + address + "'" + ", " + "city=" + "'" + city
					+ "'" + ", " + "state=" + "'" + state + "'" + ", " + "zip=" + zip + "";
			int result = statement.executeUpdate(query);
			System.out.println(result);
			if (result > 0) {
				System.out.println("Address Updated Successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
