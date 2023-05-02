package com.alviss.football.fixtures;
import org.junit.platform.commons.annotation.Testable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

@Testable
public class FixturesGeneratorTest {


    Team[] teams = getTeams();

    List<List<Match>> fixtures = new FixturesGenerator().generate(teams);

    public FixturesGeneratorTest() throws IOException {
    }

    public Team[] getTeams () throws IOException {

        // Team[] teams = new Team[] {new Team()};
        return new ObjectMapper().readValue(getFileFromResourceAsStream(), Team[].class);
    }

    private InputStream getFileFromResourceAsStream() {
         ClassLoader classLoader = getClass().getClassLoader();
         InputStream inputStream = classLoader.getResourceAsStream("json/teams.json");
        // System.out.println(FixturesApplication.class.);

         if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + "json/teams.json");
         } else {
            return inputStream;
         }
    }

    @Test
    public void testTeamFixturesAgainstSelf () {

        // Check that no team plays against itself
        for (List<Match> matchRound : fixtures) {
            for (Match match : matchRound) {
                assertNotEquals("A team is playing against itself in match " + match,
                        match.getHomeTeam(), match.getAwayTeam());
            }
        }
    }

    @Test
    public void testTeamFixturesAgainstEachOtherTeam () {


        // Check that each team plays every other team twice
        for (int i = 0; i < teams.length; i++) {
            Team team1 = teams[i];
            for (int j = i + 1; j < teams.length; j++) {
                Team team2 = teams[j];
                int count = 0;
                for (List<Match> matchRound : fixtures) {
                    for (Match mtc : matchRound) {
                        if ((mtc.getHomeTeam() == team1 && mtc.getAwayTeam() == team2) || (mtc.getHomeTeam() == team2 && mtc.getAwayTeam() == team1)) {
                            count++;
                        }
                    }
                }
                System.out.println("Matches played between " + team1.getName() + " and " + team2.getName() + ": " + count);
                assertEquals(2, count);
            }
        }
    }

    @Test
    public void testNumberOfTeamFixtures () {
        // Check that each team plays 38 matches
        for (Team team : teams) {
            int count = 0;
            for (List<Match> matchRound : fixtures) {
                for (Match mtc : matchRound) {
                    if (mtc.getHomeTeam() == team || mtc.getAwayTeam() == team) {
                        count++;
                    }
                }
            }
            System.out.println("Number of matches played by team " + team.getName() + ": " + count);
            assertEquals(38, count);
        }
    }

    @Test
    public void testGenerateFixtures() throws IOException {
        Team[] teams = getTeams();
        
        List<List<Match>> fixtures = new FixturesGenerator().generate(teams);
        
        // Check that the correct number of fixtures are generated
//        assertEquals(190, fixtures.size());

        ArrayList<Match> matches = new ArrayList<>();

        for (List<Match> matchRound : fixtures) {
            matches.addAll(matchRound);
        }

        System.out.println(Arrays.deepToString(matches.toArray()));
        assertEquals(380, matches.size());
        
        // Check that each team plays 38 matches
//        for (String team : teams) {
//            int count = 0;
//            for (String fixture : fixtures) {
//                if (fixture.contains(team)) {
//                    count++;
//                }
//            }
//            assertEquals(38, count);
//        }

        // Check that each team plays 38 matches


        // Check that each team plays every other team twice
//        for (int i = 0; i < teams.size(); i++) {
//            String team1 = teams.get(i);
//            for (int j = i + 1; j < teams.size(); j++) {
//                String team2 = teams.get(j);
//                int count = 0;
//                for (String fixture : fixtures) {
//                    if (fixture.contains(team1) && fixture.contains(team2)) {
//                        count++;
//                    }
//                }
//                assertEquals(2, count);
//            }
//        }

    }

}
