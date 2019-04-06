package com.lxisoft.sas.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sas.domain.enumeration.EventType;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category")
    private EventType eventCategory;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private Instant eventDate;

    @Column(name = "event_venue")
    private String eventVenue;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany
    @JoinTable(name = "event_cordinators",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cordinators_id", referencedColumnName = "id"))
    private Set<UserDomain> cordinators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventCategory() {
        return eventCategory;
    }

    public Event eventCategory(EventType eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    public void setEventCategory(EventType eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventName() {
        return eventName;
    }

    public Event eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public Event eventDate(Instant eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public Event eventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
        return this;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public Boolean isActive() {
        return active;
    }

    public Event active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<UserDomain> getCordinators() {
        return cordinators;
    }

    public Event cordinators(Set<UserDomain> userDomains) {
        this.cordinators = userDomains;
        return this;
    }

    public Event addCordinators(UserDomain userDomain) {
        this.cordinators.add(userDomain);
        return this;
    }

    public Event removeCordinators(UserDomain userDomain) {
        this.cordinators.remove(userDomain);
        return this;
    }

    public void setCordinators(Set<UserDomain> userDomains) {
        this.cordinators = userDomains;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", eventCategory='" + getEventCategory() + "'" +
            ", eventName='" + getEventName() + "'" +
            ", eventDate='" + getEventDate() + "'" +
            ", eventVenue='" + getEventVenue() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
