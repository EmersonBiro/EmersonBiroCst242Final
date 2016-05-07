package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
						getAccountData();
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

	public void getAccountData() {
		setStudentData();
		getMajor();
		majorRequirements();
		coursesTaken();
		calculateGpa();

	}

	private int totalCredits;
	private double qualityPoints;
	private String calcGpa;
	private String studentId;
	private String majorId;
	private String[] personData;
	private ArrayList<String> coursesTakenBag;
	private ArrayList<String> majorRequirementBag;
	private ArrayList<String> majorBag;

	public void calculateGpa() {
		qualityPoints = 0;
		// grade is at [3]
		for (int i = 0; i < coursesTakenBag.size(); i++) {
			String[] grade = coursesTakenBag.get(i).split(" ");
			switch (grade[2]) {
			case "A":
				qualityPoints += (Double.valueOf(grade[3]) * 4);
				break;
			case "B+":
				qualityPoints += (Double.valueOf(grade[3]) * 3.5);
				break;
			case "B":
				qualityPoints += (Double.valueOf(grade[3]) * 3);
				break;
			case "C+":
				qualityPoints += (Double.valueOf(grade[3]) * 2.5);
				break;
			case "C":
				qualityPoints += (Double.valueOf(grade[3]) * 2);
				break;
			case "D+":
				qualityPoints += (Double.valueOf(grade[3]) * 1.5);
				break;
			case "D":
				qualityPoints += (Double.valueOf(grade[3]) * 1);
				break;
			case "F":
				qualityPoints += (Double.valueOf(grade[3])) * 0;
				break;
			default:
			}

		}
		double temp = (qualityPoints / totalCredits);
		calcGpa = new DecimalFormat("#.##").format(temp);
	}

	public void setStudentData() {
		String query = "SELECT * FROM sain_report.student;";
		personData = new String[5];
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (accountId.equals(rs.getString("account_id"))) {
					studentId = rs.getString("student_id");
					personData[0] = rs.getString("student_id");
					personData[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					personData[2] = rs.getString("gpa");
					personData[3] = rs.getString("major");
					personData[4] = rs.getString("campus");

					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ObservableList<String> coursesTaking() {
		ObservableList<String> tak = FXCollections.observableArrayList();
		tak.add("COURSE		GRADE	CREDITS");

		String query = "SELECT * FROM sain_report.courses_taking;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("student_id").equals(studentId)) {
					tak.add(rs.getString("course_name") + rs.getString("course_number") + "		"
							+ rs.getString("grade") + "	 	" + rs.getString("credits"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tak;
	}

	public void coursesTaken() {
		totalCredits = 0;
		coursesTakenBag = new ArrayList<>();
		String query = "SELECT * FROM sain_report.courses_taken;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (studentId.equals(rs.getString("student_id"))) {
					coursesTakenBag.add(rs.getString("course_name") + rs.getString("course_number") + " "
							+ rs.getString("course_credit_name") + " " + rs.getString("grade") + " "
							+ rs.getString("course_credit"));
					totalCredits += Integer.valueOf(rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean setMajor(String major) {
		if (major != null) {
			majorId = major;
			setStudentData();
			majorRequirements();
			coursesTaken();
			return true;
		} else {
			return false;
		}
	}

	public void getMajor() {
		String query = "SELECT * FROM sain_report.major;";
		majorBag = new ArrayList<>();
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				majorBag.add(rs.getString("major_name") + " " + rs.getString("major_id") + " "
						+ rs.getString("credits_required") + " " + rs.getString("min_gpa"));
				if (rs.getString("major_name").equals(personData[3])) {
					majorId = rs.getString("major_id");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void majorRequirements() {
		majorRequirementBag = new ArrayList<String>();
		String query = "SELECT * FROM sain_report.major_requirements;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("major_id").equals(majorId)) {
					majorRequirementBag.add(rs.getString("area_type") + " " + rs.getString("course_credit_name"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ObservableList<String> getReqCoursesTaken() {

		ObservableList<String> req = FXCollections.observableArrayList();
		req.add("COURSE		GRADE	CREDITS");
		for (int i = 0; i < coursesTakenBag.size(); i++) {
			for (int j = 0; j < majorRequirementBag.size(); j++) {
				String a = coursesTakenBag.get(i).split(" ")[1];
				String b = majorRequirementBag.get(j).split(" ")[2];
				String[] s = coursesTakenBag.get(i).split(" ");
				if (a.equals(b) && !s[2].equals("F") && !s[2].equals("W")) {
					req.add(s[0] + "		" + s[2] + "		" + s[3]);
				}
			}
		}
		return req;

	}

	public ObservableList<String> getOtherCoursesTaken() {
		ObservableList<String> oth = FXCollections.observableArrayList();
		oth.add("COURSE		GRADE	CREDITS");

		for (int i = 0; i < coursesTakenBag.size(); i++) {
			boolean test = true;
			for (int j = 0; j < majorRequirementBag.size(); j++) {
				String a = coursesTakenBag.get(i).split(" ")[1];
				String b = majorRequirementBag.get(j).split(" ")[2];

				if (a.equals(b)) {
					test = false;
				}
			}
			String[] s = coursesTakenBag.get(i).split(" ");
			if (test && !s[2].equals("F") && !s[2].equals("W")) {
				oth.add(s[0] + "		" + s[2] + "		" + s[3]);
			}
		}

		return oth;
	}

	public ObservableList<String> getFailedWithdrawn() {
		ObservableList<String> fow = FXCollections.observableArrayList();
		fow.add("COURSE		GRADE	CREDITS");

		for (int i = 0; i < coursesTakenBag.size(); i++) {
			String[] s = coursesTakenBag.get(i).split(" ");
			if (s[2].trim().equals("F") && !s[2].equals("W")) {
				fow.add(s[0] + "		" + s[2] + "		" + s[3]);
			}
		}

		return fow;

	}

	public String[] getPersonData() {
		return personData;
	}

	public ArrayList<String> getMajorBag() {
		return majorBag;
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

	public String getCalcGpa() {
		return calcGpa;
	}

}
