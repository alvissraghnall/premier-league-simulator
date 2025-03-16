package com.alviss.football.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import com.alviss.football.fixtures.Team;

@Testable
class SimulationTest {
  private Team homeTeam;
  private Team awayTeam;
  private Simulation simulation;

  @BeforeEach
  void setUp() {
    homeTeam = new Team("HomeTeam", "HTE", 80, 75, 70, 85);
    awayTeam = new Team("AwayTeam", "ATE", 75, 70, 80, 75);
    simulation = new Simulation(homeTeam, awayTeam);
  }

  @Test
  void testComputeScore() {
    Map<Team, Integer> score = simulation.computeScore();

    assertNotNull(score);
    assertTrue(score.containsKey(homeTeam));
    assertTrue(score.containsKey(awayTeam));

    // Goals should be realistic values
    assertTrue(score.get(homeTeam) >= 0);
    assertTrue(score.get(homeTeam) <= 6);
    assertTrue(score.get(awayTeam) >= 0);
    assertTrue(score.get(awayTeam) <= 6);
  }

  @Test
  void testGetMatchStatistics() {
    simulation.computeScore();

    Statistics stats = simulation.getMatchStatistics();

    assertNotNull(stats);

    assertEquals(100, stats.getHomePossession() + stats.getAwayPossession());

    assertTrue(stats.getHomeShots() >= 0);
    assertTrue(stats.getAwayShots() >= 0);
    assertTrue(stats.getHomeShotsOnTarget() <= stats.getHomeShots());
    assertTrue(stats.getAwayShotsOnTarget() <= stats.getAwayShots());
    assertTrue(stats.getHomeCorners() >= 0);
    assertTrue(stats.getAwayCorners() >= 0);
    assertTrue(stats.getHomeFouls() >= 0);
    assertTrue(stats.getAwayFouls() >= 0);
  }

  @Test
  void testHomeAdvantage() {
    int homeWins = 0;
    int awayWins = 0;
    int draws = 0;

    for (int i = 0; i < 100; i++) {
      Simulation sim = new Simulation(homeTeam, awayTeam);
      Map<Team, Integer> score = sim.computeScore();

      if (score.get(homeTeam) > score.get(awayTeam)) {
        homeWins++;
      } else if (score.get(homeTeam) < score.get(awayTeam)) {
        awayWins++;
      } else {
        draws++;
      }
    }

    assertTrue(homeWins > awayWins, "Home team should win more often due to home advantage");
  }

  @Test
  void testBetterTeamPerformance() {
    Team strongTeam = new Team("StrongTeam", "STE", 90, 90, 90, 90);
    Team weakTeam = new Team("WeakTeam", "WTE", 60, 60, 60, 60);

    int strongTeamWins = 0;
    int weakTeamWins = 0;

    // Home advantage for weak team to see if quality still dominates
    for (int i = 0; i < 100; i++) {
      Simulation sim = new Simulation(weakTeam, strongTeam);
      Map<Team, Integer> score = sim.computeScore();

      if (score.get(weakTeam) > score.get(strongTeam)) {
        weakTeamWins++;
      } else if (score.get(weakTeam) < score.get(strongTeam)) {
        strongTeamWins++;
      }
    }

    assertTrue(strongTeamWins > weakTeamWins, "Stronger team should win more often even when playing away");
  }
}
