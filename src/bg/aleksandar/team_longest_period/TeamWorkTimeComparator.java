package bg.aleksandar.team_longest_period;

import java.util.Comparator;

public class TeamWorkTimeComparator implements Comparator<Team>{

    @Override
    public int compare(Team firstTeam, Team secondTeam) {
	 int team1TotalWorkTime = firstTeam.getDaysWorked();
	 int team2TotalWorkTime = secondTeam.getDaysWorked(); 
	 
	 if(team2TotalWorkTime < team1TotalWorkTime) {
	     return -1;
	 }else if(team2TotalWorkTime > team1TotalWorkTime) {
	     return 1;
	 }else {
	     return 0;
	 }
	 
    }

}
