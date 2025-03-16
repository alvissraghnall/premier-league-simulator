package com.alviss.football.sim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatisticsTest {
  private Statistics statistics;

  @BeforeEach
  void setUp() {
    statistics = new Statistics(60, 40, 15, 8, 6, 3, 7, 2, 10, 12, 2.3, 1.1);
  }

  @Test
  void testGetters() {
    assertEquals(60, statistics.getHomePossession());
    assertEquals(40, statistics.getAwayPossession());
    assertEquals(15, statistics.getHomeShots());
    assertEquals(8, statistics.getAwayShots());
    assertEquals(6, statistics.getHomeShotsOnTarget());
    assertEquals(3, statistics.getAwayShotsOnTarget());
    assertEquals(7, statistics.getHomeCorners());
    assertEquals(2, statistics.getAwayCorners());
    assertEquals(10, statistics.getHomeFouls());
    assertEquals(12, statistics.getAwayFouls());
    assertEquals(2.3, statistics.getHomeXG());
    assertEquals(1.1, statistics.getAwayXG());
  }

  @Test
  void testPossessionSumsTo100() {
    assertEquals(100, statistics.getHomePossession() + statistics.getAwayPossession());
  }
}
