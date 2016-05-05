package controller;

import model.MySQL;
import view.Login;
import view.WindowView;

public class Controller {
	
	public Controller(WindowView window, Login login, MySQL sql) {
		
		//when the login button is pressed
		login.loginButtonPressed(e->{
			//checks to see if your login credentials were corrects
			if (sql.CheckLogin(login.getUsernamet(), login.getPasswordt())) {
				
				if(sql.getCl().isStudent()) {
					window.studentView(sql.getStudentData(), sql.reqCoursesTaken());
					
				} else if(sql.getCl().isFaculty()) {
					
				} else if(sql.getCl().isAdmin()) {
					
				}
				
				
			} else { //display an error window if what you entered was wrong
				window.errorWindow();
			}
		});
		
	}
}
