import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.collections.*;
import java.time.LocalDate;
import java.util.UUID;

public class ProjectFrame {
    Stage stage;
    ProjectManager projectManager;
    int projectIndex;

    PanelMain panelMain;
    Label nameLabel;
    TextField nameInput;
    Label departmentLabel;
    DepartmentInput departmentInput;
    Label membersLabel;
    TextField memberInput;
    Button addMemberButton;
    MembersInput membersInput;
    Button delMemberButton;
    Label leaderLabel;
    ChoiceBox<String> leaderInput;
    Label startDateLabel;
    DatePicker startDateInput;
    Label endDateLabel;
    DatePicker endDateInput;
    Label budgetLabel;
    TextField budgetInput;
    Label incomeLabel;
    TextField incomeInput;
    Label progressLabel;
    ProgressInput progressInput;
    Button okButton;
    Button cancelButton;

    private class DepartmentInput extends GridPane {
	private ToggleGroup departmentsGroup;
	private RadioButton[][] departmentButtons;
	private String[][] departments = { {"PMAB", "AMSN", "EN", "SA"},
					   {"ICT", "WEO", "MST", "FST"} };

	public DepartmentInput() {
	    super();
	    this.departmentsGroup = new ToggleGroup();
	    this.departmentButtons = new RadioButton[this.departments.length][this.departments[0].length];
	    createComponents();

	    setHgap(16);
	    setVgap(8);
	}

	private void createComponents() {
	    for (int i = 0; i < this.departments.length; i++) {
		for (int j = 0; j < this.departments[0].length; j++) {
		    RadioButton departmentButton = new RadioButton(this.departments[i][j]);
		    departmentButton.setUserData(this.departments[i][j]);
		    departmentButton.setToggleGroup(this.departmentsGroup);
		    add(departmentButton, i, j);
		    this.departmentButtons[i][j] = departmentButton;
		}
	    }
	}

	public String getDepartment() {
	    return this.departmentsGroup.getSelectedToggle().getUserData().toString();
	}

	public ObservableList<Toggle> getDepartmentToggles() {
	    return this.departmentsGroup.getToggles();
	}

	public RadioButton[][] getDepartmentButtons() {
	    return this.departmentButtons;
	}
    }

    private class MembersInput extends ListView<String> {

	public MembersInput() {
	    super();

	    setMaxHeight(150);
	}

	public String[] getMembers() {
	    return getItems().toArray(new String[0]);
	}
    }

    private class ProgressInput extends Slider {

	public ProgressInput() {
	    super();

	    setMin(0);
	    setMax(100);
	    setMajorTickUnit(10);
	    setMinorTickCount(0);
	    setShowTickMarks(true);
	    setShowTickLabels(true);
	    setSnapToTicks(true);
	}
    }

    private class PanelMain extends GridPane {

	public PanelMain() {
	    super();

	    setAlignment(Pos.CENTER);
	    setHgap(10);
	    setVgap(12);
	    setPadding(new Insets(20));
	}
    }

    public ProjectFrame(ProjectManager projectManager) {
	this.projectManager = projectManager;

	this.stage = new Stage();
	createComponents();

	this.memberInput.setOnAction(event -> {
		addMemberAction();
		event.consume();
	    });
	this.addMemberButton.setOnAction(event -> addMemberAction());
	this.delMemberButton.setOnAction(event -> delMemberAction());
	this.progressInput.valueProperty().addListener((observable, oldValue, newValue) -> {
		if (newValue.doubleValue() == 100.0)
		    this.endDateInput.setDisable(false);
		else
		    this.endDateInput.setDisable(true);
	    });

	this.okButton.setOnAction(event -> submit());
	this.cancelButton.setOnAction(event -> close());
    }

    private void addMemberAction() {
	ObservableList<String> members = this.membersInput.getItems();
	String member = this.memberInput.getText().trim();
	if (!member.isEmpty() && members.contains(member))
	    (new Alert(Alert.AlertType.ERROR, "Name already exists.")).show();
	else if (!member.isEmpty()) {
	    members.add(0, member);
	    this.memberInput.setText("");
	}
    }

    private void delMemberAction() {
	ObservableList<String> members = this.membersInput.getItems();
	int memberIndex = this.membersInput.getFocusModel().getFocusedIndex();
	if (memberIndex != -1) {
	    String member = members.remove(memberIndex);
	    if (this.leaderInput.getValue() == member)
		this.leaderInput.setValue(null);
	}
    }

    private void submit() {
	// Error checking
	if (this.nameInput.getText().trim().isEmpty()) {
	    (new Alert(Alert.AlertType.ERROR, "Empty project name.")).show();
	    return;
	}
	if (this.progressInput.getValue() == 100.0 &&
	    ( this.endDateInput.getValue() == null ||
	      this.startDateInput.getValue().isAfter(this.endDateInput.getValue()) )) {
	    (new Alert(Alert.AlertType.ERROR, "Invalid date range.")).show();
	    return;
	}

	double sBudget;
	double sIncome;
	try {
	    sBudget = Double.parseDouble(this.budgetInput.getText());
	} catch (NumberFormatException e) {
	    (new Alert(Alert.AlertType.ERROR, "Invalid value for budget.")).show();
	    return;
	}
	try {
	    sIncome = Double.parseDouble(this.incomeInput.getText());
	} catch (NumberFormatException e) {
	    (new Alert(Alert.AlertType.ERROR, "Invalid value for income.")).show();
	    return;
	}

	// Make project
	String name = this.nameInput.getText().trim();
	String department = this.departmentInput.getDepartment();
	String[] teamMembers = this.membersInput.getMembers();
	String teamLeader = this.leaderInput.getValue();
	int budget = (int) sBudget;
	int income = (int) sIncome;
	int progress = (int) this.progressInput.getValue();
	LocalDate startDate = this.startDateInput.getValue();
	LocalDate endDate = this.endDateInput.getValue();

	Project project = new Project(name, department, teamLeader, teamMembers,
				      budget, income, progress, startDate, endDate);

	// Create or edit
	if (this.projectIndex == -1)
	    this.projectManager.addProject(project);
	else
	    this.projectManager.setProject(projectIndex, project);

	close();
    }

    public void show() {
	this.projectIndex = -1;

	this.nameInput.setText(null);
	this.departmentInput.getDepartmentButtons()[0][0].setSelected(true);
	this.memberInput.setText(null);
	this.membersInput.getItems().clear();
	this.leaderInput.setValue(null);
	this.budgetInput.setText(null);
	this.incomeInput.setText(null);
	this.startDateInput.setValue(LocalDate.now());
	this.endDateInput.setValue(null);
	this.progressInput.setValue(0);

	this.nameInput.requestFocus();

	this.stage.setTitle("New project");
	this.stage.showAndWait();
    }

    public void show(int projectIndex) {
	Project project = this.projectManager.getProjects().get(projectIndex);
	this.projectIndex = projectIndex;

	this.nameInput.setText(project.getName());
	for (Toggle departmentButton : this.departmentInput.getDepartmentToggles()) {
	    if (departmentButton.getUserData().toString().equals(project.getDepartment()))
		departmentButton.setSelected(true);
	}
	this.membersInput.getItems().setAll(project.getTeamMembers());
	this.leaderInput.setValue(project.getTeamLeader());
	this.budgetInput.setText(String.valueOf(project.getBudget()));
	this.incomeInput.setText(String.valueOf(project.getIncome()));
	this.startDateInput.setValue(project.getStartDate());
	this.endDateInput.setValue(project.getEndDate());
	this.progressInput.setValue(project.getProgress());

	this.okButton.requestFocus();

	this.stage.setTitle("Edit project");
	this.stage.showAndWait();
    }

    public void close() {
	this.stage.close();
    }

    private void createComponents() {
	this.panelMain = new PanelMain();
	Scene scene = new Scene(this.panelMain);
	this.stage.initModality(Modality.APPLICATION_MODAL);
	this.stage.setResizable(false);
	this.stage.setScene(scene);

	this.nameLabel = new Label("Project name:");
	this.nameInput = new TextField();
	this.panelMain.addRow(0, this.nameLabel, this.nameInput);

	this.departmentLabel = new Label("Department:");
	this.departmentInput = new DepartmentInput();
	this.panelMain.addRow(1, this.departmentLabel, this.departmentInput);

	this.membersLabel = new Label("Team members:");
	this.memberInput = new TextField();
	this.addMemberButton = new Button("Add");
	this.addMemberButton.setMaxWidth(Double.MAX_VALUE);
	this.membersInput = new MembersInput();
	this.delMemberButton = new Button("Delete");
	GridPane membersGrid = new GridPane();
	membersGrid.setHgap(10);
	membersGrid.setVgap(12);
	membersGrid.addRow(0, memberInput, addMemberButton);
	membersGrid.addRow(1, membersInput, delMemberButton);
	this.panelMain.addRow(2, this.membersLabel, membersGrid);

	this.leaderLabel = new Label("Project leader:");
	this.leaderInput = new ChoiceBox<String>();
	this.leaderInput.setItems(this.membersInput.getItems());
	this.panelMain.addRow(3, this.leaderLabel, this.leaderInput);

	this.startDateLabel = new Label("Start date:");
	this.startDateInput = new DatePicker();
	this.panelMain.addRow(4, this.startDateLabel, this.startDateInput);

	this.endDateLabel = new Label("Finish date:");
	this.endDateInput = new DatePicker();
	this.endDateInput.setDisable(true);
	this.panelMain.addRow(5, this.endDateLabel, this.endDateInput);

	this.budgetLabel = new Label("Budget:");
	this.budgetInput = new TextField();
	this.panelMain.addRow(6, this.budgetLabel, this.budgetInput);

	this.incomeLabel = new Label("Income:");
	this.incomeInput = new TextField();
	this.panelMain.addRow(7, this.incomeLabel, this.incomeInput);

	this.progressLabel = new Label("Progress:");
	this.progressInput = new ProgressInput();
	this.panelMain.addRow(8, this.progressLabel, this.progressInput);

	HBox buttonsPanel = new HBox();
	buttonsPanel.setAlignment(Pos.CENTER);
	buttonsPanel.setSpacing(10);
	buttonsPanel.setPadding(new Insets(10, 0, 10, 0));

	this.okButton = new Button("OK");
	this.okButton.setDefaultButton(true);
	buttonsPanel.getChildren().add(this.okButton);
	this.cancelButton = new Button("Cancel");
	buttonsPanel.getChildren().add(this.cancelButton);

	GridPane.setColumnSpan(buttonsPanel, 2);
	this.panelMain.addRow(9, buttonsPanel);
    }
}
