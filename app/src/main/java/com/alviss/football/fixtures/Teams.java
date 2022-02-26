package com.alviss.football.fixtures;


class Team {
	String name;
	String shortName;
	int overall;
	int defence;
	int midfield;
	int attack;
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	String getShortName() {
		return shortName;
	}
	void setShortName(String intName) {
		this.shortName = intName;
	}
	int getOverall() {
		return overall;
	}
	void setOverall(int overall) {
		this.overall = overall;
	}
	int getDefence() {
		return defence;
	}
	void setDefence(int defence) {
		this.defence = defence;
	}
	int getMidfield() {
		return midfield;
	}
	void setMidfield(int midfield) {
		this.midfield = midfield;
	}
	int getAttack() {
		return attack;
	}
	void setAttack(int attack) {
		this.attack = attack;
	}
	  
}
