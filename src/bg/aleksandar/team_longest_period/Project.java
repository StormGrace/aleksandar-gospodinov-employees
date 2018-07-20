package bg.aleksandar.team_longest_period;

public class Project {
    private int projectID;
    
    Project(int projectID){
	this.projectID = projectID;
    }
    
    public int getProjectId() {
	return this.projectID;
    }

    public boolean equals(Object o) {
	Project project = (Project) o;

	if (this.projectID == project.projectID) {
	    return true;
	} else {
	    return false;
	}
    }
    
    public int hashCode() {
        return projectID;
    }
}
