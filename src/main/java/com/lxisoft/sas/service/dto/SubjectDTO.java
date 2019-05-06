package com.lxisoft.sas.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.sas.domain.enumeration.Semester;

/**
 * A DTO for the Subject entity.
 */

public class SubjectDTO implements Serializable {

    private Long id;

    private String subjectCode;

    private Semester semester;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubjectDTO subjectDTO = (SubjectDTO) o;
        if (subjectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
