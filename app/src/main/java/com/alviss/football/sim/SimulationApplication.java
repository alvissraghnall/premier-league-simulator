package com.alviss.football.sim;

import com.alviss.football.fixtures.FixturesGenerator;
import com.alviss.football.fixtures.Match;
import com.alviss.football.fixtures.Team;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulationApplication {

    public static List<List<Result>> simulate (Team[] teams) {
        Simulation sim;
        Map<Team, Integer> score;
        Result result;
        final List<List<Result>> fixturesWithScores = new LinkedList<>();

        FixturesGenerator fixgen = new FixturesGenerator();
        List<List<Match>> fixtures = fixgen.generate(teams);

        for (List<Match> roundFixtures : fixtures) {
            List<Result> matchDay = new ArrayList<>();
            for (Match match : roundFixtures) {
                sim = new Simulation(match.getHomeTeam(), match.getAwayTeam());
                score = sim.getScore();
                result = new Result(match, score);

                matchDay.add(result);
            }
//            System.out.println(matchDay.size());
            fixturesWithScores.add(matchDay);
        }
        return fixturesWithScores;
    }
}
