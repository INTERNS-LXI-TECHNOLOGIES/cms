package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LeaveApplication.
 */
@Entity
@Table(name = "leave_application")
public class LeaveApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @OneToMany(mappedBy = "leaveApplication")
    private Set<Attatchment> attatchments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("leaveApplications")
    private UserDomain appliedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public LeaveApplication reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public LeaveApplication fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public LeaveApplication toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Set<Attatchment> getAttatchments() {
        return attatchments;
    }

    public LeaveApplication attatchments(Set<Attatchment> attatchments) {
        this.attatchments = attatchments;
        return this;
    }

    public LeaveApplication addAttatchments(Attatchment attatchment) {
        this.attatchments.add(attatchment);
        attatchment.setLeaveApplication(this);
        return this;
    }

    public LeaveApplication removeAttatchments(Attatchment attatchment) {
        this.attatchments.remove(attatchment);
        attatchment.setLeaveApplication(null);
        return this;
    }

    public void setAttatchments(Set<Attatchment> attatchments) {
        this.attatchments = attatchments;
    }

    public UserDomain getAppliedBy() {
        return appliedBy;
    }

    public LeaveApplication appliedBy(UserDomain userDomain) {
        this.appliedBy = userDomain;
        return this;
    }

    public void setAppliedBy(UserDomain userDomain) {
        this.appliedBy = userDomain;
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
        LeaveApplication leaveApplication = (LeaveApplication) o;
        if (leaveApplication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveApplication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            "}";
    }
}
