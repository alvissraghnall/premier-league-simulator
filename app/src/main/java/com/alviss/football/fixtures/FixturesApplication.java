package com.alviss.football.fixtures;

import java.util.Arrays;

public class FixturesApplication {
  
  public static String[] teams = new String[20];
  
  public static void main(String[] args) {
    createTeams();
    System.out.println(Arrays.toString(teams));
    System.out.println(Arrays.toString(createTeams()));
    
  }
  
  private static String[] createTeams(){
    for(int i = 0, j = 1; i < teams.length; ++i, j++) {
      teams[i] = "Team " + j;
    }
    return teams;
  }
  
  public List<String> makeFixtures() {
    
  }
}
