package controller;

import handling.Observer;
import handling.GloblVars.Events;
import model.MySql;
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
		case LV_LOGIN_BUTTON:

			if (sql.checkLogin(((LoginView) view).getUsername(), ((LoginView) view).getPassword())) {
				if (sql.isStudent()) {
					((LoginView) view).stop(); // close the login window
					view = new StudentView(view.getStage(), view.getObservers());
					view.getStage().setTitle("SAIN Report");

					handleStudent();
				} else if (sql.isFaculty()) {

				} else if (sql.isAdmin()) {

				}
			} else {
				view.errorWindow();
			}

			break;
		case EXIT_BUTTON:
			System.exit(0);
			break;
		case SV_SAIN_BUTTON:
			sql.getAccountData();
			handleStudent();
			break;
		case SV_WHATIF_BUTTON:

			if (sql.setMajor(((StudentView) view).whatIfChooseMajor(sql.getMajorBag()))) {
				((StudentView) view).getReqCoursesTakenList().setItems(sql.getReqCoursesTaken());
				((StudentView) view).getOtherCoursesTakenList().setItems(sql.getOtherCoursesTaken());
				((StudentView) view).getWithFailedCoursesTakenList().setItems(sql.getFailedWithdrawn());
				((StudentView) view).getCurrTakingCoursesList().setItems(sql.coursesTaking());
			}
			break;
		default:
			break;
		}
	}

	private void handleStudent() {

		sql.setStudentData();
		((StudentView) view).studentInfoTop(sql.getPersonData());
		((StudentView) view).getReqCoursesTakenList().setItems(sql.getReqCoursesTaken());
		((StudentView) view).getOtherCoursesTakenList().setItems(sql.getOtherCoursesTaken());
		((StudentView) view).getWithFailedCoursesTakenList().setItems(sql.getFailedWithdrawn());
		((StudentView) view).getCurrTakingCoursesList().setItems(sql.coursesTaking());
		((StudentView) view).getGpaR().setText(sql.getCalcGpa());
		view.getStage().setResizable(true);

	}

}
