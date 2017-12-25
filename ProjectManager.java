import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Arrays;
import java.io.*;

public class ProjectManager {
    private ObservableList<Project> projects;

    public boolean saving = false;

    private static final String database = "projects.db";

    public ProjectManager() {
	this.projects = FXCollections.observableArrayList();
    }

    public void addProject(Project project) {
	this.projects.add(0, project);
    }

    public void setProject(int projectIndex, Project project) {
	this.projects.set(projectIndex, project);
    }

    public void delProject(Project project) {
	this.projects.remove(project);
    }

    public void loadProjects(Project[] projects) {
	this.projects.clear();
	this.projects.addAll(projects);
    }

    public ObservableList<Project> getProjects() {
	return this.projects;
    }

    public void load() {
	try {
	    ObjectInputStream oin = new ObjectInputStream(new FileInputStream(this.database));
	    Project[] copyProjects = (Project[]) oin.readObject();
	    loadProjects(copyProjects);
	    oin.close();
	} catch (IOException e) {
	    System.err.println("Warning: no valid data files found.");
	} catch (ClassNotFoundException e) {
	    System.err.println("Incompatible class from data.");
	    System.exit(1);
	}
    }

    public void save() {
	try {
	    ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(this.database));
	    Project[] copyProjects = projects.toArray(new Project[0]);
	    oout.writeObject(copyProjects);
	    oout.close();
	} catch (IOException e) {
	    System.err.println("Error saving data.");
	    System.exit(1);
	}
    }
}
