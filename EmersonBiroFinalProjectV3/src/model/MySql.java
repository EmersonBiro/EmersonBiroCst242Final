package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySql {
	private Connection myConn;
	protected Statement myStmt;

	// this constructor establishes a connection to the database
	public MySql() {
		String url = "jdbc:mysql://localhost:3306/sain_report?useSSL=false";
		String user = "root";
		String password = "drowssap";

		try {
			// try to connect
			myConn = DriverManager.getConnection(url, user, password);
			myStmt = myConn.createStatement();

		} catch (SQLException e) {
			// if cannot connect catch the exception
			e.printStackTrace();
		}

	}

	private String accountId;
	private boolean student;
	private boolean faculty;
	private boolean admin;

	public boolean checkLogin(String username, String password) {
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

	private String studentId;
	private String[] data;

	public String[] getStudentData() {
		String query = "SELECT * FROM sain_report.student;";
		data = new String[5];
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (accountId.equals(rs.getString("account_id"))) {
					studentId = rs.getString("student_id");
					data[0] = rs.getString("student_id");
					data[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					data[2] = rs.getString("gpa");
					data[3] = rs.getString("major");
					data[4] = rs.getString("campus");

					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;

	}

	ArrayList<String> coursesTakenBag;

	public ArrayList<String> coursesTaken() {
		coursesTakenBag = new ArrayList<>();
		String query = "SELECT * FROM sain_report.courses_taken;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (studentId.equals(rs.getString("student_id"))) {
					coursesTakenBag.add(rs.getString("course_name") + rs.getString("course_number") + "\t  "
							+ rs.getString("area_required") + " \t\t" + rs.getString("grade"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coursesTakenBag;
	}

	public ObservableList<String> getReqCoursesTaken() {

		ObservableList<String> req = FXCollections.observableArrayList();

		String query = "SELECT * FROM sain_report.major_requirements;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (data[3].equals(rs.getString("area_required"))) {
					for (int i = 0; i < coursesTakenBag.size(); i++) {

						String a = coursesTakenBag.get(i).split(" ")[0].trim();
						String b = rs.getString("course_name");

						if (a.equals(b) && !coursesTakenBag.get(i).split(" ")[4].trim().equals("F")
								&& !coursesTakenBag.get(i).split(" ")[4].trim().equals("W")) {
							req.add(coursesTakenBag.get(i));
						}
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return req;

	}

	public ObservableList<String> getOtherCoursesTaken() {
		ObservableList<String> oth = FXCollections.observableArrayList();

		for (int i = 0; i < coursesTakenBag.size(); i++) {
			String a = coursesTakenBag.get(i).split(" ")[2].trim() + " " + coursesTakenBag.get(i).split(" ")[3].trim();

			if (!a.equals(data[3]) && !coursesTakenBag.get(i).split(" ")[4].trim().equals("F")
					&& !coursesTakenBag.get(i).split(" ")[4].trim().equals("W")) {
				oth.add(coursesTakenBag.get(i));
			}
		}

		return oth;
	}

	public ObservableList<String> getFailedWithdrawn() {

		ObservableList<String> fow = FXCollections.observableArrayList();

		for (int i = 0; i < coursesTakenBag.size(); i++) {

			if (coursesTakenBag.get(i).split(" ")[4].trim().equals("F")) {
				fow.add(coursesTakenBag.get(i));
			}
		}

		return fow;

	}

}
