package com.biedronka.biedronka_recipes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Zwracamy nazwę pliku HTML (index.html) w folderze templates
        return "index";
    }
}

