package com.lxisoft.sas.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Qualification entity.
 */
public class QualificationDTO implements Serializable {

    private Long id;

    private String grade;

    private Integer year;

    private String university;

    private Double marks;

    private Float percentage;

    private Long userDomainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Long getUserDomainId() {
        return userDomainId;
    }

    public void setUserDomainId(Long userDomainId) {
        this.userDomainId = userDomainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QualificationDTO qualificationDTO = (QualificationDTO) o;
        if (qualificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), qualificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QualificationDTO{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            ", year=" + getYear() +
            ", university='" + getUniversity() + "'" +
            ", marks=" + getMarks() +
            ", percentage=" + getPercentage() +
            ", userDomain=" + getUserDomainId() +
            "}";
    }
}
