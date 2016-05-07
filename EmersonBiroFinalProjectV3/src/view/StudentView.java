package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import handling.GloblVars;
import handling.GloblVars.Events;
import handling.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StudentView extends ViewGeneric {

	private BorderPane root;

	public StudentView(Stage stage, ArrayList<Observer> obsArr) {
		super(stage, new BorderPane(), GloblVars.SV_WIDTH, GloblVars.SV_HEIGHT, obsArr);
		root = (BorderPane) getRoot();
		obsArr = new ArrayList<>();

		setCenter();
		bottom();
		init();
	}

	private Label gpaR;

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

		idR.setText(info[0]);
		nameR.setText(info[1]);
		gpaR.setText(info[2]);
		majorR.setText(info[3]);
		campusR.setText(info[4]);

		gp.getChildren().addAll(nameL, nameR, idL, idR, majorL, majorR, gpaL, gpaR, campusL, campusR);

		root.setTop(gp);
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

	public void setCenter() {
		GridPane center = new GridPane();
		center.setPadding(new Insets(10, 10, 10, 10));
		center.setAlignment(Pos.CENTER);
		center.setHgap(10);
		center.setVgap(10);

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

		center.getChildren().addAll(reqCoursesTaken, otherCoursesTaken, withdrawnFailedCoursesTaken,
				currentlyTakingCourses, reqCoursesTakenList, otherCoursesTakenList, withFailedCoursesTakenList,
				currTakingCoursesList);

		root.setCenter(center);
	}

	public void bottom() {
		HBox bottom = new HBox(10);
		bottom.setPadding(new Insets(10, 10, 10, 10));
		bottom.setAlignment(Pos.CENTER);

		Button sain = new Button("SAIN Report");
		Button whatIf = new Button("What-If Analysis");

		bottom.getChildren().addAll(sain, whatIf);

		sain.setOnAction(e -> {
			NotifyObservers(Events.SV_SAIN_BUTTON);
		});

		whatIf.setOnAction(e -> {
			NotifyObservers(Events.SV_WHATIF_BUTTON);
		});

		root.setBottom(bottom);
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

	public Label getGpaR() {
		return gpaR;
	}

}
