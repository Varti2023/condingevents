package org.launchcode.codingevents.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.Size;

@Entity
public class Event extends AbstractEntity{

    @NotBlank(message="Name is required")
    @Size(min =3, max=50 , message="Name must be between 3 to 50 characters.")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;
    @ManyToOne
    @NotNull(message="Category is required")
    private EventCategory eventCategory;

    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();
    public Event() {
    }
    public Event(String name, String location, EventCategory eventCategory) {
        this.name = name;
        this.eventCategory = eventCategory;
    }
    public EventCategory getEventCategory() {
        return eventCategory;
    }
    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public EventDetails getEventDetails() {
        return eventDetails;
    }
    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }
    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return name;
    }

}
