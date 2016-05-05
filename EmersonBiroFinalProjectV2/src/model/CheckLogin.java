package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckLogin {

	private Statement myStmt;
	private String username;
	private String password;

	public CheckLogin(Statement myStmt, String username, String password) {
		this.myStmt = myStmt;
		this.username = username;
		this.password = password;
	}

	private boolean student;
	private boolean faculty;
	private boolean admin;
	private String accountId;
	
	public boolean checkLogin() {
		// get the accounts table to be able to check
		String query = "SELECT * FROM sain_report.accounts;";

		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
					// if you logged in this switch statement test what level
					// you are
					accountId = rs.getString("account_id");
					switch (rs.getString("level")) {
					case "1":
						student = true;
						break;
					case "2":
						faculty = true;
						break;
					case "3":
						admin = true;
						break;
					}

					return true;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}
	
	public String getAccountId() {
		return accountId;
	}

	public boolean isStudent() {
		return student;
	}

	public boolean isFaculty() {
		return faculty;
	}

	public boolean isAdmin() {
		return admin;
	}

}
