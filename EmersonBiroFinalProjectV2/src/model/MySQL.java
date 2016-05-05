package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQL {
	private Connection myConn = null;
	protected Statement myStmt = null;

	// this constructor establishes a connection to the database
	public MySQL() {
		String url = "jdbc:mysql://localhost:3306/sain_report";
		String user = "student";
		String password = "student";

		try {
			// try to connect
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();

		} catch (SQLException e) {
			// if cannot connect catch the exception
			e.printStackTrace();
		}

	}

	private CheckLogin cl;

	public boolean CheckLogin(String username, String password) {
		// this will go to the checklogin class to see if the user exist
		cl = new CheckLogin(myStmt, username, password);

		return cl.checkLogin();

	}

	public CheckLogin getCl() {
		return cl;
	}

	private String studentId;

	public String[] getStudentData() {
		String query = "SELECT * FROM sain_report.student;";
		String[] data = new String[5];
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (cl.getAccountId().equals(rs.getString("account_id"))) {
					studentId = rs.getString("student_id");
					data[0] = rs.getString("student_id");
					data[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					data[2] = rs.getString("gpa");
					data[3] = rs.getString("major");
					data[4] = rs.getString("campus");

					reqCoursesTaken();

					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;

	}

	public ObservableList<String> reqCoursesTaken() {
		ObservableList<String> items = (ObservableList) FXCollections.observableArrayList();
		String query = "SELECT * FROM sain_report.courses_taken;";
		ResultSet rs;
		
		try {
			rs = myStmt.executeQuery(query);

			

			while (rs.next()) {
				if (studentId.equals(rs.getString("student_id"))) {
					items.add(rs.getString("course_name") + "" + rs.getString("course_number") + "\t\t" + rs.getString("area_required") +
							"\t  " + rs.getString("grade"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}

}
