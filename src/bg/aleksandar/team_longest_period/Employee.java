package bg.aleksandar.team_longest_period;
import java.util.Date;

public class Employee extends Project{
    private int employeeID;
    private Date from, to;
    
    Employee(int projectID, int employeeID, int daysWorked, Date from, Date to) {
	super(projectID);
	this.employeeID = employeeID;
	this.from = from;
	this.to = to;
    }

    public int getEmployeeId() {
	return this.employeeID;
    }

    public Date getDateWorkedFrom() {
	return this.from;
    }

    public Date getDateWorkedTo() {
	return this.to;
    }

    public boolean equals(Object o) {
	Employee employee2 = (Employee) o;

	if (this.employeeID == employee2.employeeID) {
	    return true;
	} else {
	    return false;
	}
    }

}
