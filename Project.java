import java.time.LocalDate;
import java.io.Serializable;

public class Project implements Serializable {

    private String name;
    private String department;
    private String[] teamMembers;
    private String teamLeader;
    private int budget;
    private int income;
    private int progress;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate modifiedDate;
    private String image;

    public Project(String name,
		   String department,
		   String teamLeader,
		   String[] teamMembers,
		   int budget,
		   int income,
		   int progress,
		   LocalDate startDate,
		   LocalDate endDate) {
	this.name = name;
	this.department = department;
	this.teamLeader = teamLeader;
	this.teamMembers = teamMembers;
	this.budget = budget;
	this.income = income;
	this.progress = progress;
	this.startDate = startDate;
	this.endDate = endDate;
	this.modifiedDate = LocalDate.now();
	this.image = ImageProvider.randomImage();
    }

    public String getName() {
	return this.name;
    }

    public String getDepartment() {
	return this.department;
    }

    public String getTeamLeader() {
	return this.teamLeader;
    }

    public String[] getTeamMembers() {
	return this.teamMembers;
    }

    public int getBudget() {
	return this.budget;
    }

    public int getIncome() {
	return this.income;
    }

    public int getProfit() {
	return this.income - this.budget;
    }

    public int getProgress() {
	return this.progress;
    }

    public LocalDate getStartDate() {
	return this.startDate;
    }

    public LocalDate getEndDate() {
	if (this.progress == 100)
	    return this.endDate;
	else
	    return null;
    }

    public LocalDate getEndDateApprox() {
	if (this.progress == 100)
	    return this.endDate;
	else if (LocalDate.now().isAfter(this.startDate))
	    return LocalDate.now().plusDays((LocalDate.now().toEpochDay() - this.startDate.toEpochDay()) * (100 - this.progress) / 100);
	else
	    return this.startDate.plusDays(72);
    }

    public String getImage() {
	return this.image;
    }

    // Formatted outputs
    public int getNumMembers() {
	return this.teamMembers.length;
    }

    public String getTeamMembersText() {
	return String.join(", ", this.teamMembers);
    }

    public String getStartDateText() {
	return this.startDate.toString();
    }

    public String getEndDateText() {
	if (this.progress == 100)
	    return this.endDate.toString();
	else
	    return this.getEndDateApprox().toString() + " (est.)";
    }
}
