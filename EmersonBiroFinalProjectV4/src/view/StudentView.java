package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import handling.GloblVars;
import handling.GloblVars.Events;
import handling.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StudentView extends ViewGeneric {

	private BorderPane root;

	public StudentView(Stage stage, ArrayList<Observer> obsArr) {
		super(stage, new BorderPane(), GloblVars.SV_WIDTH, GloblVars.SV_HEIGHT, obsArr);
		root = (BorderPane) getRoot();
		obsArr = new ArrayList<>();
		setCenter();
		init();
	}

	public void setBottom() {
		bottom();
	}

	private Label gpaR;
	private boolean admin = false;

	public void studentInfoTop(String[] info) {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 0, 10));
		gp.setHgap(100);
		gp.setVgap(5);

		Label nameL = new Label("Name:");
		GridPane.setConstraints(nameL, 0, 0);
		Label nameR = new Label();
		GridPane.setConstraints(nameR, 1, 0);

		Label idL = new Label("ID: ");
		GridPane.setConstraints(idL, 0, 1);
		Label idR = new Label();
		GridPane.setConstraints(idR, 1, 1);

		Label majorL = new Label("Major: ");
		GridPane.setConstraints(majorL, 0, 2);
		Label majorR = new Label();
		GridPane.setConstraints(majorR, 1, 2);

		Label gpaL = new Label("GPA: ");
		GridPane.setConstraints(gpaL, 0, 3);
		gpaR = new Label();
		GridPane.setConstraints(gpaR, 1, 3);

		Label campusL = new Label("Campus: ");
		GridPane.setConstraints(campusL, 0, 4);
		Label campusR = new Label();
		GridPane.setConstraints(campusR, 1, 4);

		gp.getChildren().addAll(nameL, nameR, idL, idR, majorL, majorR, gpaL, gpaR, campusL, campusR);

		idR.setText(info[0]);
		nameR.setText(info[1]);
		gpaR.setText(info[2]);
		majorR.setText(info[3]);
		campusR.setText(info[4]);

		if (admin) {
			Button modifyCoursesTaken = new Button("Mdfy Crs Taken");
			GridPane.setConstraints(modifyCoursesTaken, 2, 0);
			Button modifyCoursesTaking = new Button("Mdfy Crs Taking");
			GridPane.setConstraints(modifyCoursesTaking, 3, 0);

			modifyCoursesTaken.setOnAction(e -> {
				NotifyObservers(Events.MDFY_CRS_TAKEN);
			});

			modifyCoursesTaking.setOnAction(e -> {
				NotifyObservers(Events.MDFY_CRS_TAKING);
			});

			gp.getChildren().addAll(modifyCoursesTaken, modifyCoursesTaking);
		}
		root.setTop(gp);
		admin = false;
	}

	///////////////////////////////////////////////
	private ListView<String> reqCoursesTakenList = new ListView<>();

	public ListView<String> getReqCoursesTakenList() {
		return reqCoursesTakenList;
	}

	///////////////////////////////////////////////

	///////////////////////////////////////////////
	private ListView<String> otherCoursesTakenList = new ListView<>();

	public ListView<String> getOtherCoursesTakenList() {
		return otherCoursesTakenList;
	}

	///////////////////////////////////////////////

	///////////////////////////////////////////////
	private ListView<String> withFailedCoursesTakenList = new ListView<>();

	public ListView<String> getWithFailedCoursesTakenList() {
		return withFailedCoursesTakenList;
	}

	///////////////////////////////////////////////

	///////////////////////////////////////////////
	private ListView<String> currTakingCoursesList = new ListView<>();

	public ListView<String> getCurrTakingCoursesList() {
		return currTakingCoursesList;
	}
	///////////////////////////////////////////////

	///////////////////////////////////////////////
	private ListView<String> getCoursesNeededList = new ListView<>();

	public ListView<String> getCoursesNeededList() {
		return getCoursesNeededList;
	}
	///////////////////////////////////////////////

	///////////////////////////////////////////////
	private ListView<String> getSummaryList = new ListView<>();

	public ListView<String> getSummaryList() {
		return getSummaryList;
	}
	///////////////////////////////////////////////

	public void setCenter() {
		VBox center = new VBox(10);
		center.setPadding(new Insets(10, 10, 10, 10));
		GridPane gridPaneCenter = new GridPane();
		// gridPaneCenter.setPadding(new Insets(10, 10, 10, 10));
		gridPaneCenter.setAlignment(Pos.CENTER);
		gridPaneCenter.setHgap(10);
		gridPaneCenter.setVgap(10);

		Font font = new Font(20);

		Label reqCoursesTaken = new Label("Required Courses Taken");
		reqCoursesTaken.setAlignment(Pos.CENTER);
		reqCoursesTaken.setFont(font);
		GridPane.setConstraints(reqCoursesTaken, 0, 0);
		GridPane.setConstraints(reqCoursesTakenList, 0, 1);

		Label otherCoursesTaken = new Label("Other Courses Taken");
		otherCoursesTaken.setAlignment(Pos.CENTER);
		otherCoursesTaken.setFont(font);
		GridPane.setConstraints(otherCoursesTaken, 1, 0);
		GridPane.setConstraints(otherCoursesTakenList, 1, 1);

		Label withdrawnFailedCoursesTaken = new Label("Withdrawn / Failed");
		withdrawnFailedCoursesTaken.setAlignment(Pos.CENTER);
		withdrawnFailedCoursesTaken.setFont(font);
		GridPane.setConstraints(withdrawnFailedCoursesTaken, 2, 0);
		GridPane.setConstraints(withFailedCoursesTakenList, 2, 1);

		Label currentlyTakingCourses = new Label("Currently Taking");
		currentlyTakingCourses.setAlignment(Pos.CENTER);
		currentlyTakingCourses.setFont(font);
		GridPane.setConstraints(currentlyTakingCourses, 3, 0);
		GridPane.setConstraints(currTakingCoursesList, 3, 1);

		gridPaneCenter.getChildren().addAll(reqCoursesTaken, otherCoursesTaken, withdrawnFailedCoursesTaken,
				currentlyTakingCourses, reqCoursesTakenList, otherCoursesTakenList, withFailedCoursesTakenList,
				currTakingCoursesList);

		VBox crsNeeded = new VBox(10);
		Label coursesNeeded = new Label("Courses Needed");
		coursesNeeded.setAlignment(Pos.CENTER);
		coursesNeeded.setFont(font);

		crsNeeded.getChildren().addAll(coursesNeeded, getCoursesNeededList);

		VBox summary = new VBox(10);
		Label summaryl = new Label("Summary");
		summaryl.setAlignment(Pos.CENTER);
		summaryl.setFont(font);

		summary.getChildren().addAll(summaryl, getSummaryList);

		center.getChildren().addAll(gridPaneCenter, crsNeeded, summary);
		root.setCenter(center);
	}

	boolean faculty;

	public void bottom() {
		HBox bottom = new HBox(10);
		bottom.setPadding(new Insets(10, 10, 10, 10));
		bottom.setAlignment(Pos.CENTER);

		Button sain = new Button("SAIN Report");
		Button whatIf = new Button("What-If Analysis");
		Button logout = new Button("Logout");
		bottom.getChildren().addAll(logout, sain, whatIf);
		Button back = null;

		if (faculty == true) {
			back = new Button("Search Again");
			bottom.getChildren().add(back);

			back.setOnAction(e -> {
				NotifyObservers(Events.SV_BACK_BUTTON);
			});
		}

		logout.setOnAction(e -> {
			NotifyObservers(Events.LOGOUT_BUTTON);
		});

		sain.setOnAction(e -> {
			NotifyObservers(Events.SV_SAIN_BUTTON);
		});

		whatIf.setOnAction(e -> {
			NotifyObservers(Events.SV_WHATIF_BUTTON);
		});

		root.setBottom(bottom);
		faculty = false;
	}

	public String whatIfChooseMajor(ArrayList<String> majors) {
		List<String> choices = new ArrayList<>();
		for (int i = 0; i < majors.size(); i++) {
			choices.add(majors.get(i).split(" ")[2] + " " + majors.get(i).split(" ")[0] + " "
					+ majors.get(i).split(" ")[1]);
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
		dialog.setTitle("What-if Anaylsis");
		dialog.setHeaderText("What-If Analysis");
		dialog.setContentText("Choose your major:");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			return result.get().split(" ")[0];
		}
		return null;
	}

	private String selected;
	private ComboBox<String> changeGradet;
	private ComboBox<String> addClasst;
	
	public void modifyCoursesTaken(ObservableList<String> s) {
		Stage mct = new Stage();
		mct.setTitle("Change Courses Taken");
		HBox root = new HBox(10);
		this.root.setDisable(true);
		root.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(root, 500, 500);

		ListView<String> coursesTaken = new ListView<>();
		coursesTaken.setItems(s);

		VBox right = new VBox(10);
		Label changeGrade = new Label("Select From Right,\n And Select New Grade");

		changeGradet = new ComboBox();
		changeGradet.getItems().addAll("A", "B+", "B", "C+", "C", "D+", "D", "F");
		changeGradet.setValue("A");

		Button mdfySelected = new Button("Change Grade");
		Button close = new Button("Close");

		right.getChildren().addAll(changeGrade, changeGradet, mdfySelected, close);

		coursesTaken.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selected = newValue;
			}
		});

		mdfySelected.setOnAction(e -> {
			NotifyObservers(Events.CHANGE_GRADE_BUTTON);
			mct.close();
		});

		close.setOnAction(e -> {
			mct.close();
			this.root.setDisable(false);
		});

		mct.setOnCloseRequest(e -> {
			this.root.setDisable(false);
		});

		root.getChildren().addAll(coursesTaken, right);

		mct.setScene(scene);
		mct.show();
	}

	public void modifyCoursesTaking(ObservableList<String> cTaking, ObservableList<String> courses) {
		Stage mcting = new Stage();
		mcting.setTitle("Change Courses Taking");
		HBox root = new HBox(10);
		this.root.setDisable(true);
		root.setPadding(new Insets(10, 10, 10, 10));
		Scene scene = new Scene(root, 500, 500);

		ListView<String> coursesTaken = new ListView<>();
		coursesTaken.setItems(cTaking);

		VBox right = new VBox(10);
		Label changeGrade = new Label("Select From Right,\n And Select New Class");

		addClasst = new ComboBox<String>();
		addClasst.getItems().addAll(courses);
		addClasst.getSelectionModel().selectFirst();

		Button add = new Button("Add class");
		Button delete = new Button("Delete");
		Button close = new Button("Close");

		right.getChildren().addAll(changeGrade, addClasst,add,delete, close);

		coursesTaken.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selected = newValue;
			}
		});

		add.setOnAction(e -> {
			NotifyObservers(Events.ADD_CLASS_BUTTON);
			mcting.close();
		});
		
		delete.setOnAction(e->{
			NotifyObservers(Events.DELTE_CLASS_BUTTON);
			mcting.close();
		});

		close.setOnAction(e -> {
			mcting.close();
			this.root.setDisable(false);
		});

		mcting.setOnCloseRequest(e -> {
			this.root.setDisable(false);
		});

		root.getChildren().addAll(coursesTaken, right);

		mcting.setScene(scene);
		mcting.show();
	}

	public String getChangeGradet() {
		return (String) changeGradet.getValue();
	}
	
	public String getAddClasst() {
		return (String) addClasst.getValue();
	}

	public String getSelected() {
		return selected;
	}

	public Label getGpaR() {
		return gpaR;
	}

	public void setFaculty(boolean faculty) {
		this.faculty = faculty;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
