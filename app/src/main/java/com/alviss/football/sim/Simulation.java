package com.alviss.football.sim;

import com.alviss.football.fixtures.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    private Team home;
    private Team away;
    private int[] goalsList;

    public Simulation (Team home, Team away) {
        this.home = home;
        this.away = away;
        goalsList = this.teamGoalsList();
    }


    private int[] teamGoalsList () {
        int zeroList[] = new int[20], oneList[] = new int[20], twoList[] = new int[10], threeList[] = new int[8], fourList[] = new int[6], fiveList[] = new int[4], sixList[] = new int[3], sevenList[] = new int[2], eightList[] = new int[2], nineList[] = new int[1], tenList[] = new int[1];

        Arrays.fill(oneList, 1);
        Arrays.fill(twoList, 2);
        Arrays.fill(threeList, 3);
        Arrays.fill(fourList, 4);
        Arrays.fill(fiveList, 5);
        Arrays.fill(sixList, 6);
        Arrays.fill(sevenList, 7);
        Arrays.fill(eightList, 8);
        Arrays.fill(nineList, 9);
        Arrays.fill(tenList, 10);

        return (int[]) concatArrays(zeroList, oneList, twoList, threeList, fourList, fiveList, sixList, sevenList, eightList, nineList, tenList);
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
        int base = ThreadLocalRandom.current().nextInt(0, goalsList.length);

        return base;
    }
//
//    private int[] generateBases (boolean home, boolean away) {
//        int homeBase = home ? ThreadLocalRandom.current().nextInt(0, goalsList.length + 1);
//        int awayBase = ThreadLocalRandom.current().nextInt(0, goalsList.length + 1);
//
//        return new int[] { homeBase, awayBase};
//    }

    public Map<Team, Integer> computeScore () {
        HashMap<Team, Integer> matchScore = new HashMap<>();
        int[] bases = new int[] {generateBase(), generateBase()};

        int homeScore = goalsList[bases[0]], awayScore = goalsList[bases[1]];

        if ((home.getAttack() > away.getDefence() + 4) && homeScore == 0 || homeScore == 1) {
                bases[0] = generateBase();
                homeScore = goalsList[bases[0]];
        }

        if ((home.getDefence() > away.getAttack() + 5) && awayScore > 3) {

                bases[1] = generateBase();
                awayScore = goalsList[bases[1]];
        }

        if (away.getAttack() > home.getDefence() + 4) {
            if (awayScore == 0 || awayScore == 1) {
                bases[1] = generateBase();
                awayScore = goalsList[bases[1]];
            }
        }

        if (away.getDefence() > home.getAttack() + 5) {
            if (homeScore > 3) {
                bases[0] = generateBase();
                homeScore = goalsList[bases[1]];
            }
        }

        if (homeScore > 4 && home.getAttack() < 79) {
            bases[0] = generateBase();
            homeScore = goalsList[bases[0]];
        }

        matchScore.put(home, homeScore);
        matchScore.put(away, awayScore);

        System.out.println(Arrays.toString(teamGoalsList()) + "\n" + teamGoalsList().length);
        return matchScore;
    }

}