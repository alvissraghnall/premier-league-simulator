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

  private int currentMatchDay;
  private LeagueTable leagueTable;

  public static List<List<Result>> simulate(Team[] teams) {
    Simulation sim;
    Map<Team, Integer> score;
    Statistics stats;
    Result result;
    final List<List<Result>> fixturesWithScores = new LinkedList<>();

    FixturesGenerator fixgen = new FixturesGenerator(teams);
    List<MatchDay> fixtures = fixgen.generate();

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
}
