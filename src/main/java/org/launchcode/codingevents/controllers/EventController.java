package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    private static List<String> events = new ArrayList<>();
    @GetMapping
    public String getEvents(Model model){
        model.addAttribute("title", "All Events");
        model.addAttribute("events", events);

        return "events/index";
    }
// lives at /event/create
   @GetMapping("create")
    public String renderCreateEventForm(){
        return "events/create";
    }
}
