package com.alviss.football.config;

import com.alviss.football.fixtures.Team;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class BeansConfig {
    
    @Value("classpath:json/teams.json")
    private Resource teamsFile;

    // @Autowired private ApplicationContext ctx;

    @Bean
    public ObjectMapper objectMapper () {
        return new ObjectMapper();
    }

    @Bean
    public Team[] fillTeams(ObjectMapper objectMapper) throws IOException {
        // String teamsFile = "resources/json/teams.json";
        // Resource resource = ctx.getResource("classpath:json/teams.json");

        InputStream is = teamsFile.getInputStream();
        Team[] teams = objectMapper.readValue(is, Team[].class);
        // Team[] teams = new Team[] {new Team()};
        return teams;
    }
}
