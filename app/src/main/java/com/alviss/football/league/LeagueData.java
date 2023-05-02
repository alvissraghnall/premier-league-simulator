package com.alviss.football.league;


public class LeagueData {
    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int goalsDifference;
    private int points;

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalsDifference() {
        return goalsDifference;
    }

    public void setGoalsDifference(int goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public FixedSizeDeque<Form> getForm() {
        return form;
    }

    private void setForm(FixedSizeDeque<Form> form) {
        this.form = form;
    }

    private FixedSizeDeque<Form> form = new FixedSizeDeque<>(5);

}
