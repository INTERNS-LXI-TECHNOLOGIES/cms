package com.lxisoft.sis.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.sis.domain.enumeration.Department;
import com.lxisoft.sis.domain.enumeration.Semester;

/**
 * A DTO for the ExamSchedule entity.
 */
public class ExamScheduleDTO implements Serializable {

    private Long id;

    private String title;

    private Instant startDate;

    private Instant endDate;

    private Boolean active;

    private Department department;

    private Semester semester;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

        ExamScheduleDTO examScheduleDTO = (ExamScheduleDTO) o;
        if (examScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamScheduleDTO{" +
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
