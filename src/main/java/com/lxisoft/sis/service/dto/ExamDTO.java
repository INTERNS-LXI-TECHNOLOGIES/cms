package com.lxisoft.sis.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exam entity.
 */
public class ExamDTO implements Serializable {

    private Long id;

    private Instant examDate;

    private String startingTime;

    private String duration;


    private Long examScheduleId;

    private Long subjectId;

    private Set<ExamHallDTO> halls = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExamDate() {
        return examDate;
    }

    public void setExamDate(Instant examDate) {
        this.examDate = examDate;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(Long examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Set<ExamHallDTO> getHalls() {
        return halls;
    }

    public void setHalls(Set<ExamHallDTO> examHalls) {
        this.halls = examHalls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamDTO examDTO = (ExamDTO) o;
        if (examDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", examDate='" + getExamDate() + "'" +
            ", startingTime='" + getStartingTime() + "'" +
            ", duration='" + getDuration() + "'" +
            ", examSchedule=" + getExamScheduleId() +
            ", subject=" + getSubjectId() +
            "}";
    }
}
