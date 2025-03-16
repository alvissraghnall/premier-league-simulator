package com.alviss.football.fixtures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class TeamTest {

    @Test
    void testGetName() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals("Manchester United", team.getName());
    }

    @Test
    void testGetShortName() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals("MUFC", team.getShortName());
    }

    @Test
    void testGetOverall() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals(85, team.getOverall());
    }

    @Test
    void testGetDefence() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals(80, team.getDefence());
    }

    @Test
    void testGetMidfield() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals(82, team.getMidfield());
    }

    @Test
    void testGetAttack() {
        Team team = new Team("Manchester United", "MUFC", 85, 80, 82, 88);
        assertEquals(88, team.getAttack());
    }

    @Test
    void testEquals() {
        Team team1 = new Team("Chelsea", "CHE", 84, 82, 83, 85);
        Team team2 = new Team("Chelsea", "CHE", 84, 82, 83, 85);
        Team team3 = new Team("Arsenal", "ARS", 83, 81, 82, 84);

        assertTrue(team1.equals(team2));
        assertFalse(team1.equals(team3));
    }

    @Test
    void testHashCode() {
        Team team1 = new Team("Liverpool", "LIV", 87, 85, 86, 88);
        Team team2 = new Team("Liverpool", "LIV", 87, 85, 86, 88);

        assertEquals(team1.hashCode(), team2.hashCode());
    }

    @Test
    void testGetByName() {
        Team team1 = new Team("Manchester City", "MCI", 88, 86, 89, 90);
        Team team2 = new Team("Tottenham Hotspur", "TOT", 84, 83, 85, 86);

        Team[] teams = {team1, team2};

        Team foundTeam = Team.getByName(teams, "Manchester City");
        assertEquals(team1, foundTeam);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Team.getByName(teams, "Non-existent Team");
        });
    }
}
