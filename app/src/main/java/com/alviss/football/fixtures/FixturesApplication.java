package com.alviss.football.fixtures;

import java.util.*;

import com.alviss.football.sim.Result;
import com.alviss.football.sim.Simulation;
//import com.fasterxml.jackson.core.JsonParseException;
import com.alviss.football.sim.SimulationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class FixturesApplication {

    public static String[] teamsArray = new String[20];

    private Team[] teams;

    private ObjectMapper objectMapper;
    private MappingJackson2HttpMessageConverter springMvcJacksonConverter;

    @Value("classpath:json/teams.json")
    private Resource teamsFile;

    private ApplicationContext ctx;

    public FixturesApplication(MappingJackson2HttpMessageConverter springMvcJacksonConverter, final ApplicationContext ctx, Team[] fillTeams) throws IOException {
        this.ctx = ctx;
        this.objectMapper = springMvcJacksonConverter.getObjectMapper();
        System.out.println("====\n\n\n" + this.objectMapper);
        this.teams = fillTeams;
    }

    // private Team[] fillTeams() throws IOException {
    //     // String teamsFile = "resources/json/teams.json";
    //     Resource resource = ctx.getResource("classpath:json/teams.json");

    //     InputStream is = resource.getInputStream();
    //     Team[] teams = objectMapper.readValue(is, Team[].class);
    //     // Team[] teams = new Team[] {new Team()};
    //     return teams;
    // }

    // private InputStream getFileFromResourceAsStream(String fileName) {
    // ClassLoader classLoader = getClass().getClassLoader();
    // InputStream inputStream = classLoader.getResourceAsStream(fileName);
    //// System.out.println(FixturesApplication.class.);
    //
    // if (inputStream == null) {
    // throw new IllegalArgumentException("file not found! " + fileName);
    // } else {
    // return inputStream;
    // }
    // }

    //
    // private static String[] createTeams(){
    // for(int i = 0, j = 1; i < teams.length; ++i, j++) {
    // teams[i] = "Team " + j;
    // }
    // return teams;
    // }
    //

    // public List<List<HashMap<String, Result>>> generateScores
    // (List<List<HashMap<String, String>>> fixtures) {
    // List<HashMap<String, Result>> matchDayScores = new ArrayList<HashMap<String,
    // Result>>();
    // List<List<HashMap<String, Result>>> allScores = new
    // ArrayList<List<HashMap<String, Result>>>();
    // HashMap<String, Result> score = new HashMap<>();
    // Simulation sim;
    // Map<Team, Integer> teamScore = new HashMap<>();
    // String home, away;
    // Team homeTeam, awayTeam;

    // for (List<HashMap<String, String>> matchDays : fixtures ) {
    // for (HashMap<String, String> individualFixture : matchDays) {
    // home = individualFixture.get("home");
    // away = individualFixture.get("away");
    // homeTeam = Team.getByName(teams, home);
    // awayTeam = Team.getByName(teams, away);
    // sim = new Simulation(homeTeam, awayTeam);
    // teamScore = sim.computeScore();
    // sim.computeScore().entrySet().forEach(entry -> {
    // System.out.println(entry.getKey().getName() + " : " +
    // entry.getValue().toString());
    // });
    // Team finalHomeTeam = homeTeam;
    // score.put("home", new Result(teamScore.keySet().stream().filter(entry ->
    // entry.equals(finalHomeTeam)).toString(), teamScore.get(homeTeam)));
    // Team finalAwayTeam = awayTeam;
    // score.put("away", new Result(teamScore.keySet().stream().filter(entry ->
    // entry.equals(finalAwayTeam)).toString(), teamScore.get(awayTeam)));

    // matchDayScores.add(score);
    // }
    // allScores.add(matchDayScores);
    // }
    // return allScores;
    // }

    // public List<List<HashMap<String, String>>> makeFixtures(Team[] teams) {
    // List<String> teamsList = new ArrayList<>();
    // List<List<HashMap<String, String>>> fixtures = new ArrayList<>();

    // for(Team team : teams) {
    // teamsList.add(team.getName());
    // }
    // int length = teamsList.size() - 1;
    // byte numOfDays = (byte) (length - 1);
    // byte roundRobin = (byte) (numOfDays * 2);
    // byte matchesPerRound = (byte) (length / 2);
    // String constantTeam = teamsList.remove(0);

    // for (byte robin = 0; robin < 2; robin++) {
    // for(byte day = 0; day <= numOfDays; day++) {
    // System.out.println("=====================================");
    // List<HashMap<String, String>> matchDay = new ArrayList<>();
    // System.out.println(String.format("Day %d", robin == 0 ? day + 1 : day + 20));
    // for(int match = 0; match < matchesPerRound; ++match) {
    // String home = teamsList.get(match - day >= 0 ? match - day : match - day +
    // 19);
    // String away = teamsList.get(length - 1 - day - match >= 0 ? length - 1 - day
    // - match : length - 1 - day - match + 19);
    // // String away = teamsList.get(match);
    // if(match == 9) home = constantTeam;

    // HashMap<String, String> roundFixes = new HashMap<String, String>();;

    // roundFixes.put("home", robin == 0 ? home : away);
    // roundFixes.put("away", robin == 0 ? away : home);
    // // if(day < numOfDays) {
    // // System.out.println(String.format("%s vs %s", teamsList.get(home),
    // teamsList.get(away)));
    // // } else {
    // // System.out.println(String.format("%s vs %s", teamsList.get(away),
    // teamsList.get(home)));;
    // // }
    // matchDay.add(roundFixes);
    // // roundFixes.clear();

    // }
    // System.out.println("=====================================");
    // System.out.println(matchDay.toString());
    // fixtures.add(matchDay);
    // }
    // }

    // System.out.println(fixtures.size());
    // System.out.println(teamsList.toString());
    // System.out.println(fixtures.toString());
    // return fixtures;
    // }

    public List<List<Result>> simulate() {
        // this.teams = fillTeams();

        System.out.println("====\n\n\n" + this.objectMapper);
        return SimulationApplication.simulate(teams);
    }
}
