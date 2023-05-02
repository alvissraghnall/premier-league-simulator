package com.alviss.football.league;

public class RankedTeamLeagueData extends LeagueData {

    private int rank;

    private String teamName;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
