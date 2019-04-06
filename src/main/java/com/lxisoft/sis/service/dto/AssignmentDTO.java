package com.lxisoft.sis.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.sis.domain.enumeration.Semester;
import com.lxisoft.sis.domain.enumeration.Department;

/**
 * A DTO for the Assignment entity.
 */
public class AssignmentDTO implements Serializable {

    private Long id;

    private Instant submissionDate;

    private String topic;

    private Semester semester;

    private Department department;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignmentDTO assignmentDTO = (AssignmentDTO) o;
        if (assignmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssignmentDTO{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", topic='" + getTopic() + "'" +
            ", semester='" + getSemester() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
