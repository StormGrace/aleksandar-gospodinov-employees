package bg.aleksandar.team_longest_period;
import java.util.Date;

public class Employee {
    private int employeeID;
    private int daysWorked;
    private Date from, to;
    
    Employee(int employeeID, int daysWorked, Date from, Date to) {
	this.employeeID = employeeID;
	this.daysWorked = daysWorked;
	this.from = from;
	this.to = to;
    }

    public int getid() {
	return this.employeeID;
    }

    public int getdaysworked() {
	return this.daysWorked;
    }

    public Date getdatefrom() {
	return this.from;
    }

    public Date getdateto() {
	return this.to;
    }

    public boolean equals(Object o) {
	Employee project = (Employee) o;

	if (this.employeeID == project.employeeID) {
	    return true;
	} else {
	    return false;
	}
    }
}
