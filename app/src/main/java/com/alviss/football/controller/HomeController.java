package com.alviss.football.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alviss.football.service.HomeService;

@Controller
@RequestMapping
public class HomeController {

  private final HomeService homeService;

  public HomeController(final HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("title", "Premier League Simulator");
    model.addAttribute("teams", homeService.getTeams());

    return "home";
  }

  @GetMapping("/pl")
  public String pl(Model model) {
    return "simulate";
  }

  // @PostMapping("/simulate")
  // public String simulate(@RequestParam(name = "days", required = false) int
  // days, Model model) throws IOException {
  // model.addAttribute("teamDataList", homeService.simulate());
  // return "simulate";
  // }

  @PostMapping("/simulate")
  public String simulate(@RequestParam(name = "days", required = false) Integer days,
      Model model,
      RedirectAttributes redirectAttributes) {
    try {
      if (days != null) {
        if (days <= 0) {
          redirectAttributes.addFlashAttribute("error", "Number of days must be a positive integer");
          return "redirect:/home";
        }

        model.addAttribute("teamDataList", homeService.simulate(days));
        model.addAttribute("simulatedDays", days);
      } else {
        model.addAttribute("teamDataList", homeService.simulate());
        model.addAttribute("simulatedDays", "all");
      }

      return "simulate";
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", "Failed to simulate: " + e.getMessage());
      return "redirect:/error";
    } catch (Exception e) {
      // Handle any other exceptions
      redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
      return "redirect:/error";
    }
  }
}
