package com.alviss.football.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alviss.football.fixtures.Team;
import com.alviss.football.league.LeagueData;
import com.alviss.football.league.LeagueTable;
import com.alviss.football.league.RankedTeamLeagueData;
import org.springframework.stereotype.Service;

import com.alviss.football.fixtures.FixturesApplication;
import com.alviss.football.sim.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HomeService {

  // final private ObjectMapper objectMapper;
  final private FixturesApplication fixturesApplication;
  final Team[] teams;

  public HomeService(final FixturesApplication fixturesApplication, Team[] fillTeams) {
    // this.objectMapper = objectMapper;
    this.fixturesApplication = fixturesApplication;
    this.teams = fillTeams;
  }

  public Team[] getTeams() {
    return teams;
  }

  public List<RankedTeamLeagueData> simulate(int numOfDays) throws IOException {

    /// TODO: EDITTTTTTTTTTTTTTTTTTTTTTT
    LeagueTable table = fixturesApplication.simulate(numOfDays);

    Map<Team, LeagueData> teamLeagueDataMap = table.generate();
    List<Team> sortedTeams = table.getSortedTeamsList();

    List<RankedTeamLeagueData> teamDataList = new ArrayList<>();
    int rank = 1;
    for (Team team : sortedTeams) {
      LeagueData teamData = teamLeagueDataMap.get(team);
      RankedTeamLeagueData data = new RankedTeamLeagueData();

      data.setTeamName(team.getName());
      data.setRank(rank++);
      data.setPoints(teamData.getPoints());
      data.setGoalsFor(teamData.getGoalsFor());
      data.setGoalsAgainst(teamData.getGoalsAgainst());
      data.setGoalsDifference(teamData.getGoalsDifference());
      data.setDraws(teamData.getDraws());
      data.setWins(teamData.getWins());
      data.setLosses(teamData.getLosses());
      data.setPlayed(teamData.getPlayed());
      data.setForm(teamData.getForm());
      teamDataList.add(data);
    }
    return teamDataList;
  }

  private List<RankedTeamLeagueData> processLeagueTable(LeagueTable table) {
    Map<Team, LeagueData> teamLeagueDataMap = table.generate();
    List<Team> sortedTeams = table.getSortedTeamsList();

    List<RankedTeamLeagueData> teamDataList = new ArrayList<>();
    int rank = 1;
    for (Team team : sortedTeams) {
      LeagueData teamData = teamLeagueDataMap.get(team);
      RankedTeamLeagueData data = new RankedTeamLeagueData();

      data.setTeamName(team.getName());
      data.setRank(rank++);
      data.setPoints(teamData.getPoints());
      data.setGoalsFor(teamData.getGoalsFor());
      data.setGoalsAgainst(teamData.getGoalsAgainst());
      data.setGoalsDifference(teamData.getGoalsDifference());
      data.setDraws(teamData.getDraws());
      data.setWins(teamData.getWins());
      data.setLosses(teamData.getLosses());
      data.setPlayed(teamData.getPlayed());
      data.setForm(teamData.getForm());
      teamDataList.add(data);
    }
    return teamDataList;
  }

  // Simulate a specific number of days
  public List<RankedTeamLeagueData> simulate(int numOfDays) throws IOException {
    LeagueTable table = fixturesApplication.simulate(numOfDays);
    return processLeagueTable(table);
  }

  // Simulate all remaining matches
  public List<RankedTeamLeagueData> simulate() throws IOException {
    LeagueTable table = fixturesApplication.simulate();
    return processLeagueTable(table);
  }
}
