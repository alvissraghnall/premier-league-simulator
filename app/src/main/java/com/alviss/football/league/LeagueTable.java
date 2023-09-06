package com.alviss.football.league;

import com.alviss.football.fixtures.Team;
import com.alviss.football.sim.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueTable {

    private List<List<Result>> results;

    public LeagueTable(List<List<Result>> results) {
        this.results = results;
    }

    public Map<Team, LeagueData> generate() {

        // List<List<Result>> results is assumed to be already defined

        Map<Team, LeagueData> teamDataMap = new HashMap<>();

        for (List<Result> roundResults : results) {
            for (Result result : roundResults) {
                Team homeTeam = result.getMatch().getHomeTeam();
                Team awayTeam = result.getMatch().getAwayTeam();

                int homeScore = result.getScore().get(homeTeam);
                int awayScore = result.getScore().get(awayTeam);

                // update home team data
                LeagueData homeTeamData = teamDataMap.computeIfAbsent(homeTeam, k -> new LeagueData());
                homeTeamData.setPlayed(homeTeamData.getPlayed() + 1);
                homeTeamData.setGoalsFor(homeTeamData.getGoalsFor() + homeScore);
                homeTeamData.setGoalsAgainst(homeTeamData.getGoalsAgainst() + awayScore);
                homeTeamData.setGoalsDifference(homeTeamData.getGoalsFor() - homeTeamData.getGoalsAgainst());
                if (homeScore > awayScore) {
                    homeTeamData.setWins(homeTeamData.getWins() + 1);
                    homeTeamData.setPoints(homeTeamData.getPoints() + 3);
                    FixedSizeDeque<Form> form = homeTeamData.getForm();
                    form.add(Form.W);
                    homeTeamData.setForm(form);
                } else if (homeScore == awayScore) {
                    homeTeamData.setDraws(homeTeamData.getDraws() + 1);
                    homeTeamData.setPoints(homeTeamData.getPoints() + 1);
                    FixedSizeDeque<Form> form = homeTeamData.getForm();
                    form.add(Form.D);
                    homeTeamData.setForm(form);
                } else {
                    homeTeamData.setLosses(homeTeamData.getLosses() + 1);
                    FixedSizeDeque<Form> form = homeTeamData.getForm();
                    form.add(Form.L);
                    homeTeamData.setForm(form);
                }
                // update away team data (similar to home team data)
                LeagueData awayTeamData = teamDataMap.computeIfAbsent(awayTeam, k -> new LeagueData());
                awayTeamData.setPlayed(awayTeamData.getPlayed() + 1);
                awayTeamData.setGoalsFor(awayTeamData.getGoalsFor() + awayScore);
                awayTeamData.setGoalsAgainst(awayTeamData.getGoalsAgainst() + awayScore);
                awayTeamData.setGoalsDifference(awayTeamData.getGoalsFor() - awayTeamData.getGoalsAgainst());
                if (awayScore > homeScore) {
                    awayTeamData.setWins(awayTeamData.getWins() + 1);
                    awayTeamData.setPoints(awayTeamData.getPoints() + 3);
                    FixedSizeDeque<Form> form = awayTeamData.getForm();
                    form.add(Form.W);
                    awayTeamData.setForm(form);
                } else if (homeScore == awayScore) {
                    awayTeamData.setDraws(awayTeamData.getDraws() + 1);
                    awayTeamData.setPoints(awayTeamData.getPoints() + 1);
                    FixedSizeDeque<Form> form = awayTeamData.getForm();
                    form.add(Form.D);
                    awayTeamData.setForm(form);
                } else {
                    awayTeamData.setLosses(awayTeamData.getLosses() + 1);
                    FixedSizeDeque<Form> form = awayTeamData.getForm();
                    form.add(Form.L);
                    awayTeamData.setForm(form);
                }
            }
        }


        return teamDataMap;

    }

    public List<Team> getSortedTeamsList () {
        Map<Team, LeagueData> teamLeagueDataMap = generate();
        List<Team> sortedTeams = new ArrayList<>(teamLeagueDataMap.keySet());
        sortedTeams.sort((team1, team2) -> {
            LeagueData team1Data = teamLeagueDataMap.get(team1);
            LeagueData team2Data = teamLeagueDataMap.get(team2);
            int pointsDiff = team2Data.getPoints() - team1Data.getPoints();
            if (pointsDiff != 0) {
                return pointsDiff;
            } else {
                int goalDiff = team2Data.getGoalsDifference() - team1Data.getGoalsDifference();
                if (goalDiff != 0) {
                    return goalDiff;
                } else {
                    return team2Data.getGoalsFor() - team1Data.getGoalsFor();
                }
            }
        });
        return sortedTeams;
    }
}