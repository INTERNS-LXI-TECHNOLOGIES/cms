package com.lxisoft.sas.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.sas.domain.enumeration.Semester;
import com.lxisoft.sas.domain.enumeration.Department;

/**
 * A DTO for the TimeTable entity.
 */
public class TimeTableDTO implements Serializable {

    private Long id;

    private Semester semester;

    private Department department;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        TimeTableDTO timeTableDTO = (TimeTableDTO) o;
        if (timeTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeTableDTO{" +
            "id=" + getId() +
            ", semester='" + getSemester() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }
}
