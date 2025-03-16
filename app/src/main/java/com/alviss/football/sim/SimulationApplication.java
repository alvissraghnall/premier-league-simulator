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

  public List<List<Result>> simulate(int numOfDays) {
    Simulation sim;
    Map<Team, Integer> score;
    Statistics stats;
    Result result;
    final List<List<Result>> fixturesWithScores = new LinkedList<>();

    for (MatchDay roundFixtures : fixtures) {
      List<Result> matchDay = new ArrayList<>();
      for (Match match : roundFixtures.getMatches()) {
        sim = new Simulation(match.getHomeTeam(), match.getAwayTeam());
        score = sim.computeScore();
        stats = sim.getMatchStatistics();
        result = new Result(match, score, stats);

        matchDay.add(result);
      }
      // System.out.println(matchDay.size());
      fixturesWithScores.add(matchDay);
    }
    return fixturesWithScores;
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
