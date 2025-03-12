package ru.yuzhakov.services_handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {

    @GetMapping("/homepage")
    public String getHomePage() {
        return "/main-page/main-page";
    }

    @GetMapping("/static/css/style.css")
    public String getStyles() {
        return "../static/css/style.css";
    }

    @GetMapping("/static/css/main-page.css")
    public String getStylesMainPage() {
        return "../static/css/main-page.css";
    }

}
