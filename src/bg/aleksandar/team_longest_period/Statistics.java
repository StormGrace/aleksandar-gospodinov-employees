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
			
		    Project project = new Project(projectID);
		    Employee employee = new Employee(project.getProjectId(), employeeID, dayDifference, from, to);
			
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
	    System.out.println("\nThe File-Reading process has stopped!\n");
	}
    }
    

    //Creates the Teams and Prints the Team with the highest common work period.
    public void printByHighestWorktime() {
	try {
	 Map<Project, ArrayList<Team>> teams = generateTeams();
	 
	 //Sort each Team per Project.
	 for(ArrayList<Team> team : teams.values()) {
	     Collections.sort(team, new TeamWorkTimeComparator());
	 }
	 
	 //Sort each Project based on the first Team entry.
	 List<Entry<Project, ArrayList<Team>>> teamSorted = new LinkedList<>(teams.entrySet());
	 Collections.sort(teamSorted, new LongestPeriodComparator());
	 
	 Team teamHighestWorkTime = teamSorted.get(0).getValue().get(0);
	 
	 System.out.println("The TEAM/s that collaborated the longest Time is/are formed by the given: ");
	 System.out.println(teamHighestWorkTime.toString());
	 
	 boolean anyDuplicateWorkTime = false;
	 
	 //Check for Teams with the same worktime performance [If Any print them].
	 for(Entry<Project, ArrayList<Team>> team : teams.entrySet()) {
	     if((team.getValue().get(0).getDaysWorked() == teamHighestWorkTime.getDaysWorked()) && 
	         team.getValue().get(0).equals(teamHighestWorkTime) == false) {
		 
		 if(anyDuplicateWorkTime == false) {
		     System.out.println("\nWith the same performance, collaborated:");
		     anyDuplicateWorkTime = true;
		 }
		 
		 System.out.println(team.getValue().get(0).toString());
	     }
	 }
	}catch(Exception e) {
	    System.out.println("No Teams has been found!");
	}
    }
    
    //Creates the Teams by a Pair of two Employees who worked together.
    public Map<Project, ArrayList<Team>> generateTeams() throws Exception{
	 Map<Project, ArrayList<Team>> teams = new HashMap<>();
	 boolean anyTeamsExist = false;
	 
	//Mother of Improvisation, Part-II
	for(Entry<Project, ArrayList<Employee>> pair : database.entrySet()){
	    Project project = pair.getKey();
	
	    for(int j = 0; j < pair.getValue().size(); j++) {
		Employee employee1 = pair.getValue().get(j);
		
		for(int i = j + 1; i < pair.getValue().size(); i++) {
		    Employee employee2 = pair.getValue().get(i);
		    
		    Team teamEmployees = new Team(employee1, employee2);	
			
		    if(teamEmployees.isValidTeam) {
			anyTeamsExist = true;
			
			if(teams.containsKey(project)){
			    
			    if(teams.get(project).contains(teamEmployees)) {
				teams.get(project).get(teams.get(project)
						       .indexOf(teamEmployees))
						  .addDaysWorked(teamEmployees);
			    }else {
				teams.get(project).add(teamEmployees);
			    }
			    
			}else {
			    teams.put(project, new ArrayList<>(Arrays.asList(teamEmployees)));
			}	   
		    }
		}
	    }
	}
	
	if(anyTeamsExist == false) {throw new Exception();} else {return teams;}
    }
    
    public static void main(String[] Args) {
	Statistics statistic = new Statistics();
	
	statistic.readFromFile("employees.txt");
	statistic.printByHighestWorktime();
    }
}
