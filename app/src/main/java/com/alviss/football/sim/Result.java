package com.alviss.football.sim;

import java.util.Map;

import com.alviss.football.fixtures.Team;
import com.alviss.football.fixtures.Match;

public class Result {

    private Match match;

    private Map<Team, Integer> score;

    public Result(Match match, Map<Team, Integer> score) {
        this.match = match;
        this.score = score;
    }

    public Match getMatch() {
        return match;
    }

    void setMatch(Match match) {
        this.match = match;
    }

    public Map<Team, Integer> getScore() {
        return score;
    }

    void setScore(Map<Team, Integer> score) {
        this.score = score;
    }
}
