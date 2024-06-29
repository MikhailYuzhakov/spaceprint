package ru.yuzhakov.web_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {

    @GetMapping("/static/css/styles.css")
    public String getStyles() {
        return "../static/css/styles.css";
    }

}
