package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySql {
	private Connection myConn;
	private Statement myStmt;

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
	// end of constructor

	// this method checks to see if the account exist and what level it is
	private String logedInLevel;
	private String accountId;
	private String studentId;

	public boolean checkLogin(String username, String password) {
		// get the accounts table to be able to check
		String query = "SELECT * FROM sain_report.accounts;";

		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {

					logedInLevel = rs.getString("level");
					accountId = rs.getString("account_id");
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getLoggedInLevel() {
		return logedInLevel;
	}
	// end of checkLogin method

	// these methods create bags for certain people based on account id
	private String[] stuInfoBag;

	public void getStudentInfo() {
		stuInfoBag = new String[6];
		String query = "SELECT * FROM sain_report.student;";

		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("account_id").equals(accountId)
						|| rs.getString("student_id").equals(studentSearchedId)) {
					stuInfoBag[0] = rs.getString("student_id");
					stuInfoBag[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					stuInfoBag[2] = rs.getString("gpa");
					stuInfoBag[3] = rs.getString("major");
					stuInfoBag[4] = rs.getString("campus");
					stuInfoBag[5] = rs.getString("credits_taken");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		studentId = stuInfoBag[0];
	}

	private String[] facInfoBag;

	public void getFacultyInfo() {
		facInfoBag = new String[3];
		String query = "SELECT * FROM sain_report.faculty;";

		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				facInfoBag[0] = rs.getString("faculty_id");
				facInfoBag[1] = rs.getString("first_name") + " " + rs.getString("last_name");
				facInfoBag[2] = rs.getString("rank");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String[] admInfoBag;

	public void getAdminInfo() {
		admInfoBag = new String[3];
		String query = "SELECT * FROM sain_report.faculty;";

		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				admInfoBag[0] = rs.getString("admin_id");
				admInfoBag[1] = rs.getString("first_name") + " " + rs.getString("last_name");
				admInfoBag[2] = rs.getString("rank");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// end of these methods

	// this method searches for a student based on id and returns true or false
	// if it is true
	private String studentSearchedId;

	public boolean searchStudent(String id) {
		String query = "SELECT * FROM sain_report.student;";
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("student_id").equals(id)) {
					studentSearchedId = rs.getString("student_id");
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	// end of search method

	/////////////// declare some variables and set table
	///////////////////////////////////////////////////

	private double totalCredits = 0;
	private double qualityPoints = 0;
	private String calcGpa = "4.0";
	private String majorId;
	private ArrayList<String> coursesTakenBag;
	private ArrayList<String> coursesTakingBag;
	private ArrayList<String> majorRequirementBag;
	private ArrayList<String> majorBag;

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
					totalCredits += Double.valueOf(rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void coursesTaking() {
		coursesTakingBag = new ArrayList<>();
		String query = "SELECT * FROM sain_report.courses_taking;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (studentId.equals(rs.getString("student_id"))) {
					coursesTakingBag.add(rs.getString("course_name") + rs.getString("course_number") + " "
							+ rs.getString("course_credit_name") + " " + rs.getString("grade") + " "
							+ rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// this populates the an arraylist of all the majors available
	public void getMajor() {
		String query = "SELECT * FROM sain_report.major;";
		majorBag = new ArrayList<>();
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				majorBag.add(rs.getString("major_name") + " " + rs.getString("major_id") + " "
						+ rs.getString("credits_required") + " " + rs.getString("min_gpa"));
				if (rs.getString("major_name").equals(stuInfoBag[3])) {
					majorId = rs.getString("major_id");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// this populates an arraylist with all the major requirements based on the
	// major selected
	public void majorRequirements() {
		majorRequirementBag = new ArrayList<String>();
		String query = "SELECT * FROM sain_report.major_requirements;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("major_id").equals(majorId)) {
					majorRequirementBag.add(rs.getString("area_type") + " " + rs.getString("course_credit_name") + " "
							+ rs.getString("course_credits") + " " + rs.getString("course_area"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// this is used by the what-if analysis to change the major temporary
	public boolean setMajor(String major) {
		if (major != null) {
			majorId = major;
			majorRequirements();
			coursesTaken();
			return true;
		} else {
			return false;
		}
	}
	////////////////////////////////////////////////////////////////
	/////////////////// end of population//////////////////

	////////////////// observable list for the tables/////////////////////////
	////////////////////////////////////////////////////////////////

	// this method returns an observable list of courses taking
	public ObservableList<String> getCoursesTaking() {
		ObservableList<String> tak = FXCollections.observableArrayList();
		tak.add("COURSE		GRADE	CREDITS");

		String query = "SELECT * FROM sain_report.courses_taking;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("student_id").equals(studentId)) {
					tak.add(rs.getString("course_name") + rs.getString("course_number") + "		"
							+ rs.getString("grade") + "	 	" + rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tak;
	}

	// this method returns an observable list of courses taken
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

	// this method returns an observable list of other courses taken
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

	// this method returns an observable list of failed or withdrawn classes
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

	// this method returns an observable list of courses needed
	public ObservableList<String> getCoursesNeeded() {
		ObservableList<String> gcn = FXCollections.observableArrayList();
		gcn.add("COURSE\t\t\t\t\tCREDITS");

		for (int i = 0; i < majorRequirementBag.size(); i++) {

			double crsCredit = Double.valueOf(majorRequirementBag.get(i).split(" ")[3]);
			for (int j = 0; j < coursesTakenBag.size(); j++) {
				if (coursesTakenBag.get(j).split(" ")[1].equals(majorRequirementBag.get(i).split(" ")[2])) {
					if (!coursesTakenBag.get(j).split(" ")[2].equals("F")
							&& !coursesTakenBag.get(j).split(" ")[2].equals("W")) {
						crsCredit -= Double.valueOf(coursesTakenBag.get(j).split(" ")[3]);
					}
				}

			}
			
			for (int j = 0; j < coursesTakingBag.size(); j++) {
				if(coursesTakingBag.get(j).split(" ")[1].equals(majorRequirementBag.get(i).split(" ")[2])) {
					crsCredit -= Double.valueOf(coursesTakingBag.get(j).split(" ")[3]);
				}
			}
			
			String temp = majorRequirementBag.get(i).split(" ")[4] + "\t\t\t" + Double.valueOf(crsCredit);

			if (Double.valueOf(crsCredit) > 0) {
				gcn.add(temp);
			}

		}

		return gcn;

	}

	// this method returns an observable list of your summary
	public ObservableList<String> getSummary() {
		ObservableList<String> sum = FXCollections.observableArrayList();
		sum.add("Minimum GPA:\t\t2.0");
		for (int i = 0; i < majorBag.size(); i++) {

			if (majorId.equals(majorBag.get(i).split(" ")[2])) {
				sum.add("Credits Required:\t\t" + majorBag.get(i).split(" ")[3]);
			}
		}
		sum.add("Credits Taken:\t\t\t" + stuInfoBag[5]);
		return sum;

	}

	//////////////// end of observable list for the tables
	////////////////////////////////////////////////////////////////

	// a refresher method for some data when you change major or search new
	// student
	public void getAccountData() {
		getMajor();
		majorRequirements();
		coursesTaken();
		coursesTaking();
		calculateGpa();

	}

	// this method actually takes all your grades that count and calculate your
	// gpa
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
		double temp;
		try {
			temp = (qualityPoints / totalCredits);
			calcGpa = new DecimalFormat("#.##").format(temp);
			updateStudentGpa();
			updateCreditsTaken();
		} catch (Exception e) {
			calcGpa = "4.0";
		}

	}

	// this is a prepared statement that updates the students gpa in the
	// database
	public void updateStudentGpa() {
		String query = "update student set gpa = ? where student_id = ?";
		try {
			PreparedStatement preparedStmt = myConn.prepareStatement(query);
			preparedStmt.setString(1, calcGpa);
			preparedStmt.setString(2, studentId);

			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// this is a prepared statement that updates the students credits taken in
	// the
	// database
	public void updateCreditsTaken() {
		String query = "update student set credits_taken = ? where student_id = ?";
		try {
			PreparedStatement preparedStmt = myConn.prepareStatement(query);
			preparedStmt.setString(1, Double.toString(totalCredits));
			preparedStmt.setString(2, studentId);

			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// getter methods
	public String[] getStuInfoBag() {
		return stuInfoBag;
	}

	public String[] getFacInfoBag() {
		return facInfoBag;
	}

	public String[] getAdmInfoBag() {
		return admInfoBag;
	}

	public ArrayList<String> getMajorBag() {
		return majorBag;
	}

	public String getCalcGpa() {
		return calcGpa;
	}
	// end of getter methods
}
