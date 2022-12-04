package com.alviss.football.fixtures;


import java.util.Arrays;

public class Team {
	private String name;
	private String shortName;
	private int overall;
	private int defence;
	private int midfield;
	private int attack;
	
	
	public String getName() {
		return name;
	}
	// void setName(String name) {
	// 	this.name = name;
	// }
	public String getShortName() {
		return shortName;
	}
	// void setShortName(String intName) {
	// 	this.shortName = intName;
	// }
	public int getOverall() {
		return overall;
	}
	// void setOverall(int overall) {
	// 	this.overall = overall;
	// }
	public int getDefence() {
		return defence;
	}
	// void setDefence(int defence) {
	// 	this.defence = defence;
	// }
	public int getMidfield() {
		return midfield;
	}
	// void setMidfield(int midfield) {
	// 	this.midfield = midfield;
	// }
	public int getAttack() {
		return attack;
	}
	// void setAttack(int attack) {
	// 	this.attack = attack;
	// }

	public static Team getByName (Team[] teams, String teamName) {
		return Arrays.stream(teams).filter(team -> team.getName() == teamName).toList().get(0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Team team = (Team) o;

		if (overall != team.overall) return false;
		if (defence != team.defence) return false;
		if (midfield != team.midfield) return false;
		if (attack != team.attack) return false;
		if (name != null ? !name.equals(team.name) : team.name != null) return false;
		return shortName != null ? shortName.equals(team.shortName) : team.shortName == null;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
		result = 31 * result + overall;
		result = 31 * result + defence;
		result = 31 * result + midfield;
		result = 31 * result + attack;
		return result;
	}
}
