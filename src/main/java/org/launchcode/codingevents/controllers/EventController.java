package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

   // private static List<Event> events = new ArrayList<>();
    @GetMapping
    public String displayAllEvents(Model model){

        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }
    // lives at /events/create
   @GetMapping("create")
    public String renderCreateEventForm(Model model){
       model.addAttribute("title", "Create Event");
       model.addAttribute(new Event());
       model.addAttribute("types",EventType.values());
        return "events/create";
    }

    // lives at /events/create
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:/events";
    }
    @GetMapping("delete")
    public String displayDeleteEvent(Model model){
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam (required = false) int[] eventIds){
        if(eventIds!=null) {
            for (int id : eventIds) {
                EventData.removeEvent(id);
            }
        }
          return "redirect:/events";
    }
    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {

        Event editEvent = EventData.getById(eventId);
        // controller code will go here
        model.addAttribute("event",editEvent);
        String title = "Edit event "+editEvent.getName() +"( "+editEvent.getId()+")";
        model.addAttribute("title",title);
        return "events/edit";
    }
    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description,String location, String contactEmail) {

        Event eventEdited  = EventData.getById(eventId);
        eventEdited.setName(name);
        eventEdited.setDescription(description);
        eventEdited.setLocation(location);
        eventEdited.setContactEmail(contactEmail);
        return "redirect:/events";
    }
}
