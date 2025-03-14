package com.alviss.football.fixtures;

import java.util.*;

import com.alviss.football.sim.Result;
//import com.fasterxml.jackson.core.JsonParseException;
import com.alviss.football.sim.SimulationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

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

    public List<List<Result>> simulate() {
        // this.teams = fillTeams();

        System.out.println("====\n\n\n" + this.objectMapper);
        return SimulationApplication.simulate(teams);
    }
}
