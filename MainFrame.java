import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.effect.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.collections.*;
import java.time.LocalDate;
import java.util.UUID;
import java.text.NumberFormat;

public class MainFrame extends Application {
    private Stage stage;
    private ProjectFrame projectFrame;
    private StatisticsFrame statisticsFrame;
    private AboutFrame aboutFrame;

    private ProjectManager projectManager;
    private StatisticsManager statisticsManager;

    private VBox panelRoot;
    private CustomMenuBar customMenuBar;
    private VBox panelMain;
    private Label headerLabel;
    private DetailPanel detailPanel;
    private ProjectsTable projectsTable;
    private ButtonsPanel buttonsPanel;

    private class CustomMenuBar extends MenuBar {
        private Menu menuFile;
        private Menu menuTool;
        private Menu menuHelp;
        private MenuItem quitMenuItem;
        private MenuItem statsMenuItem;
        private MenuItem aboutMenuItem;

        public CustomMenuBar() {
            super();
            createComponents();
        }

        private void createComponents() {
            // File Menu
            this.menuFile = new Menu("File");
            this.quitMenuItem = new MenuItem("Quit");
            this.menuFile.getItems().addAll(this.quitMenuItem);

            // Tool Menu
            this.menuTool = new Menu("Tool");
            this.statsMenuItem = new MenuItem("Statistics");
            this.menuTool.getItems().addAll(this.statsMenuItem);

            // Help Menu
            this.menuHelp = new Menu("Help");
            this.aboutMenuItem = new MenuItem("About");
            this.menuHelp.getItems().addAll(this.aboutMenuItem);

            getMenus().addAll(this.menuFile, this.menuTool, this.menuHelp);
        }

        public MenuItem getQuit() {
            return this.quitMenuItem;
        }

        public MenuItem getStats() {
            return this.statsMenuItem;
        }

        public MenuItem getAbout() {
            return this.aboutMenuItem;
        }
    }

    private class DetailPanel extends TitledPane {
        private Label nameLabel;
        private Label departmentLabel;
        private Label leaderLabel;
        private TextArea membersLabel;
        private Label budgetLabel;
        private Label incomeLabel;
        private Label profitLabel;
        private Label startDateLabel;
        private Label endDateLabel;
        private ProgressBar progressLabel;
        private ImageView imageView;

        private NumberFormat nf;

        public DetailPanel() {
            super();
            createComponents();
            setText("Detailed Information");
            setCollapsible(false);
            this.nf = NumberFormat.getInstance();
            this.nf.setGroupingUsed(true);
        }

        public void display(Project project) {
            headerLabel.setText(project.getName());
            this.nameLabel.setText(project.getName());
            this.departmentLabel.setText(project.getDepartment());
            this.leaderLabel.setText(project.getTeamLeader());
            this.membersLabel.setText(project.getTeamMembersText());
            this.budgetLabel.setText(this.nf.format(project.getBudget()).toString() + " $/month");
            this.incomeLabel.setText(this.nf.format(project.getIncome()).toString() + " $/month");
            this.profitLabel.setText(this.nf.format(project.getProfit()).toString() + " $/month");
            this.startDateLabel.setText(project.getStartDateText());
            this.endDateLabel.setText(project.getEndDateText());
            this.progressLabel.setProgress(project.getProgress() / 100.0);
            this.imageView.setImage(new Image("image/" + project.getImage()));

            this.departmentLabel.setTextFill(Coloring.departmentColor(project.getDepartment()));
            this.budgetLabel.setTextFill(Coloring.moneyColor(project.getBudget()));
            this.incomeLabel.setTextFill(Coloring.moneyColor(project.getIncome()));
            this.profitLabel.setTextFill(Coloring.binaryColor(project.getProfit()));
            this.startDateLabel.setTextFill(Coloring.startDateColor(project.getStartDate()));
            this.endDateLabel.setTextFill(Coloring.endDateColor(project.getEndDate()));
        }

        public void display() {
            headerLabel.setText("Choose a project:");
            this.nameLabel.setText(null);
            this.departmentLabel.setText(null);
            this.leaderLabel.setText(null);
            this.membersLabel.setText(null);
            this.budgetLabel.setText(null);
            this.incomeLabel.setText(null);
            this.profitLabel.setText(null);
            this.startDateLabel.setText(null);
            this.endDateLabel.setText(null);
            this.progressLabel.setProgress(0.0);
            this.imageView.setImage(null);
        }

        private void createComponents() {
            GridPane detailGrid = new GridPane();
            detailGrid.setAlignment(Pos.CENTER);
            detailGrid.setHgap(6);
            detailGrid.setVgap(8);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHalignment(HPos.RIGHT);
            col1.setHgrow(Priority.ALWAYS);
            // col1.setPercentWidth(13);
            col1.setMaxWidth(120);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(35);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setHalignment(HPos.RIGHT);
            col3.setHgrow(Priority.ALWAYS);
            // col3.setPercentWidth(12);
            col3.setMaxWidth(120);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setHgrow(Priority.ALWAYS);
            col4.setMinWidth(120);
            col4.setMaxWidth(150);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setHalignment(HPos.CENTER);
            col5.setPercentWidth(20);
            detailGrid.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

            Font boldFont = Font.font("Arial", FontWeight.BOLD, 14);
            Font normFont = Font.font("Arial", FontWeight.NORMAL, 14);

            Label nameDescription = new Label("Name: ");
            nameDescription.setFont(boldFont);
            this.nameLabel = new Label();
            this.nameLabel.setFont(normFont);

            Label departmentDescription = new Label("Department:");
            departmentDescription.setFont(boldFont);
            this.departmentLabel = new Label();
            this.departmentLabel.setFont(boldFont);

            Label leaderDescription = new Label("Leader:");
            leaderDescription.setFont(boldFont);
            this.leaderLabel = new Label();
            this.leaderLabel.setFont(normFont);

            Label membersDescription = new Label("Members:");
            membersDescription.setFont(boldFont);
            this.membersLabel = new TextArea();
            this.membersLabel.setFont(normFont);
            this.membersLabel.setMinHeight(75);
            this.membersLabel.setPrefHeight(75);
            this.membersLabel.setMaxHeight(75);
            this.membersLabel.setWrapText(true);
            this.membersLabel.setEditable(false);

            Label budgetDescription = new Label("Budget:");
            budgetDescription.setFont(boldFont);
            this.budgetLabel = new Label();
            this.budgetLabel.setFont(normFont);

            Label incomeDescription = new Label("Income:");
            incomeDescription.setFont(boldFont);
            this.incomeLabel = new Label();
            this.incomeLabel.setFont(normFont);

            Label profitDescription = new Label("Profit:");
            profitDescription.setFont(boldFont);
            this.profitLabel = new Label();
            this.profitLabel.setFont(normFont);

            Label startDateDescription = new Label("Start date:");
            startDateDescription.setFont(boldFont);
            this.startDateLabel = new Label();
            this.startDateLabel.setFont(normFont);

            Label endDateDescription = new Label("Finish date:");
            endDateDescription.setFont(boldFont);
            this.endDateLabel = new Label();
            this.endDateLabel.setFont(normFont);

            this.progressLabel = new ProgressBar();
            this.progressLabel.setMaxWidth(Double.MAX_VALUE);
            this.progressLabel.setMinHeight(25);

            this.imageView = new ImageView();
            this.imageView.setSmooth(true);
            this.imageView.setCache(true);
            this.imageView.setPreserveRatio(true);
            this.imageView.setFitWidth(110);
            DropShadow dropShadow = new DropShadow();
            this.imageView.setEffect(dropShadow);

            GridPane.setRowSpan(this.membersLabel, 3);
            GridPane.setRowSpan(this.imageView, 5);
            GridPane.setColumnSpan(this.progressLabel, 5);

            detailGrid.addColumn(0,
                                 nameDescription,
                                 departmentDescription,
                                 leaderDescription,
                                 membersDescription);
            detailGrid.addColumn(1,
                                 this.nameLabel,
                                 this.departmentLabel,
                                 this.leaderLabel,
                                 this.membersLabel);
            detailGrid.addColumn(2,
                                 budgetDescription,
                                 incomeDescription,
                                 profitDescription,
                                 startDateDescription,
                                 endDateDescription);
            detailGrid.addColumn(3,
                                 this.budgetLabel,
                                 this.incomeLabel,
                                 this.profitLabel,
                                 this.startDateLabel,
                                 this.endDateLabel);
            detailGrid.addColumn(4,
                                 this.imageView);
            detailGrid.addRow(6, this.progressLabel);

            setContent(detailGrid);
        }
    }

    private class ProjectsTable extends TableView<Project> {

        public ProjectsTable() {
            super();
            createComponents();
        }

        private void createComponents() {
            TableColumn colDepartment = new TableColumn("Dept.");
            colDepartment.setStyle("-fx-alignment: CENTER;");
            colDepartment.setCellValueFactory(new PropertyValueFactory<Project, String>("Department"));
            getColumns().add(colDepartment);

            TableColumn colName = new TableColumn("Name");
            colName.setPrefWidth(300);
            colName.setCellValueFactory(new PropertyValueFactory<Project, String>("Name"));
            getColumns().add(colName);

            TableColumn colNumMembers = new TableColumn("Members");
            colNumMembers.setStyle("-fx-alignment: CENTER;");
            colNumMembers.setCellValueFactory(new PropertyValueFactory<Project, Integer>("NumMembers"));
            getColumns().add(colNumMembers);

            TableColumn colBudget = new TableColumn("Budget");
            colBudget.setStyle("-fx-alignment: CENTER_RIGHT;");
            colBudget.setCellValueFactory(new PropertyValueFactory<Project, Integer>("Budget"));
            getColumns().add(colBudget);

            TableColumn colIncome = new TableColumn("Income");
            colIncome.setStyle("-fx-alignment: CENTER_RIGHT;");
            colIncome.setCellValueFactory(new PropertyValueFactory<Project, Integer>("Income"));
            getColumns().add(colIncome);

            TableColumn colEndDate = new TableColumn("Finish");
            colEndDate.setStyle("-fx-alignment: CENTER;");
            colEndDate.setCellValueFactory(new PropertyValueFactory<Project, LocalDate>("EndDateApprox"));
            getColumns().add(colEndDate);

            TableColumn colProgress = new TableColumn("%Done");
            colProgress.setStyle("-fx-alignment: CENTER;");
            colProgress.setCellValueFactory(new PropertyValueFactory<Project, Integer>("Progress"));
            getColumns().add(colProgress);
        }
    }

    private class ButtonsPanel extends HBox {

        public ButtonsPanel() {
            super();
            createComponents();

            setAlignment(Pos.CENTER);
            setSpacing(10);
        }

        private void createComponents() {
            Button addButton = new Button("Add");
            addButton.setOnAction(event -> addAction());
            getChildren().add(addButton);
            Button editButton = new Button("Edit");
            editButton.setOnAction(event -> editAction());
            getChildren().add(editButton);
            Button delButton = new Button("Delete");
            delButton.setOnAction(event -> delAction());
            getChildren().add(delButton);
        }

        private void addAction() {
            projectFrame.show();
            projectsTable.getSelectionModel().select(0);
        }

        private void editAction() {
            int projectIndex = projectsTable.getSelectionModel().getSelectedIndex();
            if (projectIndex != -1) {
                projectFrame.show(projectIndex);
                projectsTable.getSelectionModel().select(projectIndex);
            }
        }

        private void delAction() {
            int projectIndex = projectsTable.getSelectionModel().getSelectedIndex();
            if (projectIndex != -1)
                (new Alert(Alert.AlertType.CONFIRMATION,
                           "Are you sure you want to delete this project?"))
                    .showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> projectManager.getProjects().remove(projectIndex));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.projectManager = new ProjectManager();
        this.statisticsManager = new StatisticsManager(this.projectManager);

        this.projectFrame = new ProjectFrame(this.projectManager);
        this.statisticsFrame = new StatisticsFrame(this.statisticsManager);
        this.aboutFrame = new AboutFrame();

        this.stage = stage;
        this.stage.setTitle("Project Management Pro Limited Edition Deluxe v0.99");
        createComponents();

        this.customMenuBar.getQuit().setOnAction(event -> this.stage.close());
        this.customMenuBar.getStats().setOnAction(event -> this.statisticsFrame.show());
        this.customMenuBar.getAbout().setOnAction(event -> this.aboutFrame.show());

        this.projectsTable.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null)
                        this.detailPanel.display(newValue);
                    else
                        this.detailPanel.display();
                });

        this.projectManager.getProjects()
            .addListener((ListChangeListener) change -> this.projectManager.save());

        projectManager.load();
        this.projectsTable.setItems(this.projectManager.getProjects());
        this.stage.show();
    }

    private void createComponents() {
        // Main Layout
        this.panelRoot = new VBox();
        Scene scene = new Scene(panelRoot, 800, 600);
        this.stage.setMinWidth(800);
        this.stage.setMinHeight(600);
        this.stage.setScene(scene);

        // Menu Bar
        this.customMenuBar = new CustomMenuBar();
        this.panelRoot.getChildren().add(this.customMenuBar);

        // Main Panel
        this.panelMain = new VBox();
        this.panelMain.setAlignment(Pos.CENTER);
        this.panelMain.setSpacing(10);
        this.panelMain.setPadding(new Insets(10, 20, 20, 20));
        // Header
        this.headerLabel = new Label();
        this.headerLabel.setTextAlignment(TextAlignment.CENTER);
        this.headerLabel.setWrapText(true);
        this.headerLabel.setMinHeight(45);
        this.headerLabel.setText("Choose a project:");
        this.headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        this.panelMain.getChildren().add(this.headerLabel);
        // Information Pane
        this.detailPanel = new DetailPanel();
        this.panelMain.getChildren().add(this.detailPanel);
        // Projects Listing
        this.projectsTable = new ProjectsTable();
        this.panelMain.getChildren().add(this.projectsTable);
        // Buttons to manipulate list
        this.buttonsPanel = new ButtonsPanel();
        this.panelMain.getChildren().add(this.buttonsPanel);
        // Add to root
        this.panelRoot.getChildren().add(this.panelMain);
    }
}
