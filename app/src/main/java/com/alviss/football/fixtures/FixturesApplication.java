package com.alviss.football.fixtures;

import java.util.*;

import com.alviss.football.sim.Score;
import com.alviss.football.sim.Simulation;
//import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;


public class FixturesApplication {
  
  public static String[] teamsArray = new String[20];

  private Team[] teams;
  
  private final ObjectMapper objectMapper;

  @Value("classpath:json/teams.json")
  private Resource teamsFile;

  public FixturesApplication (final ObjectMapper objectMapper) throws IOException {
      this.teams = fillTeams();
      this.objectMapper = objectMapper;
  }

  private Team[] fillTeams () throws IOException {
//      String teamsFile = "resources/json/teams.json";
      InputStream is = teamsFile.getInputStream();
      Team[] teams = objectMapper.readValue(is, Team[].class);
//      Team[] teams = new Team[] {new Team()};
      return teams;
  }

  
//  private InputStream getFileFromResourceAsStream(String fileName) {
//    ClassLoader classLoader = getClass().getClassLoader();
//    InputStream inputStream = classLoader.getResourceAsStream(fileName);
////    System.out.println(FixturesApplication.class.);
//
//    if (inputStream == null) {
//      throw new IllegalArgumentException("file not found! " + fileName);
//    } else {
//      return inputStream;
//    }
//  }

//  
//  private static String[] createTeams(){
//    for(int i = 0, j = 1; i < teams.length; ++i, j++) {
//      teams[i] = "Team " + j;
//    }
//    return teams;
//  }
//  

    public List<List<HashMap<String, Score>>> generateScores (List<List<HashMap<String, String>>> fixtures) {
        List<HashMap<String, Score>> matchDayScores = new ArrayList<HashMap<String, Score>>();
        List<List<HashMap<String, Score>>> allScores = new ArrayList<List<HashMap<String, Score>>>();
        HashMap<String, Score> score = new HashMap<>();
        Simulation sim;
        Map<Team, Integer> teamScore = new HashMap<>();
        String home, away;
        Team homeTeam, awayTeam;

        for (List<HashMap<String, String>> matchDays : fixtures ) {
            for (HashMap<String, String> individualFixture : matchDays) {
                home = individualFixture.get("home");
                away = individualFixture.get("away");
                homeTeam = Team.getByName(teams, home);
                awayTeam = Team.getByName(teams, away);
                sim = new Simulation(homeTeam, awayTeam);
                teamScore = sim.computeScore();
                sim.computeScore().entrySet().forEach(entry -> {
                    System.out.println(entry.getKey().getName() + " : " + entry.getValue().toString());
                });
                Team finalHomeTeam = homeTeam;
                score.put("home", new Score(teamScore.keySet().stream().filter(entry -> entry.equals(finalHomeTeam)).toString(), teamScore.get(homeTeam)));
                Team finalAwayTeam = awayTeam;
                score.put("away", new Score(teamScore.keySet().stream().filter(entry -> entry.equals(finalAwayTeam)).toString(), teamScore.get(awayTeam)));

                matchDayScores.add(score);
            }
            allScores.add(matchDayScores);
        }
      return allScores;
    }
 
  public List<List<HashMap<String, String>>> makeFixtures(Team[] teams) {
	  List<String> teamsList = new ArrayList<>();
	  List<List<HashMap<String, String>>> fixtures = new ArrayList<>();

	  for(Team team : teams) {
		  teamsList.add(team.getName());
	  }
	  int length = teamsList.size() - 1;
	  byte numOfDays = (byte) (length - 1);
	  byte roundRobin = (byte) (numOfDays * 2);
	  byte matchesPerRound = (byte) (length / 2);
	  String constantTeam = teamsList.remove(0);

      for (byte robin = 0; robin < 2; robin++) {
          for(byte day = 0; day <= numOfDays; day++) {
              System.out.println("=====================================");
              List<HashMap<String, String>> matchDay = new ArrayList<>();
              System.out.println(String.format("Day %d", robin == 0 ? day + 1 : day + 20));
              for(int match = 0; match < matchesPerRound; ++match) {
                  String home = teamsList.get(match - day >= 0 ? match - day : match - day + 19);
                  String away = teamsList.get(length - 1 - day - match >= 0 ? length - 1 - day - match : length - 1 - day - match + 19);
//			  String away = teamsList.get(match);
                  if(match == 9) home = constantTeam;


                  HashMap<String, String> roundFixes = new HashMap<String, String>();;

                  roundFixes.put("home", robin == 0 ? home : away);
                  roundFixes.put("away", robin == 0 ? away : home);
//			  if(day < numOfDays) {
//				  System.out.println(String.format("%s vs %s", teamsList.get(home), teamsList.get(away)));
//			  } else {
//					System.out.println(String.format("%s vs %s", teamsList.get(away), teamsList.get(home)));;
//			  }
                  matchDay.add(roundFixes);
//			  roundFixes.clear();

              }
              System.out.println("=====================================");
              System.out.println(matchDay.toString());
              fixtures.add(matchDay);
          }
      }
	  

	  
	  
	  System.out.println(fixtures.size());
	  System.out.println(teamsList.toString());
	  System.out.println(fixtures.toString());
	  return fixtures;
  }
}
