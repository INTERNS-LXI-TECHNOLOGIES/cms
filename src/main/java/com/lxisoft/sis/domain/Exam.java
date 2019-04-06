package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_date")
    private Instant examDate;

    @Column(name = "starting_time")
    private String startingTime;

    @Column(name = "duration")
    private String duration;

    @ManyToOne
    @JsonIgnoreProperties("exams")
    private ExamSchedule examSchedule;

    @OneToOne
    @JoinColumn(unique = true)
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "exam_halls",
               joinColumns = @JoinColumn(name = "exam_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "halls_id", referencedColumnName = "id"))
    private Set<ExamHall> halls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExamDate() {
        return examDate;
    }

    public Exam examDate(Instant examDate) {
        this.examDate = examDate;
        return this;
    }

    public void setExamDate(Instant examDate) {
        this.examDate = examDate;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public Exam startingTime(String startingTime) {
        this.startingTime = startingTime;
        return this;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getDuration() {
        return duration;
    }

    public Exam duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ExamSchedule getExamSchedule() {
        return examSchedule;
    }

    public Exam examSchedule(ExamSchedule examSchedule) {
        this.examSchedule = examSchedule;
        return this;
    }

    public void setExamSchedule(ExamSchedule examSchedule) {
        this.examSchedule = examSchedule;
    }

    public Subject getSubject() {
        return subject;
    }

    public Exam subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<ExamHall> getHalls() {
        return halls;
    }

    public Exam halls(Set<ExamHall> examHalls) {
        this.halls = examHalls;
        return this;
    }

    public Exam addHalls(ExamHall examHall) {
        this.halls.add(examHall);
        examHall.getExams().add(this);
        return this;
    }

    public Exam removeHalls(ExamHall examHall) {
        this.halls.remove(examHall);
        examHall.getExams().remove(this);
        return this;
    }

    public void setHalls(Set<ExamHall> examHalls) {
        this.halls = examHalls;
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
        Exam exam = (Exam) o;
        if (exam.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), exam.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Exam{" +
            "id=" + getId() +
            ", examDate='" + getExamDate() + "'" +
            ", startingTime='" + getStartingTime() + "'" +
            ", duration='" + getDuration() + "'" +
            "}";
    }
}
