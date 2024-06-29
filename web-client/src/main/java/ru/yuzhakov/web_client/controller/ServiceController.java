package ru.yuzhakov.web_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yuzhakov.web_client.model.MyService;
import ru.yuzhakov.web_client.service.MyServiceService;

@Controller
@RequestMapping("/services")
public class ServiceController {
    private final MyServiceService service;

    public ServiceController(MyServiceService service) {
        this.service = service;
    }

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("services", service.getAllServices());
        return "services";
    }

    @GetMapping("/{uri}")
    public String getServicePage(@PathVariable("uri") String uri, Model model) {
        MyService myService = service.getServiceByUri(uri);
        model.addAttribute("service", myService);
        return "/services/" + myService.getUri();
    }


}
