import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Stream;
import java.time.LocalDate;

public class StatisticsManager {
    ProjectManager projectManager;
    List<Project> projects;

    private Set<String> people;

    // TODO: change public to private and use getters
    public long numProjects;
    public long numFinishedProjects;
    public long numOngoingProjects;
    public long numFutureProjects;
    public long numPMABProjects;
    public long numAMSNProjects;
    public long numENProjects;
    public long numSAProjects;
    public long numICTProjects;
    public long numWEOProjects;
    public long numMSTProjects;
    public long numFSTProjects;
    public double numAverageProjects;
    public long totalBudget;
    public long totalIncome;
    public long totalProfit;
    public double averageBudget;
    public double averageIncome;
    public double averageProfit;
    public LocalDate oldestProject;
    public LocalDate newestProject;
    public long longestProjectNow;
    public long longestProjectOverall;
    public long numPeople;
    public long numMostPeople;
    public double numAveragePeople;
    public double averageProgress;

    public StatisticsManager(ProjectManager projectManager) {
	this.projectManager = projectManager;
	this.projects = projectManager.getProjects();
	this.people = new HashSet<String>();
    }

    private Stream<Project> getStream() {
	return this.projects.stream();
    }

    public void update() {
	this.people.clear();
	getStream().forEach(project -> this.people.addAll(Arrays.asList(project.getTeamMembers())));

	numProjects = getStream().count();
	numFinishedProjects = getStream().filter(project -> project.getProgress() == 100).count();
	numOngoingProjects = getStream().filter(project -> project.getStartDate().isBefore(LocalDate.now()) && project.getProgress() != 100).count();
	numFutureProjects = getStream().filter(project -> project.getStartDate().isAfter(LocalDate.now())).count();
	numPMABProjects = getStream().filter(project -> project.getDepartment().equals("PMAB")).count();
	numAMSNProjects = getStream().filter(project -> project.getDepartment().equals("AMSN")).count();
	numENProjects = getStream().filter(project -> project.getDepartment().equals("EN")).count();
	numSAProjects = getStream().filter(project -> project.getDepartment().equals("SA")).count();
	numICTProjects = getStream().filter(project -> project.getDepartment().equals("ICT")).count();
	numWEOProjects = getStream().filter(project -> project.getDepartment().equals("WEO")).count();
	numMSTProjects = getStream().filter(project -> project.getDepartment().equals("MST")).count();
	numFSTProjects = getStream().filter(project -> project.getDepartment().equals("FST")).count();
	numAverageProjects = getStream().count() / 8.0;
	totalBudget = getStream().mapToInt(project -> project.getBudget()).sum();
	totalIncome = getStream().mapToInt(project -> project.getIncome()).sum();
	totalProfit = totalIncome - totalBudget;
	averageBudget = getStream().mapToInt(project -> project.getBudget()).average().orElse(0.0);
	averageIncome = getStream().mapToInt(project -> project.getIncome()).average().orElse(0.0);
	averageProfit = averageIncome - averageBudget;
	oldestProject = getStream().map(project -> project.getStartDate())
	    .filter(date -> date.isBefore(LocalDate.now()))
	    .sorted((d1, d2) -> d1.isAfter(d2) ? 1 : -1)
	    .findFirst().orElse(null);
	newestProject = getStream().map(project -> project.getStartDate())
	    .filter(date -> date.isBefore(LocalDate.now()))
	    .sorted((d1, d2) -> d2.isAfter(d1) ? 1 : -1)
	    .findFirst().orElse(null);
	longestProjectNow = getStream().filter(project -> project.getStartDate().isBefore(LocalDate.now()))
	    .map(project -> project.getEndDateApprox().toEpochDay() - LocalDate.now().toEpochDay())
	    .sorted((a, b) -> (int) (b - a))
	    .findFirst().orElse(0L);
	longestProjectOverall = getStream()
	    .map(project -> project.getEndDateApprox().toEpochDay() - LocalDate.now().toEpochDay())
	    .sorted((a, b) -> (int) (b - a))
	    .findFirst().orElse(0L);
	numPeople = (long) this.people.size();
	numMostPeople = getStream().map(project -> project.getNumMembers())
	    .sorted((a, b) -> b - a)
	    .findFirst().orElse(0);
	numAveragePeople = getStream().mapToInt(project -> project.getNumMembers()).average().orElse(0.0);
	averageProgress = getStream().mapToInt(project -> project.getProgress()).average().orElse(0.0);
    }
}
