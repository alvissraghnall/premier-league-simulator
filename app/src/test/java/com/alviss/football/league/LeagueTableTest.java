package com.alviss.football.league;

import com.alviss.football.fixtures.Team;
import com.alviss.football.sim.SimulationApplicationTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Testable
public class LeagueTableTest {

    @Test
    public void testLeagueTable () throws IOException {
        Map<Team, LeagueData> teamLeagueDataMap = new LeagueTable(new SimulationApplicationTest().getResults()).generate();
        List<Team> sortedTeams = new ArrayList<>(teamLeagueDataMap.keySet());
        sortedTeams.sort((team1, team2) -> {
            LeagueData team1Data = teamLeagueDataMap.get(team1);
            LeagueData team2Data = teamLeagueDataMap.get(team2);
            int pointsDiff = team2Data.getPoints() - team1Data.getPoints();
            if (pointsDiff != 0) {
                return pointsDiff;
            } else {
                int goalDiff = team2Data.getGoalsDifference() - team1Data.getGoalsDifference();
                if (goalDiff != 0) {
                    return goalDiff;
                } else {
                    return team2Data.getGoalsFor() - team1Data.getGoalsFor();
                }
            }
        });

        StringBuilder sb = new StringBuilder();

        System.out.println("League Table:");
        System.out.printf("%-20s        %-6s %-6s %-6s %-6s %-6s %-6s %-6s %-6s\n", "Team", "Played", "Wins", "Draws", "Losses", "GF", "GA", "GD", "Points", "Form");
        for (Team team : sortedTeams) {
            LeagueData teamData = teamLeagueDataMap.get(team);
            for (Form f : teamData.getForm()) {
                sb.append(f.toString());
            }
            System.out.printf("%-20s        %-6d %-6d %-6d %-6d %-6d %-6d %-6d %-6d %-6s\n", team.getName(), teamData.getPlayed(), teamData.getWins(), teamData.getDraws(), teamData.getLosses(), teamData.getGoalsFor(), teamData.getGoalsAgainst(), teamData.getGoalsDifference(), teamData.getPoints(), sb.toString());
            sb.delete(0, sb.length());

//            System.out.println("Form: " + sb.toString());
        }
    }
}
