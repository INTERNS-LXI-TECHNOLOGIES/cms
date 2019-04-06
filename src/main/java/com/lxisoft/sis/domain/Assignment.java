package com.lxisoft.sis.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.lxisoft.sis.domain.enumeration.Semester;

import com.lxisoft.sis.domain.enumeration.Department;

/**
 * A Assignment.
 */
@Entity
@Table(name = "assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_date")
    private Instant submissionDate;

    @Column(name = "topic")
    private String topic;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

    public Assignment submissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getTopic() {
        return topic;
    }

    public Assignment topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Semester getSemester() {
        return semester;
    }

    public Assignment semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public Assignment department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        Assignment assignment = (Assignment) o;
        if (assignment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", topic='" + getTopic() + "'" +
            ", semester='" + getSemester() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
