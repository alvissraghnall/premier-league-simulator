package com.alviss.football.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.alviss.football.fixtures.FixturesGenerator;
import com.alviss.football.fixtures.Match;
import com.alviss.football.fixtures.MatchDay;
import com.alviss.football.fixtures.Team;
import com.alviss.football.league.LeagueTable;

class SimulationApplicationTest {
  private Team[] teams;
  @Mock
  private FixturesGenerator fixturesGenerator;
  private SimulationApplication simulationApp;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    teams = new Team[] {
        new Team("Team A", "TMA", 85, 80, 75, 90),
        new Team("Team B", "TMB", 80, 85, 70, 85),
        new Team("Team C", "TMC", 75, 70, 85, 80),
        new Team("Team D", "TMD", 70, 75, 80, 75)
    };

    List<MatchDay> fixtures = new ArrayList<>();

    List<Match> matchDay1 = Arrays.asList(
        new Match(teams[0], teams[1]),
        new Match(teams[2], teams[3]));
    List<Match> matchDay2 = Arrays.asList(
        new Match(teams[0], teams[2]),
        new Match(teams[1], teams[3]));
    List<Match> matchDay3 = Arrays.asList(
        new Match(teams[0], teams[3]),
        new Match(teams[1], teams[2]));
    // Return matches
    List<Match> matchDay4 = Arrays.asList(
        new Match(teams[1], teams[0]),
        new Match(teams[3], teams[2]));
    List<Match> matchDay5 = Arrays.asList(
        new Match(teams[2], teams[0]),
        new Match(teams[3], teams[1]));
    List<Match> matchDay6 = Arrays.asList(
        new Match(teams[3], teams[0]),
        new Match(teams[2], teams[1]));

    fixtures.add(new MatchDay(matchDay1));
    fixtures.add(new MatchDay(matchDay2));
    fixtures.add(new MatchDay(matchDay3));
    fixtures.add(new MatchDay(matchDay4));
    fixtures.add(new MatchDay(matchDay5));
    fixtures.add(new MatchDay(matchDay6));

    when(fixturesGenerator.generate()).thenReturn(fixtures);

    simulationApp = new SimulationApplication(teams, fixturesGenerator);
  }

  @Test
  void testInitialState() {
    assertEquals(0, simulationApp.getCurrentMatchDay());
    assertEquals(6, simulationApp.getTotalMatchDays());
    assertEquals(0, simulationApp.getSimulatedResults().size());
  }

  @Test
  void testSimulateSingleDay() {
    simulationApp.simulate(1);

    assertEquals(1, simulationApp.getCurrentMatchDay());
    assertEquals(1, simulationApp.getSimulatedResults().size());
    assertEquals(2, simulationApp.getSimulatedResults().get(0).size()); // 2 matches per day
  }

  @Test
  void testSimulateMultipleDays() {
    simulationApp.simulate(3);

    assertEquals(3, simulationApp.getCurrentMatchDay());
    assertEquals(3, simulationApp.getSimulatedResults().size());

    // Each result should have the correct teams
    Result result1 = simulationApp.getSimulatedResults().get(0).get(0);
    assertEquals(teams[0], result1.getMatch().getHomeTeam());
    assertEquals(teams[1], result1.getMatch().getAwayTeam());
  }

  @Test
  void testSimulateRemainder() {
    // Simulate 2 days first
    simulationApp.simulate(2);
    assertEquals(2, simulationApp.getCurrentMatchDay());

    // Then simulate the remaining days
    simulationApp.simulateRemainder();
    assertEquals(6, simulationApp.getCurrentMatchDay());
    assertEquals(6, simulationApp.getSimulatedResults().size());
  }

  @Test
  void testLeagueTableGeneration() {
    simulationApp.simulateRemainder();
    LeagueTable leagueTable = simulationApp.getLeagueTable();

    assertNotNull(leagueTable);
    for (Team team : teams) {
      assertTrue(leagueTable.getSortedTeamsList().stream().anyMatch(entry -> entry.equals(team)));
    }
  }
}
