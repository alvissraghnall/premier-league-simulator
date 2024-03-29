package com.alviss.football.fixtures;

import java.util.HashMap;

public class Match {
    private Team homeTeam;
    private Team awayTeam;
    
    public Match (Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
    
    public Team getHomeTeam() {
        return homeTeam;
    }
    
    public Team getAwayTeam() {
        return awayTeam;
    }
}
