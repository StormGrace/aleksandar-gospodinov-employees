package bg.aleksandar.team_longest_period;

import java.util.ArrayList;
import java.util.Comparator;

public class SumWorkTimeComparator implements Comparator<ArrayList<Employee>>{

    @Override
    public int compare(ArrayList<Employee> team1, ArrayList<Employee> team2) {
	 int team1worktime = team1.get(0).getdaysworked() + team1.get(1).getdaysworked();
	 int team2worktime = team2.get(0).getdaysworked() + team2.get(1).getdaysworked();
	 
	 if(team2worktime < team1worktime) {
	     return -1;
	 }else if(team2worktime > team1worktime) {
	     return 1;
	 }else {
	     return 0;
	 }
	 
    }

}
