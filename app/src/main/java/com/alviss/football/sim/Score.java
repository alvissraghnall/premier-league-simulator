package com.alviss.football.sim;

public class Score {

    private String name;

    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }
}
