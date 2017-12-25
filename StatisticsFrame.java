import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.collections.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class StatisticsFrame {
    private Stage stage;

    private StatisticsManager stats;

    private Label numDeptLabel;
    private Label numLabel;
    private Label finishedLabel;
    private Label ongoingLabel;
    private Label futureLabel;
    private Label pmabLabel;
    private Label amsnLabel;
    private Label enLabel;
    private Label saLabel;
    private Label ictLabel;
    private Label weoLabel;
    private Label mstLabel;
    private Label fstLabel;
    private Label avgLabel;
    private Label budgetLabel;
    private Label incomeLabel;
    private Label profitLabel;
    private Label budgetAvgLabel;
    private Label incomeAvgLabel;
    private Label profitAvgLabel;
    private Label oldestLabel;
    private Label newestLabel;
    private Label longestNowProject;
    private Label longestOverallProject;
    private Label numPeopleLabel;
    private Label numMostPeopleLabel;
    private Label numAvgPeopleLabel;
    private Label progressLabel;

    private Button okButton;

    private NumberFormat nf;
    private DecimalFormat df;

    public StatisticsFrame(StatisticsManager statisticsManager) {
	this.stats = statisticsManager;

	this.nf = NumberFormat.getInstance();
	this.df = new DecimalFormat("#,##0.00");

	this.stage = new Stage();
	this.stage.initModality(Modality.APPLICATION_MODAL);
	this.stage.setTitle("Statistics");
	createComponents();

	this.okButton.setOnAction(event -> close());
    }

    private void createComponents() {
	VBox vbox = new VBox();
	GridPane panel = new GridPane();
	Scene scene = new Scene(vbox);
	this.stage.setResizable(false);
	this.stage.setScene(scene);

	vbox.setAlignment(Pos.CENTER);
	vbox.setSpacing(25);
	vbox.setPadding(new Insets(35, 35, 25, 35));
	panel.setAlignment(Pos.CENTER);
	panel.setHgap(30);
	panel.setVgap(12);

	this.numDeptLabel = new Label();
	this.numLabel = new Label();
	this.finishedLabel = new Label();
	this.ongoingLabel = new Label();
	this.futureLabel = new Label();
	this.pmabLabel = new Label();
	this.amsnLabel = new Label();
	this.enLabel = new Label();
	this.saLabel = new Label();
	this.ictLabel = new Label();
	this.weoLabel = new Label();
	this.mstLabel = new Label();
	this.fstLabel = new Label();
	this.avgLabel = new Label();
	this.budgetLabel = new Label();
	this.incomeLabel = new Label();
	this.profitLabel = new Label();
	this.budgetAvgLabel = new Label();
	this.incomeAvgLabel = new Label();
	this.profitAvgLabel = new Label();
	this.oldestLabel = new Label();
	this.newestLabel = new Label();
	this.longestNowProject = new Label();
	this.longestOverallProject = new Label();
	this.numPeopleLabel = new Label();
	this.numMostPeopleLabel = new Label();
	this.numAvgPeopleLabel = new Label();
	this.progressLabel = new Label();

	panel.addColumn(0,
			this.numDeptLabel,
			this.numLabel,
			this.finishedLabel,
			this.ongoingLabel,
			this.futureLabel,
			this.pmabLabel,
			this.amsnLabel,
			this.enLabel,
			this.saLabel,
			this.ictLabel,
			this.weoLabel,
			this.mstLabel,
			this.fstLabel,
			this.avgLabel);
	panel.addColumn(1,
			this.budgetLabel,
			this.incomeLabel,
			this.profitLabel,
			this.budgetAvgLabel,
			this.incomeAvgLabel,
			this.profitAvgLabel,
			this.oldestLabel,
			this.newestLabel,
			this.longestNowProject,
			this.longestOverallProject,
			this.numPeopleLabel,
			this.numMostPeopleLabel,
			this.numAvgPeopleLabel,
			this.progressLabel);
	vbox.getChildren().add(panel);

	this.okButton = new Button("OK");
	this.okButton.setDefaultButton(true);
	vbox.getChildren().add(this.okButton);
    }

    private void update() {
	this.stats.update();

	this.numDeptLabel.setText("Number of depts.: " + 6);
	this.numLabel.setText("Number of projects: " + this.stats.numProjects);
	this.finishedLabel.setText("Finished projects: " + this.stats.numFinishedProjects);
	this.ongoingLabel.setText("Ongoing projects: " + this.stats.numOngoingProjects);
	this.futureLabel.setText("Future projects: " + this.stats.numFutureProjects);
	this.pmabLabel.setText("PMAB projects: " + this.stats.numPMABProjects);
	this.amsnLabel.setText("AMSN projects: " + this.stats.numAMSNProjects);
	this.enLabel.setText("EN projects: " + this.stats.numENProjects);
	this.saLabel.setText("SA projects: " + this.stats.numSAProjects);
	this.ictLabel.setText("ICT projects: " + this.stats.numICTProjects);
	this.weoLabel.setText("WEO projects: " + this.stats.numWEOProjects);
	this.mstLabel.setText("MST projects: " + this.stats.numMSTProjects);
	this.fstLabel.setText("FST projects: " + this.stats.numFSTProjects);
	this.avgLabel.setText("Average per dept.: " + this.df.format(this.stats.numAverageProjects));
	this.budgetLabel.setText("Total budget: " + this.nf.format(this.stats.totalBudget).toString() + " $/month");
	this.incomeLabel.setText("Total income: " + this.nf.format(this.stats.totalIncome).toString() + " $/month");
	this.profitLabel.setText("Total profit: " + this.nf.format(this.stats.totalProfit).toString() + " $/month");
	this.budgetAvgLabel.setText("Average budget: " + this.df.format(this.stats.averageBudget) + " $/month");
	this.incomeAvgLabel.setText("Average income: " + this.df.format(this.stats.averageIncome) + " $/month");
	this.profitAvgLabel.setText("Average profit: " + this.df.format(this.stats.averageProfit) + " $/month");
	this.oldestLabel.setText("Oldest project: " + (this.stats.oldestProject != null ? this.stats.oldestProject.toString() : ""));
	this.newestLabel.setText("Newest project: " + (this.stats.newestProject != null ? this.stats.newestProject.toString() : ""));
	this.longestNowProject.setText("Longest project from now: " + this.stats.longestProjectNow + " days");
	this.longestOverallProject.setText("Longest project overall: " + this.stats.longestProjectOverall + " days");
	this.numPeopleLabel.setText("# of researchers: " + this.stats.numPeople);
	this.numMostPeopleLabel.setText("Largest team: " + this.stats.numMostPeople);
	this.numAvgPeopleLabel.setText("Average team size: " + df.format(this.stats.numAveragePeople));
	this.progressLabel.setText("Average progress: " + df.format(this.stats.averageProgress) + "%");

	this.finishedLabel.setTextFill(Coloring.finishedColor());
	this.pmabLabel.setTextFill(Coloring.departmentColor("PMAB"));
	this.amsnLabel.setTextFill(Coloring.departmentColor("AMSN"));
	this.enLabel.setTextFill(Coloring.departmentColor("EN"));
	this.saLabel.setTextFill(Coloring.departmentColor("SA"));
	this.ictLabel.setTextFill(Coloring.departmentColor("ICT"));
	this.weoLabel.setTextFill(Coloring.departmentColor("WEO"));
	this.mstLabel.setTextFill(Coloring.departmentColor("MST"));
	this.fstLabel.setTextFill(Coloring.departmentColor("FST"));
	this.budgetLabel.setTextFill(Coloring.moneyColor(this.stats.totalBudget));
	this.incomeLabel.setTextFill(Coloring.moneyColor(this.stats.totalIncome));
	this.profitLabel.setTextFill(Coloring.binaryColor(this.stats.totalProfit));
	this.budgetAvgLabel.setTextFill(Coloring.moneyColor(this.stats.averageBudget));
	this.incomeAvgLabel.setTextFill(Coloring.moneyColor(this.stats.averageIncome));
	this.profitAvgLabel.setTextFill(Coloring.binaryColor(this.stats.averageProfit));
    }

    public void show() {
	update();
	this.stage.showAndWait();
    }

    public void close() {
	this.stage.close();
    }
}
