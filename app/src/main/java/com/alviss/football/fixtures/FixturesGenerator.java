package com.alviss.football.fixtures;

import java.util.*;

public class FixturesGenerator {
    private final List<Team> teamsList = new ArrayList<>();
//
//    public List<List<Match>> generate(Team[] teams) {
//        final List<List<Match>> fixtures = new LinkedList<>();
//
//        teamsList.addAll(Arrays.asList(teams));
//
//
//        int length = teamsList.size();
//        final int numOfDays = length - 1;
//        int roundRobin = numOfDays * 2;
//        final int matchesPerRound = length / 2;
//        Team constantTeam = teamsList.remove(0);
//
//        for (byte robin = 0; robin < 2; robin++) {
//            for (int day = 0; day <= numOfDays; day++) {
//                System.out.println("=====================================");
//                List<Match> matchDay = new ArrayList<>();
//                System.out.printf("Day %d%n", robin == 0 ? day + 1 : day + 20);
//                for (int match = 0; match < matchesPerRound; ++match) {
//                    Team away = teamsList.get(length - match - day - 1);
//                    Team home = teamsList.get(
//                            match - day);
//                    // String away = teamsList.get(match);
//                    if (match == 9) home = constantTeam;
//
//                    // HashMap<String, String> roundFixes = new HashMap<String, String>();
//                    Match currentFixture;
//                    if (robin == 0) {
//                        currentFixture = new Match(home, away);
//                    } else {
//                        currentFixture = new Match(away, home);
//                    }
//                    matchDay.add(currentFixture);
//
////                    Match currentFixture = new Match(home, away);
//                    // if(day < numOfDays) {
//                    // System.out.println(String.format("%s vs %s", teamsList.get(home),
//                    // teamsList.get(away)));
//                    // } else {
//                    // System.out.println(String.format("%s vs %s", teamsList.get(away),
//                    // teamsList.get(home)));;
//                    // }
////                    matchDay.add(currentFixture);
//                    // roundFixes.clear();
//
//                }
//                System.out.println("=====================================");
//                System.out.println(matchDay.toString());
//                fixtures.add(matchDay);
//            }
//
//        }
//        // System.out.println(fixtures.size());
//        // System.out.println(teamsList.toString());
//        // System.out.println(fixtures.toString());
//        return fixtures;
//    }


    public List<List<Match>> generate(Team[] teams) {
        final List<List<Match>> fixtures = new LinkedList<>();

        teamsList.addAll(Arrays.asList(teams));

        final int playerCount = teamsList.size();
        final int rounds = playerCount - 1;
        final int half = playerCount / 2;
        Match currentFixture;
//        final List<List<Pairing>> fixtures = new ArrayList<>();

        final List<Integer> playerIndexes = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerIndexes.add(i);
        }
        playerIndexes.remove(0);

        for (int x = 0; x < 2; x++) {

            for (int round = 0; round < rounds; round++) {
                final List<Match> roundPairings = new ArrayList<>();

                final List<Integer> newPlayerIndexes = new ArrayList<>();
                newPlayerIndexes.add(0);
                newPlayerIndexes.addAll(playerIndexes);

                final List<Integer> firstHalf = newPlayerIndexes.subList(0, half);
                final List<Integer> secondHalf = new ArrayList<>(newPlayerIndexes.subList(half, playerCount));
                java.util.Collections.reverse(secondHalf);

                for (int i = 0; i < firstHalf.size(); i++) {
                    if (x == 0) {
                        currentFixture = new Match(teamsList.get(firstHalf.get(i)), teamsList.get(secondHalf.get(i)));
                    } else {
                        currentFixture = new Match(teamsList.get(secondHalf.get(i)), teamsList.get(firstHalf.get(i)));
                    }
                    roundPairings.add(currentFixture);
                }

                // rotating the list
                playerIndexes.add(playerIndexes.remove(0));
                fixtures.add(roundPairings);
            }

        }

        return fixtures;
    }
}
