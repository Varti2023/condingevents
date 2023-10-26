package org.launchcode.codingevents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import jakarta.validation.constraints.Size;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message="Name is required")
    @Size(min =3, max=50 , message="Name must be between 3 to 50 characters.")
    private String name;
    @Size(max=500, message = "Description too long.")
    private String description;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email. Try again")
    private String contactEmail;

    private EventType type;
    private String location;

    public Event() {
    }

    public Event(String name, String description, String location, String contactEmail, EventType type) {
        this.name = name;
        this.description=description;
        this.location=location;
        this.contactEmail=contactEmail;
        this.type = type;
    }

    public int getId() {
        return id;
    }


    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
