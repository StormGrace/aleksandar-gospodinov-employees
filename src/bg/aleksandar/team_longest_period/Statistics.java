package bg.aleksandar.team_longest_period;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;

/* 18/07/2018
 * Assignment Name: "Team Longest Period".
 * Aleksandar Gospodinov, a_gospodinov97@abv.bg 
 */

public class Statistics {
    private Map<Project, ArrayList<Employee>> database = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    //Reads the Information from a File by Extracting it.
    public void readFromFile(String fileName) {
	File file = new File(fileName);
	 
	try {
	    Scanner sc = new Scanner(file);
	    
	    while(sc.hasNextLine()) {
		String[] data = sc.nextLine().replace("NULL", sdf.format(new Date()))
					     .split("(,[\\s]*)");
		int dayDifference = 0, projectID = 0, employeeID = 0;
		Date from = null, to = null;
		
		try {
		    projectID = Integer.parseInt(data[1]);
		    employeeID = Integer.parseInt(data[0]);
			
		    from = sdf.parse(data[2]);
		    to = sdf.parse(data[3]);
		    
		    dayDifference = daysBetween(from, to);
			
		    Project project = new Project(projectID);
		    Employee employee = new Employee(employeeID, dayDifference, from, to);
			
		    if(database.containsKey(project)) {	
			database.get(project).add(employee);
		    }else {    
			database.put(project, new ArrayList<Employee>(Arrays.asList(employee)));
		    }
		    
		}catch(Exception e) {
		    System.out.println("Error Reading Data!\nSkipping Entry...");
		}
	    }
	    
	    sc.close();    
	 
	}catch(FileNotFoundException e) {
	    System.out.println("Error! The Required File is either corrupted "
	    			+ "or doesn't exist!");
	}finally {
	    System.out.println("The File-Reading process has stopped!");
	}
    }
    
    //Creates the Teams and Prints the Team with the highest common work period.
    public void byHighestWorktime() {
	try {
	 Map<Project, ArrayList<ArrayList<Employee>>> teams = generateTeams();
	 
	 for(ArrayList<ArrayList<Employee>> team : teams.values()) {
		Collections.sort(team, new SumWorkTimeComparator());
	 }
 
	 List<Entry<Project, ArrayList<ArrayList<Employee>>>> teamSorted = new LinkedList<>(teams.entrySet());
	 Collections.sort(teamSorted, new LongestPeriodComparator());
	 
	 Project project = teamSorted.get(0).getKey();
	 
	 Employee employee1 = teamSorted.get(0).getValue().get(0).get(0);
	 Employee employee2 = teamSorted.get(0).getValue().get(0).get(1);
	 
	 String workPeriod = workPeriodToString(employee1, employee2);
	 
	 System.out.println("The TEAM that worked together the most is formed by: "
	 	+ "Employee[" + employee1.getid() + "] and Employee[" + employee2.getid() + "]"
	 	+ " on Project[" + project.getid() + "] for the period [" + workPeriod + "].");
	}catch(Exception e) {
	    System.out.println("No Teams has been found!");
	}
    }
    
    //Creates the Teams by a Pair of two Employees who worked together.
    public Map<Project, ArrayList<ArrayList<Employee>>> generateTeams() throws Exception{
	 Map<Project, ArrayList<ArrayList<Employee>>> teams = new HashMap<>();
	 boolean teamExists = false;
	 
	//Mother of Improvisation
	for(Entry<Project, ArrayList<Employee>> pair : database.entrySet()){
	    Project project = pair.getKey();
	
	    for(int j = 0; j < pair.getValue().size(); j++) {
		Employee employee1 = pair.getValue().get(j);
		
		for(int i = j + 1; i < pair.getValue().size(); i++) {
		    Employee employee2 = pair.getValue().get(i);
		    
		    if(isTeam(employee1, employee2)) {
			ArrayList<Employee> teamEmployees = new ArrayList<>(Arrays.asList(employee1, employee2));		
			teamExists = true;
			
			if(teams.containsKey(project)){
			    teams.get(project).add(teamEmployees);
			}else {
			    teams.put(project, new ArrayList<>(Arrays.asList(teamEmployees)));
			}	   
		    }
		}
	    }
	}
	
	if(teamExists == false) {throw new Exception();} else {return teams;}
    }
    
    //Outputs as a String the exact period 2 Employees worked together.
    private String workPeriodToString(Employee emp1, Employee emp2) {
	String date = "";
	Date from1, to1, from2, to2;
	
	from1 = emp1.getdatefrom();
	to1 = emp1.getdateto();
	from2 = emp2.getdatefrom();
	to2 = emp2.getdateto();

	if(!from1.after(emp2.getdatefrom()) && !to1.before(from2) && !to1.after(to2)){
	    
	    date = sdf.format(from2);
	    date += " - " + (sdf.format(to1));
	    
	}else if(!from1.after(from2) && !to1.before(from2) &&
	         !to1.before(to2)){
	    
	    date = sdf.format(from1);
	    date += " - " + (sdf.format(to2));
	    
	}else if(!from1.before(from2) && !to1.before(to2)){
	    
	    date = sdf.format(from1);
	    date += " - " + (sdf.format(to2));
	    
	}
	else if(!from1.before(from2) && !to1.after(to2)) {
	    
	    date = sdf.format(from1);
	    date += " - " + (sdf.format(to1));
	    
	}
	
	return date;
    }
    
    //Compares the Dates between two Employees to check if they worked together.
    private boolean isTeam(Employee emp1, Employee emp2) {
	Date from1, to1, from2, to2;
	
	from1 = emp1.getdatefrom();
	to1 = emp1.getdateto();
	from2 = emp2.getdatefrom();
	to2 = emp2.getdateto();

    	if(!from1.before(from2) && (!to1.after(to2) || (!from1.after(to2) && !to1.before(to2))) ||
    	   !from1.after(from2) && !to1.before(from2)) {
    	    return true;
    	}else {
    	    return false;
    	}
    }
    
    //Returns the day difference between 2 Dates [Not the best way of doing it].
    private int daysBetween(Date from, Date to) {
	long mils = to.getTime() - from.getTime();
	long days =  mils / (1000 * 60 * 60 * 24);	
	return (int)days;
    }
    
    public static void main(String[] Args) {
	Statistics statistic = new Statistics();
	
	statistic.readFromFile("data.txt");
	statistic.byHighestWorktime();
    }
}
