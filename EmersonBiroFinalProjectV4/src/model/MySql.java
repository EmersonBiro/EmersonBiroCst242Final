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

/**
 * 
 * This class is where all of the calculations and manipulation of data occurs,
 * where data comes from the controller and is used to be displayed on the view
 *
 */
public class MySql {
	private Connection myConn;
	private Statement myStmt;

	/**
	 * This constructor connects to the database of mysql to have access to all
	 * the data to be used in the program
	 */
	public MySql() {

		String url = "jdbc:mysql://173.68.191.113:3306/sain_report?useSSL=false";
		String user = "root";
		String password = "rdt123";

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

	/**
	 * 
	 * @precondition the username and password equals something in the database
	 * 
	 * @postcondition connects to the database based on what account information
	 *                was entered, if it was a student, faculty, or an admin
	 * 
	 * @param username
	 *            username of the user
	 * @param password
	 *            password of ther user
	 * @return Returns true or false based of if the login was good or bad
	 */
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

	/**
	 * @precondition looks for the student logged in or searched in the database
	 * 
	 * @postcondition creates an array that holds some basic information of the
	 *                student of focus
	 */
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

	/**
	 * @precondition looks for the faculty logged in, on the database
	 * 
	 * @postcondition creates an array that holds some basic information of the
	 *                faculty of focus
	 */
	public void getFacultyInfo() {
		facInfoBag = new String[3];
		String query = "SELECT * FROM sain_report.faculty;";

		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("account_id").equals(accountId)) {
					facInfoBag[0] = rs.getString("faculty_id");
					facInfoBag[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					facInfoBag[2] = rs.getString("rank");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String[] admInfoBag;

	/**
	 * @precondition looks for the faculty logged in, on the database
	 * 
	 * @postcondition creates an array that holds some basic information of the
	 *                faculty of focus
	 * 
	 */
	public void getAdminInfo() {
		admInfoBag = new String[3];
		String query = "SELECT * FROM sain_report.admin;";

		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("account_id").equals(accountId)) {
					admInfoBag[0] = rs.getString("admin_id");
					admInfoBag[1] = rs.getString("first_name") + " " + rs.getString("last_name");
					admInfoBag[2] = rs.getString("rank");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// end of these methods

	// this method searches for a student based on id and returns true or false
	// if it is true
	private String studentSearchedId;

	/**
	 * @precondition compares student id searched for with the one in the
	 *               database
	 * 
	 * @postcondition searches for a student to see if it exist or not
	 * 
	 * @param id
	 *            the id to search for
	 * @return returns true if the student was found, or false if it was not
	 */
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
	private int coursesTakingCresdits;
	private ArrayList<String> coursesTakenBag;
	private ArrayList<String> coursesTakingBag;
	private ArrayList<String> majorRequirementBag;
	private ArrayList<String> majorBag;
	private ObservableList<String> ctkn;
	private ObservableList<String> courses;
	private ArrayList<String> coursesBag;

	/**
	 * @postcondition stores all the courses that could be taken into and
	 *                arraylist and an observablelist to be used for other
	 *                methods
	 */
	public void courses() {
		courses = FXCollections.observableArrayList();
		coursesBag = new ArrayList<>();
		String query = "SELECT * FROM sain_report.courses;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				coursesBag.add(rs.getString("area_required") + " " + rs.getString("course_name")
						+ rs.getString("course_number") + "		" + "IP" + "		" + rs.getString("course_credit"));
				courses.add(rs.getString("course_name") + rs.getString("course_number") + "		" + "IP" + "		"
						+ rs.getString("course_credit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @precondition compares the student id selected with the one in the
	 *               database
	 * 
	 * @postcondition stores all the courses taken into an arraylist and an
	 *                observarablelist, adds the total amount of credits that
	 *                was taken to be used for gpa calcualtions.
	 * 
	 */
	public void coursesTaken() {
		ctkn = FXCollections.observableArrayList();
		ctkn.add("COURSES TAKEN\nCOURSE		GRADE	CREDITS");
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
					ctkn.add(rs.getString("course_name") + rs.getString("course_number") + "		"
							+ rs.getString("grade") + "		" + rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @precondition compares the student id selected with the one in the
	 *               database
	 * 
	 * @postcondition stores all the courses taken into an arraylist, adds the
	 *                total amount of credits that are being taken.
	 * 
	 */
	public void coursesTaking() {
		coursesTakingCresdits = 0;
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
					coursesTakingCresdits += Integer.valueOf(rs.getString("course_credit"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @postcondition stores all the majors that can be taken into an arraylist
	 *                for use later
	 * 
	 */
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

	/**
	 * @precondition compares the major id selected with the one in the database
	 * 
	 * @postcondition stores all the major requirments into an arraylist, adds
	 *                the total amount of credits that was taken,
	 * 
	 */
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

	/**
	 * 
	 * @precondition compares if the major received is not equal to null
	 * 
	 * @postcondition sets a new major for the what-if analysis
	 * 
	 * @param major
	 *            the major to change to
	 * @return return true if major was set, false if not
	 */
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
	private ObservableList<String> tak;

	/**
	 * 
	 * @precondition compares if the student id equals the one selected
	 * 
	 * @postcondition creates an observablelist of all the courses taken and
	 *                inserts them
	 * 
	 * 
	 * @return returns the the obseravblelist
	 */
	public ObservableList<String> getCoursesTaking() {
		tak = FXCollections.observableArrayList();
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

	/**
	 * 
	 * @postcondition creates an observablelist of all the courses taken and
	 *                inserts them
	 * 
	 * @return returns the observablelist
	 */
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
	/**
	 * @postcondition creates an observablelist of all the other courses taken
	 *                and inserts them
	 * 
	 * 
	 * 
	 * @return returns the observablelist
	 */
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

	/**
	 * 
	 * @precondition compares if the course grade equals W or F
	 * 
	 * @postcondition creates an observablelist of all the withdrawn and failed
	 *                courses taken and inserts them
	 * 
	 * @return returns the observablelist
	 */
	public ObservableList<String> getFailedWithdrawn() {
		ObservableList<String> fow = FXCollections.observableArrayList();
		fow.add("COURSE		GRADE	CREDITS");

		for (int i = 0; i < coursesTakenBag.size(); i++) {
			String[] s = coursesTakenBag.get(i).split(" ");
			if (s[2].trim().equals("F") || s[2].trim().equals("W")) {
				fow.add(s[0] + "		" + s[2] + "		" + s[3]);
			}
		}

		return fow;

	}

	// this method returns an observable list of courses needed
	/**
	 * 
	 * @precondition compares all the courses that have been taken and taking
	 * 
	 * @postcondition Calculates all the credits and courses that need to be
	 *                taken
	 * 
	 * @return returns the observablelist
	 */
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
				if (coursesTakingBag.get(j).split(" ")[1].equals(majorRequirementBag.get(i).split(" ")[2])) {
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

	/**
	 * 
	 * @precondition compares the major id to the one selected
	 * 
	 * @postcondition generates a summary based on the courses that have been
	 *                taking and major taking, storing them in an observablelist
	 * 
	 * @return returns the observablelist
	 */
	public ObservableList<String> getSummary() {
		ObservableList<String> sum = FXCollections.observableArrayList();
		sum.add("Minimum GPA:\t\t2.0");
		for (int i = 0; i < majorBag.size(); i++) {

			if (majorId.equals(majorBag.get(i).split(" ")[2])) {
				sum.add("Credits Required:\t\t" + majorBag.get(i).split(" ")[3]);
			}
		}
		sum.add("Credits Taken:\t\t\t" + stuInfoBag[5]);
		sum.add("Current Credits Taking \t " + coursesTakingCresdits);
		return sum;

	}

	//////////////// end of observable list for the tables
	////////////////////////////////////////////////////////////////

	/**
	 * @postcondition refreshes all the tables, show it shows the most recent
	 *                data
	 * 
	 */
	public void getAccountData() {
		getMajor();
		majorRequirements();
		coursesTaken();
		coursesTaking();
		courses();
		calculateGpa();

	}

	// this method actually takes all your grades that count and calculate your
	// gpa
	/**
	 * 
	 * @precondition compares the courses current grade in a switch statement
	 * 
	 * @postcondition calculates the gpa of all the courses that the student has
	 * 
	 * 
	 */
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

	/**
	 * @postcondition a prepared statement that updates the students gpa after
	 *                the calulation
	 */
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
	/**
	 * 
	 * @postcondition a prepared statement that updates the students credits
	 *                taken after adding them
	 */
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

	/**
	 * @postcondition a prepared statement that updates the grade of the
	 *                selected course for the admin
	 * 
	 * @param course
	 *            course want to upgrade
	 * @param newGrade
	 *            grade to update course
	 */
	public void updateSelectedGrade(String course, String newGrade) {
		String query = "update courses_taken set grade = ? where student_id = ? and course_name = ? and course_number = ?";
		try {
			PreparedStatement preparedStmt = myConn.prepareStatement(query);
			preparedStmt.setString(1, newGrade.toUpperCase());
			preparedStmt.setString(2, studentId);
			preparedStmt.setString(3, course.substring(0, 3));
			preparedStmt.setString(4, course.substring(3, 6));

			preparedStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @postcondition a prepared statement that deletes class currently taking
	 *                for the admin
	 * 
	 * @param course
	 *            the course to delete
	 */
	public void deleteSelectedClass(String course) {
		String query = "delete from courses_taking where student_id = ? and course_name = ? and course_number = ?";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = myConn.prepareStatement(query);
			preparedStmt.setString(1, studentId);
			preparedStmt.setString(2, course.substring(0, 3));
			preparedStmt.setString(3, course.substring(3, 6));

			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean alreadyTaking;

	/**
	 * @postcondition a prepared statement that add the selected course to
	 *                currently taking
	 * 
	 * @param selected
	 *            selected value in the listview
	 */
	public void addSelectedClass(String selected) {
		alreadyTaking = false;
		String query = " insert into courses_taking (student_id, course_name, course_number, course_credit, course_credit_name)"
				+ " values (?, ?, ?, ?, ?)";

		if (courseNotTakenCheck(selected)) {
			String areaRequired = courseSearch(selected);
			try {
				PreparedStatement preparedStmt = myConn.prepareStatement(query);

				preparedStmt.setString(1, studentId);
				preparedStmt.setString(2, selected.substring(0, 3));
				preparedStmt.setString(3, selected.substring(3, 6));
				preparedStmt.setString(4, selected.split("		")[2]);
				preparedStmt.setString(5, areaRequired);

				preparedStmt.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			alreadyTaking = true;
		}

	}

	/**
	 * 
	 * @precondition checks to see if the course name already exist
	 * 
	 * @postcondition if exit return true, else false
	 * @param selected
	 *            selected value in the listview
	 * @return returns true if the class was found, and false if it was not
	 */
	public boolean courseNotTakenCheck(String selected) {
		String query = "SELECT * FROM sain_report.courses_taking;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				String temp = rs.getString("course_name") + rs.getString("course_number");
				if (temp.equals(selected.substring(0, 6)) && rs.getString("student_id").equals(studentId)) {
					return false;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 
	 * @precondition if the course equals the selected sent
	 * 
	 * @postcondition sends back the area required
	 * 
	 * @param selected
	 *            selected value in the listview
	 * @return returns the string of course
	 */
	public String courseSearch(String selected) {
		String query = "SELECT * FROM sain_report.courses;";
		ResultSet rs;

		try {
			rs = myStmt.executeQuery(query);

			while (rs.next()) {
				String temp = rs.getString("course_name") + rs.getString("course_number");
				if (selected.split("		")[0].equals(temp)) {
					return rs.getString("area_required");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
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

	public ObservableList<String> getCtkn() {
		return ctkn;
	}

	public ObservableList<String> getTak() {
		return tak;
	}

	public ObservableList<String> getCourses() {
		return courses;
	}

	public boolean isAlreadyTaking() {
		return alreadyTaking;
	}

	// end of getter methods
}
