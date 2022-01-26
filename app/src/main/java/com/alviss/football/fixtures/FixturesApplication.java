package com.alviss.football.fixtures;

import java.util.Arrays;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;


public class FixturesApplication {
  
  public static String[] teams = new String[20];
  
  public static void main(String[] args) throws IOException {
    FixturesApplication app = new FixturesApplication();
    String teamsFile = "json/teams.json";
    
    InputStream is = app.getFileFromResourceAsStream(teamsFile);
    printInputStream(is);
    
    //System.out.println(Arrays.toString(teams));
    //System.out.println(Arrays.toString(createTeams()));
    
  }
  
  private InputStream getFileFromResourceAsStream(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);
  
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
  
  private static String[] createTeams(){
    for(int i = 0, j = 1; i < teams.length; ++i, j++) {
      teams[i] = "Team " + j;
    }
    return teams;
  }
  
  //public List<String> makeFixtures() {
    
  //}
}
