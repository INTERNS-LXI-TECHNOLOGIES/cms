package com.lxisoft.sis.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExamHall entity.
 */
public class ExamHallDTO implements Serializable {

    private Long id;

    private Integer hallNumber;

    private String batch;

    private Integer rollNumFrom;

    private Integer rollNumTo;

    private String invigialtor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getRollNumFrom() {
        return rollNumFrom;
    }

    public void setRollNumFrom(Integer rollNumFrom) {
        this.rollNumFrom = rollNumFrom;
    }

    public Integer getRollNumTo() {
        return rollNumTo;
    }

    public void setRollNumTo(Integer rollNumTo) {
        this.rollNumTo = rollNumTo;
    }

    public String getInvigialtor() {
        return invigialtor;
    }

    public void setInvigialtor(String invigialtor) {
        this.invigialtor = invigialtor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamHallDTO examHallDTO = (ExamHallDTO) o;
        if (examHallDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examHallDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamHallDTO{" +
            "id=" + getId() +
            ", hallNumber=" + getHallNumber() +
            ", batch='" + getBatch() + "'" +
            ", rollNumFrom=" + getRollNumFrom() +
            ", rollNumTo=" + getRollNumTo() +
            ", invigialtor='" + getInvigialtor() + "'" +
            "}";
    }
}
