package com.alviss.football.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String indexPage (Model model) {
        model.addAttribute("title", "Premier League Simulator");

        return "home";
    }
}
