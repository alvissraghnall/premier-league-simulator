package com.alviss.football.sim;

import com.alviss.football.fixtures.FixturesGenerator;
import com.alviss.football.fixtures.Match;
import com.alviss.football.fixtures.MatchDay;
import com.alviss.football.fixtures.Team;
import com.alviss.football.league.LeagueTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationApplication {

  private Team[] teams;
  private int currentMatchDay;
  private LeagueTable leagueTable;
  private List<MatchDay> fixtures;
  final List<List<Result>> fixturesWithScores;

  public SimulationApplication(Team[] teams, FixturesGenerator fixgen) {
    this.teams = teams;
    this.fixtures = fixgen.generate();
    this.fixturesWithScores = new LinkedList<>();
    this.currentMatchDay = 0;
    this.leagueTable = new LeagueTable(fixturesWithScores);
  }

  public void simulate(int numOfDays) {
    int daysToSimulate = Math.min(numOfDays, fixtures.size() - currentMatchDay);
    // List<List<Result>> simulatedResults = new LinkedList<>();

    for (int i = 0; i < daysToSimulate; i++) {
      MatchDay roundFixtures = fixtures.get(currentMatchDay);
      List<Result> matchDay = simulateMatchDay(roundFixtures);

      fixturesWithScores.add(matchDay);
      // simulatedResults.add(matchDay);
      currentMatchDay++;
    }

    this.leagueTable = new LeagueTable(fixturesWithScores);

    // return simulatedResults;
  }

  private List<Result> simulateMatchDay(MatchDay roundFixtures) {
    List<Result> matchDay = new ArrayList<>();
    for (Match match : roundFixtures.getMatches()) {
      Simulation sim = new Simulation(match.getHomeTeam(), match.getAwayTeam());
      Map<Team, Integer> score = sim.computeScore();
      Statistics stats = sim.getMatchStatistics();
      Result result = new Result(match, score, stats);

      matchDay.add(result);
    }
    return matchDay;
  }

  public void simulateRemainder() {
    simulate(fixtures.size() - currentMatchDay);
  }

  public int getTotalMatchDays() {
    return fixtures.size();
  }

  public int getCurrentMatchDay() {
    return currentMatchDay;
  }

  public LeagueTable getLeagueTable() {
    return leagueTable;
  }

  public List<List<Result>> getSimulatedResults() {
    return fixturesWithScores;
  }
}
