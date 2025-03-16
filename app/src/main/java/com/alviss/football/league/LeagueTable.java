package com.alviss.football.league;

import com.alviss.football.fixtures.Team;
import com.alviss.football.sim.Result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueTable {

  private List<List<Result>> results;
  private Map<Team, LeagueData> cachedTeamDataMap;

  public LeagueTable(List<List<Result>> results) {
    this.results = results;
    cachedTeamDataMap = null;
  }

  public Map<Team, LeagueData> generate() {
    if (cachedTeamDataMap != null) {
      return cachedTeamDataMap;
    }

    Map<Team, LeagueData> teamDataMap = new HashMap<>();

    for (List<Result> roundResults : results) {
      for (Result result : roundResults) {
        processResult(teamDataMap, result);
      }
    }

    cachedTeamDataMap = teamDataMap;
    return teamDataMap;
  }

  private Comparator<Team> createTeamComparator(Map<Team, LeagueData> teamLeagueDataMap) {
    return Comparator.<Team, Integer>comparing(team -> teamLeagueDataMap.get(team).getPoints())
        .reversed()
        .thenComparing(team -> teamLeagueDataMap.get(team).getGoalsDifference(), Comparator.reverseOrder())
        .thenComparing(team -> teamLeagueDataMap.get(team).getGoalsFor(), Comparator.reverseOrder())
        .thenComparing(team -> team.getName());
  }

  public List<Team> getTopTeams(int count) {
    List<Team> sortedTeams = getSortedTeamsList();
    return sortedTeams.subList(0, Math.min(count, sortedTeams.size()));
  }

  public List<Team> getBottomTeams(int count) {
    List<Team> sortedTeams = getSortedTeamsList();
    int start = Math.max(0, sortedTeams.size() - count);
    return sortedTeams.subList(start, sortedTeams.size());
  }

  private void processResult(Map<Team, LeagueData> teamDataMap, Result result) {
    Team homeTeam = result.getMatch().getHomeTeam();
    Team awayTeam = result.getMatch().getAwayTeam();

    int homeScore = result.getScore().get(homeTeam);
    int awayScore = result.getScore().get(awayTeam);

    updateTeamData(teamDataMap, homeTeam, homeScore, awayScore);

    updateTeamData(teamDataMap, awayTeam, awayScore, homeScore);
  }

  private void updateTeamData(Map<Team, LeagueData> teamDataMap, Team team, int goalsFor,
      int goalsAgainst) {
    LeagueData teamData = teamDataMap.computeIfAbsent(team, k -> new LeagueData());

    teamData.setPlayed(teamData.getPlayed() + 1);
    teamData.setGoalsFor(teamData.getGoalsFor() + goalsFor);
    teamData.setGoalsAgainst(teamData.getGoalsAgainst() + goalsAgainst);
    teamData.setGoalsDifference(teamData.getGoalsFor() - teamData.getGoalsAgainst());

    FixedSizeDeque<Form> form = teamData.getForm();
    if (goalsFor > goalsAgainst) {
      teamData.setWins(teamData.getWins() + 1);
      teamData.setPoints(teamData.getPoints() + 3);
      form.add(Form.W);
    } else if (goalsFor == goalsAgainst) {
      teamData.setDraws(teamData.getDraws() + 1);
      teamData.setPoints(teamData.getPoints() + 1);
      form.add(Form.D);
    } else {
      teamData.setLosses(teamData.getLosses() + 1);
      form.add(Form.L);
    }

    teamData.setForm(form);
  }

  public List<Team> getSortedTeamsList() {
    Map<Team, LeagueData> teamLeagueDataMap = generate();
    List<Team> sortedTeams = new ArrayList<>(teamLeagueDataMap.keySet());

    sortedTeams.sort(createTeamComparator(teamLeagueDataMap));

    return sortedTeams;
  }

  public void printTable() {
    List<Team> sortedTeams = getSortedTeamsList();
    Map<Team, LeagueData> dataMap = generate();

    System.out.println(String.format("%-20s %3s %3s %3s %3s %3s %3s %3s %3s",
        "Team", "P", "W", "D", "L", "GF", "GA", "GD", "Pts"));
    System.out.println("-".repeat(60));

    for (int i = 0; i < sortedTeams.size(); i++) {
      Team team = sortedTeams.get(i);
      LeagueData data = dataMap.get(team);
      System.out.println(String.format("%-20s %3d %3d %3d %3d %3d %3d %3d %3d",
          (i + 1) + ". " + team.getName(),
          data.getPlayed(),
          data.getWins(),
          data.getDraws(),
          data.getLosses(),
          data.getGoalsFor(),
          data.getGoalsAgainst(),
          data.getGoalsDifference(),
          data.getPoints()));
    }
  }

}
