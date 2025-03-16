package com.alviss.football.sim;

public class Statistics {
  private int homePossession;
  private int awayPossession;
  private int homeShots;
  private int awayShots;
  private int homeShotsOnTarget;
  private int awayShotsOnTarget;
  private int homeCorners;
  private int awayCorners;
  private int homeFouls;
  private int awayFouls;
  private double homeXG;
  private double awayXG;

  public Statistics(
      int homePossession,
      int awayPossession,
      int homeShots,
      int awayShots,
      int homeShotsOnTarget,
      int awayShotsOnTarget,
      int homeCorners,
      int awayCorners,
      int homeFouls,
      int awayFouls,
      double homeXG,
      double awayXG) {
    this.awayCorners = awayCorners;
    this.homePossession = homePossession;
    this.awayPossession = awayPossession;
    this.homeShots = homeShots;
    this.awayShots = awayShots;
    this.homeShotsOnTarget = homeShotsOnTarget;
    this.awayShotsOnTarget = awayShotsOnTarget;
    this.homeCorners = homeCorners;
    this.homeFouls = homeFouls;
    this.awayFouls = awayFouls;
    this.homeXG = homeXG;
    this.awayXG = awayXG;
  }

  public int getHomePossession() {
    return homePossession;
  }

  public int getAwayPossession() {
    return awayPossession;
  }

  public int getHomeShots() {
    return homeShots;
  }

  public int getAwayShots() {
    return awayShots;
  }

  public int getHomeShotsOnTarget() {
    return homeShotsOnTarget;
  }

  public int getAwayShotsOnTarget() {
    return awayShotsOnTarget;
  }

  public int getHomeFouls() {
    return homeFouls;
  }

  public int getAwayFouls() {
    return awayFouls;
  }

  public int getHomeCorners() {
    return homeCorners;
  }

  public int getAwayCorners() {
    return awayCorners;
  }

  public double getHomeXG() {
    return homeXG;
  }

  public double getAwayXG() {
    return awayXG;
  }
}
