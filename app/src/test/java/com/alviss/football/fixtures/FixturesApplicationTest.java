
package com.alviss.football.fixtures;

import com.alviss.football.league.LeagueTable;
import com.alviss.football.sim.SimulationApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FixturesApplicationTest {

  private FixturesApplication fixturesApplication;
  private MappingJackson2HttpMessageConverter springMvcJacksonConverter;
  private ApplicationContext ctx;
  private Team[] teams;
  private FixturesGenerator fixturesGenerator;
  private SimulationApplication simulationApplication;

  @BeforeEach
  void setUp() throws IOException {
    springMvcJacksonConverter = Mockito.mock(MappingJackson2HttpMessageConverter.class);
    ctx = Mockito.mock(ApplicationContext.class);
    teams = new Team[] { new Team("Team A", "TA", 80, 80, 80, 80), new Team("Team B", "TB", 85, 85, 85, 85) };
    fixturesGenerator = Mockito.mock(FixturesGenerator.class);
    simulationApplication = Mockito.mock(SimulationApplication.class);

    fixturesApplication = new FixturesApplication(springMvcJacksonConverter, ctx, teams);
  }

  @Test
  void testConstructor() {
    assertNotNull(fixturesApplication);
  }

  @Test
  void testSimulateWithNumberOfDays() {
    int numOfDays = 5;
    when(simulationApplication.getLeagueTable()).thenReturn(Mockito.mock(LeagueTable.class));
    doNothing().when(simulationApplication).simulate(anyInt());

    LeagueTable result = fixturesApplication.simulate(numOfDays);

    assertNotNull(result);
    verify(simulationApplication, times(1)).simulate(numOfDays);
  }

  @Test
  void testSimulateRemainder() {
    doNothing().when(simulationApplication).simulate(anyInt());
    doNothing().when(simulationApplication).simulateRemainder();
    when(simulationApplication.getLeagueTable()).thenReturn(Mockito.mock(LeagueTable.class));

    LeagueTable result = fixturesApplication.simulate();
    simulationApplication.simulateRemainder();

    assertNotNull(result);
    verify(simulationApplication, times(1)).simulateRemainder();
    verify(simulationApplication, times(1)).getLeagueTable();
  }
}
