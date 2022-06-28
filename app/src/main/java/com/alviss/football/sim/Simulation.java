package com.alviss.football.sim;

import java.util.Map;

public class Simulation {

    private Team home;
    private Team away;

    public Simulation (Team one, Team two) {
        this.home = one;
        this.away = two;
    }

    Map<Integer, Integer> computeScore () {
        int homeBase = Math.floor(Math.random() * 10);
        int awayBase = Math.floor(Math.random() * 10);
    }
}