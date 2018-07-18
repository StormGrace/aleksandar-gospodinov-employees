package bg.aleksandar.team_longest_period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;

public class LongestPeriodComparator implements Comparator<Entry<Project, ArrayList<ArrayList<Employee>>>> {

    @Override
    public int compare(Entry<Project, ArrayList<ArrayList<Employee>>> team1,
	    	       Entry<Project, ArrayList<ArrayList<Employee>>> team2) {

	int team1worktime = team1.getValue().get(0).get(0).getdaysworked() 
			  + team1.getValue().get(0).get(1).getdaysworked();

	int team2worktime = team2.getValue().get(0).get(0).getdaysworked() 
			  + team2.getValue().get(0).get(1).getdaysworked();

	if (team2worktime < team1worktime) {
	    return -1;
	} else if (team2worktime > team1worktime) {
	    return 1;
	} else {
	    return 0;
	}
    }
}
