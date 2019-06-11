package com.lxisoft.sas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sas.domain.enumeration.Semester;

import com.lxisoft.sas.domain.enumeration.Department;

/**
 * A TimeTable.
 */
@Entity
@Table(name = "time_table")
public class TimeTable implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @OneToMany(mappedBy = "timeTable")
    private Set<DayOfWeek> days = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Semester getSemester() {
        return semester;
    }

    public TimeTable semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public TimeTable department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public TimeTable days(Set<DayOfWeek> dayOfWeeks) {
        this.days = dayOfWeeks;
        return this;
    }

    public TimeTable addDays(DayOfWeek dayOfWeek) {
        this.days.add(dayOfWeek);
        dayOfWeek.setTimeTable(this);
        return this;
    }

    public TimeTable removeDays(DayOfWeek dayOfWeek) {
        this.days.remove(dayOfWeek);
        dayOfWeek.setTimeTable(null);
        return this;
    }

    public void setDays(Set<DayOfWeek> dayOfWeeks) {
        this.days = dayOfWeeks;
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
        TimeTable timeTable = (TimeTable) o;
        if (timeTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeTable{" +
            "id=" + getId() +
            ", semester='" + getSemester() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
