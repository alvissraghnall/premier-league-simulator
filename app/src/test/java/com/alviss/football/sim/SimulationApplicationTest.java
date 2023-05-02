package com.alviss.football.sim;

import com.alviss.football.fixtures.FixturesGeneratorTest;
import com.alviss.football.fixtures.Match;
import com.alviss.football.fixtures.Team;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class SimulationApplicationTest {

    private Team[] getTeams () throws IOException {
        Team[] teams = new FixturesGeneratorTest().getTeams();
        return teams;
    }

    public List<List<Result>> getResults () throws IOException {
        List<List<Result>> results = SimulationApplication.simulate(getTeams());
        return results;
    }

    @Test
    public void testSimulationResult () throws IOException {

        ArrayList<Result> matchesResults = new ArrayList<>();

        for (List<Result> matchRound : getResults()) {
//            System.out.println(matchRound.size());
            matchesResults.addAll(matchRound);
        }

        System.out.println(getResults().size());
        assertEquals(380, matchesResults.size());
    }

    @Test
    public void testNumberOfTeamResults () throws IOException {
        // Check that each team plays 38 matches
        for (Team team : getTeams()) {
            int count = 0;
            for (List<Result> matchRound : getResults()) {
                for (Result rs : matchRound) {
//                    System.out.println(rs.getMatch().getHomeTeam().getName() + " " + rs.getMatch().getAwayTeam().getName() + team.getName());
                    if (rs.getMatch().getHomeTeam().equals(team) || rs.getMatch().getAwayTeam().equals(team)) {
                        count++;
                    } else {
//                        System.out.println(rs.getMatch().getHomeTeam().getName() + "vs" + rs.getMatch().getAwayTeam().getName());
                    }
                }
            }
            System.out.println("Number of results by team " + team.getName() + ": " + count);
            assertEquals(38, count);
        }
    }

    @Test
    public void testTeamResultsAgainstEachOtherTeam () throws IOException {

        Team[] teams = getTeams();
        // Check that each team plays every other team twice
        for (int i = 0; i < teams.length; i++) {
            Team team1 = teams[i];
            for (int j = i + 1; j < teams.length; j++) {
                Team team2 = teams[j];
                int count = 0;
                for (List<Result> matchRound : getResults()) {
                    for (Result mtc : matchRound) {
//                        System.out.println(mtc.getMatch().getHomeTeam().getName());
                        if ((mtc.getMatch().getHomeTeam().equals(team1) && mtc.getMatch().getAwayTeam().equals(team2)) || (mtc.getMatch().getHomeTeam().equals(team2) && mtc.getMatch().getAwayTeam().equals(team1))) {
                            count++;
                        }
                    }
                }
                System.out.println("Results generated between " + team1.getName() + " and " + team2.getName() + ": " + count);
                assertEquals(2, count);
            }
        }
    }
}
