package bg.aleksandar.team_longest_period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;

public class LongestPeriodComparator implements Comparator<Entry<Project, ArrayList<Team>>> {

    @Override
    public int compare(Entry<Project, ArrayList<Team>> firstTeam,
	    	       Entry<Project, ArrayList<Team>> secondTeam) {

	int team1TotalWorkTime = firstTeam.getValue().get(0).getDaysWorked();
	int team2TotalWorkTime = secondTeam.getValue().get(0).getDaysWorked();

	if (team2TotalWorkTime < team1TotalWorkTime) {
	    return -1;
	} else if (team2TotalWorkTime > team1TotalWorkTime) {
	    return 1;
	} else {
	    return 0;
	}
    }
}
