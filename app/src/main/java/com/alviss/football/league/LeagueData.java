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
    private FixedSizeDeque<Form> form = new FixedSizeDeque<>(5);

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

    public void setForm (FixedSizeDeque<Form> form) {
        this.form = form;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + played;
        result = prime * result + wins;
        result = prime * result + draws;
        result = prime * result + losses;
        result = prime * result + goalsFor;
        result = prime * result + goalsAgainst;
        result = prime * result + goalsDifference;
        result = prime * result + points;
        result = prime * result + ((form == null) ? 0 : form.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LeagueData other = (LeagueData) obj;
        if (played != other.played)
            return false;
        if (wins != other.wins)
            return false;
        if (draws != other.draws)
            return false;
        if (losses != other.losses)
            return false;
        if (goalsFor != other.goalsFor)
            return false;
        if (goalsAgainst != other.goalsAgainst)
            return false;
        if (goalsDifference != other.goalsDifference)
            return false;
        if (points != other.points)
            return false;
        if (form == null) {
            if (other.form != null)
                return false;
        } else if (!form.equals(other.form))
            return false;
        return true;
    }

}
