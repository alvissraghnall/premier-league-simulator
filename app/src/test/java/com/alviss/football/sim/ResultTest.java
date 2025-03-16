package com.alviss.football.sim;

import static org.junit.jupiter.api.Assertions.*;
import com.alviss.football.fixtures.Match;
import com.alviss.football.fixtures.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class ResultTest {
  private Match match;
  private Map<Team, Integer> score;
  private Statistics stats;
  private Result result;

  @BeforeEach
  void setUp() {
    Team homeTeam = new Team("HomeTeam", "HTE", 80, 75, 70, 85);
    Team awayTeam = new Team("AwayTeam", "ATE", 75, 70, 80, 75);

    match = new Match(homeTeam, awayTeam);

    score = new HashMap<>();
    score.put(homeTeam, 2);
    score.put(awayTeam, 1);

    stats = new Statistics(60, 40, 15, 8, 6, 3, 7, 2, 10, 12, 2.3, 1.1);

    result = new Result(match, score, stats);
  }

  @Test
  void testGetMatch() {
    assertEquals(match, result.getMatch());
  }

  @Test
  void testGetScore() {
    assertEquals(score, result.getScore());
    assertEquals(2, result.getScore().get(match.getHomeTeam()));
    assertEquals(1, result.getScore().get(match.getAwayTeam()));
  }

  @Test
  void testGetStats() {
    assertEquals(stats, result.getStats());
  }
}
