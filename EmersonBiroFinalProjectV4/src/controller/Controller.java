package controller;

import handling.Observer;
import handling.GloblVars.Events;
import model.MySql;
import view.FacultyView;
import view.LoginView;
import view.StudentView;
import view.ViewGeneric;

public class Controller implements Observer {
	private ViewGeneric view;
	private MySql sql;

	public Controller(ViewGeneric view, MySql sql) {
		view.addObserver(this);
		this.view = view;
		this.sql = sql;
	}

	@Override
	public void update(Object args) {
		if (args instanceof Events) {
			decodeEvent((Events) args);
		}
	}

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
				switch (sql.getLoggedInLevel()) {
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
			view = new LoginView(view.getStage(), view.getObservers());
			break;
		default:
			break;

		}
	}

	private void adminLogedIn() {
		sql.getAdminInfo(); // this will get data based on the admin that logged
							// in
	}

	private void facultyLogedIn() {
		sql.getFacultyInfo(); // this will get data based on the faculty that
								// logged in
		view = new FacultyView(view.getStage(), view.getObservers());
		view.getStage().setResizable(true);
		((FacultyView) view).facultyInfoTop(sql.getFacInfoBag());
	}

	private void studentLogedIn() {
		sql.getStudentInfo(); // this will get data based on the student that
								// logged in
		view = new StudentView(view.getStage(), view.getObservers());
		view.getStage().setResizable(true);
		((StudentView) view).setBottom();
		((StudentView) view).studentInfoTop(sql.getStuInfoBag());
		sql.getAccountData();
		fillStudentTables();
	}

	private void studentSearched() {
		if (sql.searchStudent(((FacultyView) view).getId())) {
			sql.getStudentInfo();
			view = new StudentView(view.getStage(), view.getObservers());
			view.getStage().setResizable(true);
			((StudentView) view).setFaculty(true);
			((StudentView) view).setBottom();
			((StudentView) view).studentInfoTop(sql.getStuInfoBag());
			sql.getAccountData();
			fillStudentTables();
		} else {
			view.idNotFoundWindow();
		}

	}
	
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