package ru.kduskov.lb3maven.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToMain() {
        return "redirect:/main.html";
    }

    @GetMapping("/swagger")
    public String redirectToOpenApi() {
        return "redirect:/swagger_index.html";
    }
}
