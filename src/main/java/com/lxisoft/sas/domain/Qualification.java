package com.lxisoft.sas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Qualification.
 */
@Entity
@Table(name = "qualification")
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private String grade;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "university")
    private String university;

    @Column(name = "marks")
    private Double marks;

    @Column(name = "percentage")
    private Float percentage;

    @ManyToOne
    @JsonIgnoreProperties("qualifications")
    private UserDomain userDomain;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public Qualification grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getYear() {
        return year;
    }

    public Qualification year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUniversity() {
        return university;
    }

    public Qualification university(String university) {
        this.university = university;
        return this;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Double getMarks() {
        return marks;
    }

    public Qualification marks(Double marks) {
        this.marks = marks;
        return this;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Float getPercentage() {
        return percentage;
    }

    public Qualification percentage(Float percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public UserDomain getUserDomain() {
        return userDomain;
    }

    public Qualification userDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
        return this;
    }

    public void setUserDomain(UserDomain userDomain) {
        this.userDomain = userDomain;
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
        Qualification qualification = (Qualification) o;
        if (qualification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), qualification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Qualification{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            ", year=" + getYear() +
            ", university='" + getUniversity() + "'" +
            ", marks=" + getMarks() +
            ", percentage=" + getPercentage() +
            "}";
    }
}
