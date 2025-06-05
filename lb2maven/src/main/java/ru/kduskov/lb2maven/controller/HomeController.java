package ru.kduskov.lb2maven.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String redirectToMain() {
        return "redirect:/create.html";
    }

    @GetMapping("/swagger")
    public String redirectToOpenApi() {
        return "redirect:/swagger_index.html";
    }
}
