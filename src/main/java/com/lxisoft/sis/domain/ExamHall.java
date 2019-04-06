package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExamHall.
 */
@Entity
@Table(name = "exam_hall")
public class ExamHall implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hall_number")
    private Integer hallNumber;

    @Column(name = "batch")
    private String batch;

    @Column(name = "roll_num_from")
    private Integer rollNumFrom;

    @Column(name = "roll_num_to")
    private Integer rollNumTo;

    @Column(name = "invigialtor")
    private String invigialtor;

    @ManyToMany(mappedBy = "halls")
    @JsonIgnore
    private Set<Exam> exams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHallNumber() {
        return hallNumber;
    }

    public ExamHall hallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public void setHallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
    }

    public String getBatch() {
        return batch;
    }

    public ExamHall batch(String batch) {
        this.batch = batch;
        return this;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getRollNumFrom() {
        return rollNumFrom;
    }

    public ExamHall rollNumFrom(Integer rollNumFrom) {
        this.rollNumFrom = rollNumFrom;
        return this;
    }

    public void setRollNumFrom(Integer rollNumFrom) {
        this.rollNumFrom = rollNumFrom;
    }

    public Integer getRollNumTo() {
        return rollNumTo;
    }

    public ExamHall rollNumTo(Integer rollNumTo) {
        this.rollNumTo = rollNumTo;
        return this;
    }

    public void setRollNumTo(Integer rollNumTo) {
        this.rollNumTo = rollNumTo;
    }

    public String getInvigialtor() {
        return invigialtor;
    }

    public ExamHall invigialtor(String invigialtor) {
        this.invigialtor = invigialtor;
        return this;
    }

    public void setInvigialtor(String invigialtor) {
        this.invigialtor = invigialtor;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public ExamHall exams(Set<Exam> exams) {
        this.exams = exams;
        return this;
    }

    public ExamHall addExams(Exam exam) {
        this.exams.add(exam);
        exam.getHalls().add(this);
        return this;
    }

    public ExamHall removeExams(Exam exam) {
        this.exams.remove(exam);
        exam.getHalls().remove(this);
        return this;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
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
        ExamHall examHall = (ExamHall) o;
        if (examHall.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examHall.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamHall{" +
            "id=" + getId() +
            ", hallNumber=" + getHallNumber() +
            ", batch='" + getBatch() + "'" +
            ", rollNumFrom=" + getRollNumFrom() +
            ", rollNumTo=" + getRollNumTo() +
            ", invigialtor='" + getInvigialtor() + "'" +
            "}";
    }
}
