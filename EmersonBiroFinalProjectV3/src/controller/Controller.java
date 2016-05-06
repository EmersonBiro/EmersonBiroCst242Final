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
				((LoginView) view).stop(); // close the login window
				view = new StudentView(view.getStage(), view.getObservers(), sql.getStudentData(), sql.coursesTaken());
				view.getStage().setTitle("SAIN Report");
				((StudentView)view).getReqCoursesTakenList().setItems(sql.getReqCoursesTaken());
				((StudentView)view).getOtherCoursesTakenList().setItems(sql.getOtherCoursesTaken());
				((StudentView)view).getWithFailedCoursesTakenList().setItems(sql.getFailedWithdrawn());
				view.getStage().setResizable(true);
			} else {
				view.errorWindow();
			}

			break;
		case EXIT_BUTTON:
			System.exit(0);
			break;
		default:
			break;
		}
	}

}
