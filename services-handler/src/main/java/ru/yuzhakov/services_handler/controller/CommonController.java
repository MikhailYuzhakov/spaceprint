package ru.yuzhakov.services_handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {

    @GetMapping("/static/css/style.css")
    public String getStyles() {
        return "../static/css/style.css";
    }

}
