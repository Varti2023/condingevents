package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.EventDetails;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("events")
public class EventController {

   // private static List<Event> events = new ArrayList<>();
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    //findAll, save, findById
    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model){

        if(categoryId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("events", eventRepository.findAll());
        }else{
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if(result.isEmpty()){
                model.addAttribute("title", "Invalid Category ID "+categoryId);
            }else{
                EventCategory category = result.get();
                model.addAttribute("title","Events is category: "+category.getName());
                model.addAttribute("events",category.getEvents());
            }
        }
        return "events/index";
    }
    // lives at /events/create
   @GetMapping("create")
    public String renderCreateEventForm(Model model){
       model.addAttribute("title", "Create Event");
       model.addAttribute(new Event());
       model.addAttribute("categories",eventCategoryRepository.findAll());
       return "events/create";
    }

    // lives at /events/create
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        eventRepository.save(newEvent);
        return "redirect:/events";
    }
    @GetMapping("delete")
    public String displayDeleteEvent(Model model){
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam (required = false) int[] eventIds){
        if(eventIds!=null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
          return "redirect:/events";
    }
    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {

        Optional<Event> result = eventRepository.findById(eventId);
        if(result.isEmpty()){
            model.addAttribute("title","Invalid Event Id: "+eventId);
        }else {
            Event editEvent = result.get();
            model.addAttribute("event", editEvent);
            String title = "Edit event " + editEvent.getName() + "( " + editEvent.getId() + ")";
            model.addAttribute("title", title);
        }
        return "events/edit";
    }
    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description,String location, String contactEmail) {

        Optional<Event> results  = eventRepository.findById(eventId);
        Event eventEdited = results.get();
        eventEdited.setName(name);
        eventEdited.getEventDetails().setDescription(description);
        eventEdited.getEventDetails().setLocation(location);
        eventEdited.getEventDetails().setContactEmail(contactEmail);
        eventRepository.save(eventEdited);
        return "redirect:/events";
    }

    @GetMapping("detail")
    public String displayEventDetail(@RequestParam Integer eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        if(result.isEmpty()){
            model.addAttribute("title","Invalid Event Id: "+eventId);
        }else{
            Event event  = result.get();
            model.addAttribute("title",event.getName() + " details");
            model.addAttribute("event" ,event);
            model.addAttribute("tags" ,event.getTags());
        }
        return "events/detail";
    }
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
            Optional<Event> result = eventRepository.findById(eventId);
            Event event = result.get();
            model.addAttribute("Title","Add tag to: "+event.getName());
            model.addAttribute("tags",tagRepository.findAll());
            EventTagDTO eventTag = new EventTagDTO();
            eventTag.setEvent(event);
            model.addAttribute("eventTag",eventTag);
            return "events/add-tag.html";
    }
    @PostMapping("add-tag")
    public String ProcessAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag,
                                    Errors errors,
                                    Model model){

            if(!errors.hasErrors()){
                Event event = eventTag.getEvent();
                Tag tag = eventTag.getTag();
                    if(!event.getTags().contains(tag)){
                        event.addTag(tag);
                        eventRepository.save(event);
                    }
                    return "redirect:detail?eventId=" +event.getId();
            }
            return "redirect:add-tag";
        }

}
