

package com.alviss.football.fixtures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.platform.commons.annotation.Testable;
import java.util.List;

@Testable
public class FixturesGeneratorTest {

    @Test
    void testGenerateFixturesWithEvenNumberOfTeams() {
        Team[] teams = {
            new Team("Team A", "TA", 80, 75, 80, 85),
            new Team("Team B", "TB", 85, 80, 85, 90),
            new Team("Team C", "TC", 82, 78, 82, 88),
            new Team("Team D", "TD", 78, 74, 78, 82)
        };

        FixturesGenerator generator = new FixturesGenerator(teams);
        List<MatchDay> fixtures = generator.generate();

        int expectedRounds = (teams.length - 1) * 2;
        assertEquals(expectedRounds, fixtures.size());

        int expectedMatchesPerRound = teams.length / 2;
        for (MatchDay matchDay : fixtures) {
            assertEquals(expectedMatchesPerRound, matchDay.getMatches().size());
        }

        for (int i = 0; i < teams.length; i++) {
            for (int j = 0; j < teams.length; j++) {
                if (i != j) {
                    int homeMatches = countMatches(fixtures, teams[i], teams[j], true);
                    int awayMatches = countMatches(fixtures, teams[i], teams[j], false);
                    assertEquals(1, homeMatches, "Team " + teams[i].getName() + " should play home against " + teams[j].getName() + " once");
                    assertEquals(1, awayMatches, "Team " + teams[i].getName() + " should play away against " + teams[j].getName() + " once");
                }
            }
        }
    }

    @Test
    void testGenerateFixturesWithEmptyTeams() {
        Team[] teams = {};
        FixturesGenerator generator = new FixturesGenerator(teams);

        List<MatchDay> fixtures = generator.generate();

        assertEquals(0, fixtures.size());
    }

    @Test
    void testGenerateFixturesWithSingleTeam() {
        Team[] teams = {
            new Team("Team A", "TA", 80, 75, 80, 85)
        };

        FixturesGenerator generator = new FixturesGenerator(teams);
        List<MatchDay> fixtures = generator.generate();

        assertEquals(0, fixtures.size());
    }

    private int countMatches(List<MatchDay> fixtures, Team team1, Team team2, boolean isHome) {
        int count = 0;
        for (MatchDay matchDay : fixtures) {
            for (Match match : matchDay.getMatches()) {
                if (isHome) {
                    if (match.getHomeTeam().equals(team1) && match.getAwayTeam().equals(team2)) {
                        count++;
                    }
                } else {
                    if (match.getHomeTeam().equals(team2) && match.getAwayTeam().equals(team1)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
