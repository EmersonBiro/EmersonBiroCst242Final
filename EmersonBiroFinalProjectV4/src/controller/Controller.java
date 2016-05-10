package controller;

import handling.Observer;
import handling.GloblVars.Events;
import model.MySql;
import view.FacultyView;
import view.LoginView;
import view.StudentView;
import view.ViewGeneric;

/**
 * This is the controller class where the interactions between the view and
 * model happen. This is the basis of the MVC design pattern
 *
 */
public class Controller implements Observer {
	private ViewGeneric view;
	private MySql sql;
	private String level;

	/**
	 * 
	 * This is the controller constructor, this is what is used when the program
	 * runs.
	 * 
	 * @param view
	 *            the view of the mvc
	 * @param sql
	 *            the model of the mvc
	 */
	public Controller(ViewGeneric view, MySql sql) {
		view.addObserver(this);
		this.view = view;
		this.sql = sql;
	}

	/**
	 * @param args
	 *            args is the Event of the button pressed in the view
	 * 
	 * @precondition args is and instance of Events
	 * 
	 */
	@Override
	public void update(Object args) {
		if (args instanceof Events) {
			decodeEvent((Events) args);
		}
	}

	/**
	 * 
	 * @param event
	 *            if event is an instant of Events, then it can be decoded
	 * 
	 * @precondition switch statement that decodes the event
	 * 
	 * @postcondition and action based on the event is fired
	 */
	private void decodeEvent(Events event) {
		switch (event) {
		case EXIT_BUTTON:
			System.exit(0);
			break;
		case FV_SEARCH_BUTTON:
			studentSearched();

			break;
		case LV_LOGIN_BUTTON:
			if (sql.checkLogin(((LoginView) view).getUsername(), ((LoginView) view).getPassword())) {
				level = sql.getLoggedInLevel();
				switch (level) {
				case "1":
					studentLogedIn();
					break;
				case "2":
					facultyLogedIn();
					break;
				case "3":
					adminLogedIn();
					break;
				}
			} else {
				view.errorWindow();
			}
			break;
		case SV_SAIN_BUTTON:
			sql.getAccountData();
			fillStudentTables();
			break;
		case SV_WHATIF_BUTTON:
			if (sql.setMajor(((StudentView) view).whatIfChooseMajor(sql.getMajorBag()))) {
				fillStudentTables();
			}
			break;
		case SV_BACK_BUTTON:
			facultyLogedIn();
			break;
		case LOGOUT_BUTTON:
			view.getStage().setTitle("SAIN LOGIN");
			view = new LoginView(view.getStage(), view.getObservers());
			break;
		case MDFY_CRS_TAKEN:
			((StudentView) view).modifyCoursesTaken(sql.getCtkn());
			break;
		case MDFY_CRS_TAKING:
			((StudentView) view).modifyCoursesTaking(sql.getTak(), sql.getCourses());
			break;
		case CHANGE_GRADE_BUTTON:
			sql.updateSelectedGrade(((StudentView) view).getSelected().split("		")[0],
					((StudentView) view).getChangeGradet());
			sql.getAccountData();
			fillStudentTables();
			((StudentView) view).modifyCoursesTaken(sql.getCtkn());
			break;
		case ADD_CLASS_BUTTON:
			sql.addSelectedClass(((StudentView) view).getAddClasst());
			sql.getAccountData();
			fillStudentTables();
			if (sql.isAlreadyTaking()) {
				view.classAlreadyTaken();
			}
			((StudentView) view).modifyCoursesTaking(sql.getTak(), sql.getCourses());

			break;
		case DELTE_CLASS_BUTTON:
			sql.deleteSelectedClass(((StudentView) view).getSelected().split("		")[0]);
			sql.getAccountData();
			fillStudentTables();
			((StudentView) view).modifyCoursesTaking(sql.getTak(), sql.getCourses());
			break;
		default:
			break;

		}
	}

	/**
	 * 
	 * @postcondition sets the correct data for the admin when he logs in
	 */
	private void adminLogedIn() {
		sql.getAdminInfo(); // this will get data based on the admin that logged
							// in

		view = new FacultyView(view.getStage(), view.getObservers());
		view.getStage().setTitle("SAIN REPORT");
		view.getStage().setResizable(true);
		((FacultyView) view).facultyInfoTop(sql.getAdmInfoBag());
	}

	/**
	 * @postcondition sets the correct data for the faculty when he logs in
	 */
	private void facultyLogedIn() {
		sql.getFacultyInfo(); // this will get data based on the faculty that
								// logged in

		view = new FacultyView(view.getStage(), view.getObservers());
		view.getStage().setTitle("SAIN REPORT");
		view.getStage().setResizable(true);
		((FacultyView) view).facultyInfoTop(sql.getFacInfoBag());
	}

	/**
	 * @Postcondition sets the correct data for the student when he logs in
	 */
	private void studentLogedIn() {
		sql.getStudentInfo(); // this will get data based on the student that
								// logged in
		view = new StudentView(view.getStage(), view.getObservers());
		view.getStage().setTitle("SAIN REPORT");
		view.getStage().setResizable(true);
		((StudentView) view).setBottom();
		((StudentView) view).studentInfoTop(sql.getStuInfoBag());
		sql.getAccountData();
		fillStudentTables();
	}

	/**
	 * @precondition the id the faculty searched has to exist, and equal one of
	 *               the students if persons is an admin, show the modify
	 *               buttons
	 * 
	 * @postcondition shows the student view, after searched and displays it
	 *                accordingly based on your level
	 */
	private void studentSearched() {
		if (sql.searchStudent(((FacultyView) view).getId())) {
			sql.getStudentInfo();
			view = new StudentView(view.getStage(), view.getObservers());
			view.getStage().setResizable(true);
			((StudentView) view).setFaculty(true);
			if (level.equals("3")) {
				((StudentView) view).setAdmin(true);
			}
			((StudentView) view).setBottom();
			((StudentView) view).studentInfoTop(sql.getStuInfoBag());
			sql.getAccountData();
			fillStudentTables();
		} else {
			view.idNotFoundWindow();
		}

	}

	/**
	 * @postcondition when this method is called, it refreshes all the tables
	 *                with the most accurate data
	 * 
	 */
	private void fillStudentTables() {
		((StudentView) view).getReqCoursesTakenList().setItems(sql.getReqCoursesTaken());
		((StudentView) view).getOtherCoursesTakenList().setItems(sql.getOtherCoursesTaken());
		((StudentView) view).getWithFailedCoursesTakenList().setItems(sql.getFailedWithdrawn());
		((StudentView) view).getCurrTakingCoursesList().setItems(sql.getCoursesTaking());
		((StudentView) view).getCoursesNeededList().setItems(sql.getCoursesNeeded());
		((StudentView) view).getSummaryList().setItems(sql.getSummary());
		((StudentView) view).getGpaR().setText(sql.getCalcGpa());
	}
}
