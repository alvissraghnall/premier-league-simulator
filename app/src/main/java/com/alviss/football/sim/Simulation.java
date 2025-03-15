package com.alviss.football.sim;

import com.alviss.football.fixtures.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

  private Team home;
  private Team away;
  private int[] goalDistribution;
  private Random random;
  private static final double HOME_ADVANTAGE = 1.2;
  private double[] xGs = new double[2];

  public Simulation(Team home, Team away) {
    this.home = home;
    this.away = away;
    this.goalDistribution = generateGoalDistribution();
    this.random = new Random();
  }

  /**
   * Generates a realistic distribution of possible goal outcomes
   * Based on historical football match data
   */
  private int[] generateGoalDistribution() {
    // Goals distribution is weighted based on frequency in real matches
    // 0 goals: ~15%, 1 goal: ~25%, 2 goals: ~30%, 3 goals: ~20%, 4+ goals: ~10%
    int[] zeroGoals = new int[15];
    int[] oneGoal = new int[25];
    int[] twoGoals = new int[30];
    int[] threeGoals = new int[20];
    int[] fourGoals = new int[7];
    int[] fiveGoals = new int[2];
    int[] sixGoals = new int[1];

    Arrays.fill(oneGoal, 1);
    Arrays.fill(twoGoals, 2);
    Arrays.fill(threeGoals, 3);
    Arrays.fill(fourGoals, 4);
    Arrays.fill(fiveGoals, 5);
    Arrays.fill(sixGoals, 6);

    return concatArrays(zeroGoals, oneGoal, twoGoals, threeGoals, fourGoals, fiveGoals, sixGoals);
  }

  /**
   * Utility method to concatenate multiple arrays
   */
  private int[] concatArrays(int[] first, int[]... rest) {
    int totalLength = first.length;
    for (int[] arr : rest) {
      totalLength += arr.length;
    }
    int[] result = Arrays.copyOf(first, totalLength);
    int offset = first.length;

    for (int[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }
    return result;
  }

  /**
   * Calculates expected goals (xG) based on team stats
   * 
   * @param attacking Team doing the attacking
   * @param defending Team doing the defending
   * @param isHome    Whether the attacking team is playing at home
   * @return Expected goals value
   */
  private double calculateExpectedGoals(Team attacking, Team defending, boolean isHome) {
    // Base xG calculated from attack vs defense ratings
    double baseXg = (attacking.getAttack() / (double) defending.getDefence()) * 1.5;

    // Adjust for home advantage
    if (isHome) {
      baseXg *= HOME_ADVANTAGE;
    }

    // Midfield influence on chance creation
    double midfieldFactor = attacking.getMidfield() / 100.0 * 0.5;

    // Team quality overall factor
    double qualityFactor = attacking.getOverall() / 100.0 * 0.3;

    // Random variance to simulate match day form
    double formVariance = 0.7 + (random.nextDouble() * 0.6);

    return baseXg * (1 + midfieldFactor + qualityFactor) * formVariance;
  }

  /**
   * Converts expected goals to actual goals using Poisson-like distribution
   * 
   * @param xG Expected goals
   * @return Actual goals scored
   */
  private int convertExpectedGoalsToActual(double xG) {
    // Base from our distribution
    int baseIndex = random.nextInt(goalDistribution.length);
    int baseGoals = goalDistribution[baseIndex];

    // Modify based on xG
    if (xG > 2.5 && baseGoals < 2) {
      // High xG teams are more likely to score more
      baseGoals += 1;
    } else if (xG < 0.8 && baseGoals > 2) {
      // Low xG teams are less likely to score many
      baseGoals -= 1;
    }

    // Ensure non-negative
    return Math.max(0, baseGoals);
  }

  /**
   * Simulates special events like red cards, injuries, etc.
   * 
   * @return A map with event types and their effects
   */
  private Map<String, Double> simulateSpecialEvents() {
    Map<String, Double> events = new HashMap<>();

    // 5% chance of red card
    if (random.nextDouble() < 0.05) {
      // Determine which team gets the red card (slightly higher for away team)
      boolean homeTeamCard = random.nextDouble() > 0.55;
      String key = homeTeamCard ? "homeRedCard" : "awayRedCard";
      // Reduction factor for the team with red card
      events.put(key, 0.7); // 30% reduction in effectiveness
    }

    // 10% chance of weather impact
    if (random.nextDouble() < 0.1) {
      // Bad weather reduces scoring
      events.put("weatherImpact", 0.85);
    }

    return events;
  }

  /**
   * Main method to simulate and compute the match score
   * 
   * @return Map containing the score for each team
   */
  public Map<Team, Integer> computeScore() {
    Map<Team, Integer> matchScore = new HashMap<>();

    // Calculate expected goals
    double homeXG = calculateExpectedGoals(home, away, true);
    double awayXG = calculateExpectedGoals(away, home, false);

    xGs[0] = homeXG;
    xGs[1] = awayXG;

    // Simulate special events
    Map<String, Double> events = simulateSpecialEvents();

    // Apply special event modifiers
    if (events.containsKey("homeRedCard")) {
      homeXG *= events.get("homeRedCard");
    }
    if (events.containsKey("awayRedCard")) {
      awayXG *= events.get("awayRedCard");
    }
    if (events.containsKey("weatherImpact")) {
      homeXG *= events.get("weatherImpact");
      awayXG *= events.get("weatherImpact");
    }

    // Convert to actual goals
    int homeGoals = convertExpectedGoalsToActual(homeXG);
    int awayGoals = convertExpectedGoalsToActual(awayXG);

    // High-scoring match adjustment (makes high-scoring matches more realistic)
    if (homeGoals + awayGoals > 7) {
      // Extremely high-scoring matches are rare
      int totalGoalsAdjustment = random.nextInt(3);
      if (homeGoals > awayGoals) {
        homeGoals -= totalGoalsAdjustment;
      } else {
        awayGoals -= totalGoalsAdjustment;
      }
    }

    // Return the final score
    matchScore.put(home, Math.max(0, homeGoals));
    matchScore.put(away, Math.max(0, awayGoals));

    return matchScore;
  }

  /**
   * Returns match statistics beyond just the score
   * 
   * @return Statistics with various match statistics
   */
  public Statistics getMatchStatistics() {
    // Map<String, Object> stats = new HashMap<>();

    // Base possession on midfield ratings
    double totalMidfield = home.getMidfield() + away.getMidfield();
    int homePossession = (int) Math.round(((home.getMidfield() / totalMidfield) + random.nextInt(-20, 20)) * 100);

    // Shots based on attack ratings and possession
    int homeShots = 5 + (int) (home.getAttack() / 10.0) + (homePossession > 55 ? 3 : 0);
    int awayShots = 3 + (int) (away.getAttack() / 10.0) + (homePossession < 45 ? 3 : 0);

    // Shots on target based on shots and attacking efficiency
    int homeShotsOnTarget = (int) Math.round(homeShots * (0.3 + (home.getAttack() / 200.0)));
    int awayShotsOnTarget = (int) Math.round(awayShots * (0.3 + (away.getAttack() / 200.0)));

    // Add small random variance
    homePossession += (random.nextInt(11) - 5);
    homePossession = Math.min(100, Math.max(0, homePossession));

    Statistics stats = new Statistics(
        homePossession,
        100 - homePossession,
        homeShots,
        awayShots,
        homeShotsOnTarget,
        awayShotsOnTarget,
        2 + random.nextInt(8),
        1 + random.nextInt(6),
        5 + random.nextInt(10),
        7 + random.nextInt(10),
        xGs[0],
        xGs[1]);

    return stats;
  }
}
