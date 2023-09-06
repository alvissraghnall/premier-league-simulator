package com.alviss.football.sim;

import com.alviss.football.fixtures.Team;

/**
 *
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {

    private Team home;
    private Team away;
    private List<Integer> goalsList;

    public Simulation(Team home, Team away) {
        this.home = home;
        this.away = away;
        this.goalsList = generateGoalsList();
    }

    private List<Integer> generateGoalsList() {
        List<Integer> goalsList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10 - i; j++) {
                goalsList.add(i);
            }
        }
        Collections.shuffle(goalsList);
        return goalsList;
    }

    private int generateBase() {
        return (int) (Math.random() * goalsList.size());
    }

    private int adjustScore(int score, int attack, int defense) {
        int adjustment = 0;
        if (attack > defense + 5) {
            if (score <= 1) {
                adjustment = 1;
            } else if (score >= 9) {
                adjustment = -1;
            }
        } else if (defense > attack + 5) {
            if (score >= 3) {
                adjustment = -1;
            } else if (score <= 1) {
                adjustment = 1;
            }
        } else if (score >= 5 && attack < 79) {
            adjustment = -1;
        }
        return score + adjustment;
    }

    public Map<Team, Integer> computeScore() {
        Map<Team, Integer> matchScore = new HashMap<>();
        int homeScore = goalsList.get(generateBase());
        int awayScore = goalsList.get(generateBase());

        homeScore = adjustScore(homeScore, home.getAttack(), away.getDefence());
        awayScore = adjustScore(awayScore, away.getAttack(), home.getDefence());

        matchScore.put(home, homeScore);
        matchScore.put(away, awayScore);

        return matchScore;
    }
}
*/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    private Team home;
    private Team away;
    private int[] goalsList;

    public Simulation (Team home, Team away) {
        this.home = home;
        this.away = away;
//        goalsList = this.generateGoalsList();
    }

    private int[] generateGoalsList () {
        int[] zeroList = new int[10];
        int[] oneList = new int[20];
        int[] twoList = new int[30];
        int[] threeList = new int[25];
        int[] fourList = new int[10];
        int[] fiveList = new int[4];
        int[] sixList = new int[1];

        Arrays.fill(oneList, 1);
        Arrays.fill(twoList, 2);
        Arrays.fill(threeList, 3);
        Arrays.fill(fourList, 4);
        Arrays.fill(fiveList, 5);
        Arrays.fill(sixList, 6);

        return (int[]) concatArrays(zeroList, oneList, twoList, threeList, fourList, fiveList, sixList);
    }

    private  int[] concatArrays (int[] first, int[]... rest) {
        int totalLength = first.length;
        for (int[] arr : rest) {
            totalLength += arr.length;
        }
        int[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;

        for (int[] array : rest ) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private int generateBase () {
        return ThreadLocalRandom.current().nextInt(0, goalsList.length);
    }

//    public Map<Team, Integer> computeScore () {
//        HashMap<Team, Integer> matchScore = new HashMap<>();
//        int[] bases = new int[] {generateBase(), generateBase()};
//        int homeScore = goalsList[bases[0]];
//        int awayScore = goalsList[bases[1]];
//
//        // Add weight based on team's attack and defence ratings
//        homeScore = (int) Math.round(homeScore * (home.getAttack() + away.getDefence()) / 100.0);
//        awayScore = (int) Math.round(awayScore * (away.getAttack() + home.getDefence()) / 100.0);
//
//        // Add weight based on team's midfield ratings
//        homeScore += (int) Math.round(homeScore * home.getMidfield() / 100.0);
//        awayScore += (int) Math.round(awayScore * away.getMidfield() / 100.0);
//
//        // Apply additional conditions to make the score more realistic
//        if (homeScore >= 3 && homeScore > awayScore + 2 && home.getAttack() > 80) {
//            homeScore = ThreadLocalRandom.current().nextInt(awayScore, homeScore);
//        }
//
//        if (awayScore >= 3 && awayScore > homeScore + 2 && away.getAttack() > 80) {
//            awayScore = ThreadLocalRandom.current().nextInt(homeScore, awayScore);
//        }  
//
//        // Put the scores into a Map
//        matchScore.put(home, homeScore);
//        matchScore.put(away, awayScore);
//
//        return matchScore;
//    }

    public int computeScore(Team home, Team away) {
        Random rand = new Random();
        int score = 0;

        // Calculate weights for each stat based on strength of opposing defense
        double homeDefWeight = 0.3 + (0.1 * (10 - away.getDefence())); // Increase defense weight for home team if away team has weak defense
        double homeAttWeight = 0.1 + (0.1 * home.getAttack()) - (0.05 * (10 - away.getDefence())); // Adjust attack weight for home team based on strength of away team's defense
        double awayDefWeight = 0.3 + (0.1 * (10 - home.getDefence())); // Increase defense weight for away team if home team has weak defense
        double awayAttWeight = 0.1 + (0.1 * away.getAttack()) - (0.05 * (10 - home.getDefence())); // Adjust attack weight for away team based on strength of home team's defense

        // Calculate total weight for each team
        double homeWeight = home.getOverall() * 0.4 + homeDefWeight * 0.3 + home.getMidfield() * 0.2 + homeAttWeight * 0.1;
        double awayWeight = away.getOverall() * 0.4 + awayDefWeight * 0.3 + away.getMidfield() * 0.2 + awayAttWeight * 0.1;
        double totalWeight = homeWeight + awayWeight;

        int randomNum = rand.nextInt((int) totalWeight);

        if (randomNum < homeWeight) {
            score += rand.nextInt(4) + 2;
        }
        if (randomNum >= homeWeight && randomNum < totalWeight) {
            score += rand.nextInt(4) + 1;
        }

        return score;
    }



    public Map<Team, Integer> getScore () {
        HashMap<Team, Integer> matchScore = new HashMap<>();
        int homeScore = computeScore(home, away);
        int awayScore = computeScore(away, home);

        matchScore.put(home, homeScore);
        matchScore.put(away, awayScore);
        return matchScore;
    }
}
