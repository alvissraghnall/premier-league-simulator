package com.alviss.football.sim;

import java.util.Map;

import com.alviss.football.fixtures.Team;
import com.alviss.football.fixtures.Match;

public class Result {

  private Match match;

  private Map<Team, Integer> score;

  private Statistics stats;

  public Result(Match match, Map<Team, Integer> score, Statistics stats) {
    this.match = match;
    this.score = score;
    this.stats = stats;
  }

  public Match getMatch() {
    return match;
  }

  public Map<Team, Integer> getScore() {
    return score;
  }

  public Statistics getStats() {
    return stats;
  }
}
