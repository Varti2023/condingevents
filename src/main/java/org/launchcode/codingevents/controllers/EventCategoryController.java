package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("eventCategories")

public class EventCategoryController {

    @Autowired
     private EventCategoryRepository eventCategoryRepository;

        @GetMapping
        public String displayAllCategories(Model model){
            model.addAttribute("title","All Categories");
            model.addAttribute("categories",eventCategoryRepository.findAll());
            return "eventCategories/index";
        }

        @GetMapping("create")
        public String renderCreateEventCategoryForm(Model model){
            model.addAttribute("title","Create Category");
            model.addAttribute(new EventCategory());
            return "eventCategories/create";
        }

        @PostMapping("create")
        public String processCreateEventCategoryForm(@ModelAttribute @Valid EventCategory eventCategory,
                                                     Errors errors, Model model){

            if(errors.hasErrors()){
                model.addAttribute("title","Create Category");
                return "eventCategories/create";
            }
            eventCategoryRepository.save(eventCategory);
            return "redirect:/eventCategories";
        }

        @GetMapping("delete")
        public String displayDeleteEventCategory(Model model){
            model.addAttribute("title", "Delete Category");
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            return "eventCategories/delete";
        }

        @PostMapping("delete")
        public String deleteEvent(@RequestParam(required = false) int[] eventCategoryId){
            if(eventCategoryId!=null) {
                for (int id : eventCategoryId) {
                    eventCategoryRepository.deleteById(id);
                }
            }
            return "redirect:/eventCategories";
        }

}
