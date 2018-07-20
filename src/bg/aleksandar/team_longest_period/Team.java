package bg.aleksandar.team_longest_period;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Team {
    private Employee employee1;
    private Employee employee2;
    
    private ArrayList<Date[]> workPeriods = new ArrayList<>();
    
    private int daysWorked; 
    
    public final boolean isValidTeam;
    
    public Team(Employee employee1, Employee employee2) {
	    
	    this.isValidTeam = isTeam(employee1, employee2);
	    
	    if(isValidTeam == true) {
		this.employee1 = employee1;
		this.employee2 = employee2;
	    
		Date[] workPeriod = getTeamWorkPeriod(employee1, employee2);
		
		workPeriods.add(workPeriod);
		
		this.daysWorked = daysBetween(workPeriod[0], workPeriod[1]);
	    }
    }
    
    public String getWorkPeriodsAsString() {
	ArrayList<Date[]> workPeriodsDates = this.getWorkPeriodsAsDates();
	SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
	String workPeriods = "";
	
	for(Date[] workPeriod : workPeriodsDates) {
	    workPeriods += "( " + sdf.format(workPeriod[0]) + " -- " + sdf.format(workPeriod[1]) + " )";
	}
	
	return workPeriods;
    }
    
    public ArrayList<Date[]> getWorkPeriodsAsDates() {
	return this.workPeriods;
    }
    
    public int getDaysWorked() {
	return this.daysWorked;
    }
    
    public int getProjectId() {
	return this.employee1.getProjectId();
    }
    
    public void addDaysWorked(Team team) {
	this.daysWorked += team.getDaysWorked();
	this.workPeriods.addAll(team.getWorkPeriodsAsDates());
    }
    
    //Returns the day difference between 2 Dates [Not the best way of doing it].
    private int daysBetween(Date from, Date to) {
	long mils = to.getTime() - from.getTime();
	long days =  mils / (1000 * 60 * 60 * 24);	
	return (int)days;
    }
    
    //Outputs as a String the exact period 2 Employees worked together.
    private Date[] getTeamWorkPeriod(Employee emp1, Employee emp2) {
	Date periodFrom = null, periodTo = null;
	Date from1, to1, from2, to2;
	
	from1 = emp1.getDateWorkedFrom();
	to1 = emp1.getDateWorkedTo();
	
	from2 = emp2.getDateWorkedFrom();
	to2 = emp2.getDateWorkedTo();
	
	if(!from1.after(from2) && !to1.before(from2) && !to1.after(to2)){
	    
	    periodFrom = from2;
	    periodTo = to1;
	    
	}else if(!from1.after(from2) && !to1.before(from2) && !to1.before(to2)){
	    
	    periodFrom = from2;
	    periodTo = to2;
	    
	}else if(!from1.before(from2) && !to1.before(to2)){
	    
	    periodFrom = from1;
	    periodTo = to2;
	    
	}
	else if(!from1.before(from2) && !to1.after(to2)) {
	    
	    periodFrom = from1;
	    periodTo = to1;
	    
	}

	return new Date[] {periodFrom, periodTo};
    }
    
    //Compares the Dates between two Employees to check if they worked together.
    private boolean isTeam(Employee emp1, Employee emp2) {
	Date from1, to1, from2, to2;
	
	from1 = emp1.getDateWorkedFrom();
	to1 = emp1.getDateWorkedTo();
	
	from2 = emp2.getDateWorkedFrom();
	to2 = emp2.getDateWorkedTo();

    	if((!from1.before(from2) && (!to1.after(to2) || (!from1.after(to2) && !to1.before(to2))) ||
    	   !from1.after(from2) && !to1.before(from2)) && (emp1.getEmployeeId() != emp2.getEmployeeId())) {
    	    return true;
    	}else {
    	    return false;
    	}
    }
    
    public boolean equals(Object o) {
	Team team = (Team) o;

	if ((this.employee1.equals(team.employee1) || this.employee1.equals(team.employee2)) && 
	    (this.employee2.equals(team.employee2) || this.employee2.equals(team.employee1)) &&
	     this.employee1.getProjectId() == team.employee2.getProjectId()) {
	    return true;
	} else {
	    return false;
	}
    }
    
    public String toString() {
	return (" Team: Employee[" 
		+ this.employee1.getEmployeeId() + "] and Employee[" + this.employee2.getEmployeeId() + "]," 
		+ " collab. Project [" + this.employee1.getProjectId() + "], collab. Period [" + this.getWorkPeriodsAsString() + "].");
    }
}
