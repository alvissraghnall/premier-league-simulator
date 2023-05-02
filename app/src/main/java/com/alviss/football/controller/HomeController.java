package com.alviss.football.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.alviss.football.service.HomeService;

@Controller
@RequestMapping
public class HomeController {

    private final HomeService homeService;

    public HomeController (final HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home (Model model) {
        model.addAttribute("title", "Premier League Simulator");
        model.addAttribute("teams", homeService.getTeams());

        return "home";
    }

    @GetMapping("/pl")
    public String pl (Model model) {
        return "simulate";
    }

    @PostMapping("/simulate")
    public String simulate (Model model) throws IOException {
        model.addAttribute("teamDataList", homeService.simulate());
        return "simulate";
    }
}
