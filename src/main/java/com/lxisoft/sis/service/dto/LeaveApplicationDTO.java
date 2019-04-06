package com.lxisoft.sis.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LeaveApplication entity.
 */
public class LeaveApplicationDTO implements Serializable {

    private Long id;

    private String reason;

    private Instant fromDate;

    private Instant toDate;


    private Long appliedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Long getAppliedById() {
        return appliedById;
    }

    public void setAppliedById(Long userDomainId) {
        this.appliedById = userDomainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaveApplicationDTO leaveApplicationDTO = (LeaveApplicationDTO) o;
        if (leaveApplicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveApplicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveApplicationDTO{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", appliedBy=" + getAppliedById() +
            "}";
    }
}
