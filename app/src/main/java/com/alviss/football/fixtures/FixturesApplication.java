package com.alviss.football.fixtures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;


public class FixturesApplication {
  
  public static String[] teams = new String[20];
  
  public static void main(String[] args) throws IOException, JsonParseException {
    FixturesApplication app = new FixturesApplication();
    String teamsFile = "resources/json/teams.json";
    
    InputStream is = app.getFileFromResourceAsStream(teamsFile);
//    printInputStream(is);
    
    ObjectMapper objectMapper = new ObjectMapper();
    Team[] teams = objectMapper.readValue(is, Team[].class);
    
    System.out.println(Arrays.deepToString(teams));
    System.out.println(teams[0].getAttack());
    
    app.makeFixtures(teams);
//    JsonFactory jsonFactory = new JsonFactory();
//    JsonParser jp = jsonFactory.createJsonParser(is);
    
    //System.out.println(Arrays.toString(teams));
    //System.out.println(Arrays.toString(createTeams()));
    
  }
  
  private InputStream getFileFromResourceAsStream(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);
//    System.out.println(FixturesApplication.class.);
  
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
  }
  
  private static void printInputStream(InputStream is) {
    try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
      BufferedReader reader = new BufferedReader(streamReader)) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
//  
//  private static String[] createTeams(){
//    for(int i = 0, j = 1; i < teams.length; ++i, j++) {
//      teams[i] = "Team " + j;
//    }
//    return teams;
//  }
//  
  
 
  public List<String> makeFixtures(Team[] teams) {
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
	  
	  for(byte day = 0; day < numOfDays; day++) {
		  System.out.println("=====================================");
		  List<HashMap<String, String>> matchDay = new ArrayList<>();
		  System.out.println(String.format("Day %d", day + 1));
		  for(int match = 0; match < matchesPerRound; ++match) {
			  String home = teamsList.get(match - day >= 0 ? match - day : match - day + 19);
			  String away = teamsList.get(length - 1 - day - match >= 0 ? length - 1 - day - match : length - 1 - day - match + 19);
//			  String away = teamsList.get(match);
			  if(match == 9) home = constantTeam;
			  

			  HashMap<String, String> roundFixes = new HashMap<String, String>();;
			 
			  roundFixes.put("home", home);
			  roundFixes.put("away", away);
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
	  
	  
	  System.out.println(fixtures.size());
	  System.out.println(teamsList.toString());
	  System.out.println(fixtures.toString());
	  return List.of("Hello!");
  }
}
