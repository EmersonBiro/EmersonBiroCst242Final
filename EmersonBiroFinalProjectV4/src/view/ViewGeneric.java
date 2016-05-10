package view;

import java.util.ArrayList;

import handling.Observer;
import handling.Subject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * This is an abstract class that plays the role of scene control for the many
 * different views that are presented throughout the program. It extends Scene
 * to allow for interchanging of scenes, and implements subject to be able to be
 * used by the observer, to allow for MVC
 *
 */
public abstract class ViewGeneric extends Scene implements Subject {
	protected ArrayList<Observer> obsArr;
	protected Stage stage;
	protected boolean first = true;

	/**
	 * 
	 * this is the first constructor that does not contain an arraylist of
	 * observers
	 * 
	 * @param stage
	 *            stage the view will be shown on
	 * @param root
	 *            the root that goes in the scene
	 * @param width
	 *            width of window
	 * @param height
	 *            height of window
	 */
	public ViewGeneric(Stage stage, Parent root, int width, int height) {
		super(root, width, height);
		this.stage = stage;
	}

	/**
	 * This is the second constructor that does contain an arraylist of
	 * observers
	 * 
	 * @param stage
	 *            stage the view will be shown on
	 * @param root
	 *            the root that goes in the scene
	 * @param width
	 *            width of window
	 * @param height
	 *            height of window
	 * @param obsArr
	 *            array of observers
	 */
	public ViewGeneric(Stage stage, Parent root, int width, int height, ArrayList<Observer> obsArr) {
		super(root, width, height);
		this.stage = stage;
		this.obsArr = obsArr;
	}

	/**
	 * @postcondition displays an error window when the method is called, if you
	 *                fail to login
	 */
	public void errorWindow() {
		// this is just the error window you get if you failed to login
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Username or Password");
		alert.setHeaderText("The Username or Password you entered is incorrect");
		alert.setContentText("Try again or exit!");

		alert.showAndWait();
	}

	/**
	 * @postcondition displays an error window when the method is called, if the
	 *                id you searched for does not exist
	 */
	public void idNotFoundWindow() {
		// this is just the error window you get if you failed to login
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ID not found");
		alert.setHeaderText("The ID you entered was not found");
		alert.setContentText("Try again or exit!");

		alert.showAndWait();
	}

	/**
	 * @postcondition displays an error window when the method is called, if the
	 *                course you are trying to add as an admin is already being
	 *                taken
	 */
	public void classAlreadyTaken() {
		// this is just the error window you get if you failed to login
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Class already taken");
		alert.setHeaderText("The class you choose is taken already");
		alert.setContentText("Try again or exit!");

		alert.showAndWait();
	}

	/**
	 * @postcondition this method is called at the end of every new view
	 *                Constructor to show that view
	 */
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

	/**
	 * @postcondition adds an observer to the arraylist
	 */
	@Override
	public void addObserver(Observer o) {
		if (obsArr == null)
			obsArr = new ArrayList<>();
		obsArr.add(o);
	}

	/**
	 * 
	 * @precondition if the observer array is not null
	 * 
	 * @postcondition removes an observer from the arraylist
	 */
	@Override
	public void removeObserver(Observer o) {
		if (obsArr == null)
			return;
		obsArr.remove(o);
	}

	/**
	 * @precondition if the observer array is not null
	 * 
	 * @postcondition notify all observers in the arraylist that a change has
	 *                occured
	 * @param args
	 *            the object to notify
	 */
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
