package view;

import java.util.ArrayList;

import handling.Observer;
import handling.Subject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public abstract class ViewGeneric extends Scene implements Subject {
	protected ArrayList<Observer> obsArr;
	protected Stage stage;
	protected boolean first = true;

	public ViewGeneric(Stage stage, Parent root, int width, int height) {
		super(root, width, height);
		this.stage = stage;
	}

	public ViewGeneric(Stage stage, Parent root, int width, int height, ArrayList<Observer> obsArr) {
		super(root, width, height);
		this.stage = stage;
		this.obsArr = obsArr;
	}

	public void errorWindow() {
		// this is just the error window you get if you failed to login
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Username or Password");
		alert.setHeaderText("The Username or Password you entered is incorrect");
		alert.setContentText("Try again or exit!");

		alert.showAndWait();
	}

	protected void init() {
		stage.setScene(this);
		if (first) {
			stage.setResizable(false);
			stage.setTitle("SAIN Login");
			stage.show();
			first = false;
		}
	}

	public ArrayList<Observer> getObservers() {
		return obsArr;
	}

	@Override
	public void addObserver(Observer o) {
		if (obsArr == null)
			obsArr = new ArrayList<>();
		obsArr.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		if (obsArr == null)
			return;
		obsArr.remove(o);
	}

	@Override
	public void NotifyObservers(Object args) {
		if (obsArr == null)
			return;
		for (Observer observer : obsArr) {
			observer.update(args);
		}
	}

	public Stage getStage() {
		return stage;
	}

}
