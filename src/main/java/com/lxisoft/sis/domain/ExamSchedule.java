package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sis.domain.enumeration.Department;

import com.lxisoft.sis.domain.enumeration.Semester;

/**
 * A ExamSchedule.
 */
@Entity
@Table(name = "exam_schedule")
public class ExamSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @OneToMany(mappedBy = "examSchedule")
    private Set<Exam> exams = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ExamSchedule title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ExamSchedule startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ExamSchedule endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return active;
    }

    public ExamSchedule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Department getDepartment() {
        return department;
    }

    public ExamSchedule department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() {
        return semester;
    }

    public ExamSchedule semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public ExamSchedule exams(Set<Exam> exams) {
        this.exams = exams;
        return this;
    }

    public ExamSchedule addExams(Exam exam) {
        this.exams.add(exam);
        exam.setExamSchedule(this);
        return this;
    }

    public ExamSchedule removeExams(Exam exam) {
        this.exams.remove(exam);
        exam.setExamSchedule(null);
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
        ExamSchedule examSchedule = (ExamSchedule) o;
        if (examSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamSchedule{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", active='" + isActive() + "'" +
            ", department='" + getDepartment() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
