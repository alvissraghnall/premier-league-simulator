package com.alviss.football.fixtures;

import java.util.ArrayList;
import java.util.List;

public class MatchDay {
    private int number;
    private List<Match> matches;
    
    public MatchDay(int number) {
        this.number = number;
        this.matches = new ArrayList<>();
    }
    
    public void addMatch(Match match) {
        matches.add(match);
    }
    
    public List<Match> getMatches() {
        return matches;
    }
    
    public int getNumber() {
        return number;
    }
}