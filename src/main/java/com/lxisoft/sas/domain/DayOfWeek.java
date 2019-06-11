package com.lxisoft.sas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DayOfWeek.
 */
@Entity
@Table(name = "day_of_week")
public class DayOfWeek implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_1")
    private String sub1;

    @Column(name = "sub_2")
    private String sub2;

    @Column(name = "sub_3")
    private String sub3;

    @Column(name = "sub_4")
    private String sub4;

    @Column(name = "sub_5")
    private String sub5;

    @Column(name = "sub_6")
    private String sub6;

    @Column(name = "sub_7")
    private String sub7;

    @ManyToOne
    @JsonIgnoreProperties("days")
    private TimeTable timeTable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSub1() {
        return sub1;
    }

    public DayOfWeek sub1(String sub1) {
        this.sub1 = sub1;
        return this;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public DayOfWeek sub2(String sub2) {
        this.sub2 = sub2;
        return this;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public DayOfWeek sub3(String sub3) {
        this.sub3 = sub3;
        return this;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    public String getSub4() {
        return sub4;
    }

    public DayOfWeek sub4(String sub4) {
        this.sub4 = sub4;
        return this;
    }

    public void setSub4(String sub4) {
        this.sub4 = sub4;
    }

    public String getSub5() {
        return sub5;
    }

    public DayOfWeek sub5(String sub5) {
        this.sub5 = sub5;
        return this;
    }

    public void setSub5(String sub5) {
        this.sub5 = sub5;
    }

    public String getSub6() {
        return sub6;
    }

    public DayOfWeek sub6(String sub6) {
        this.sub6 = sub6;
        return this;
    }

    public void setSub6(String sub6) {
        this.sub6 = sub6;
    }

    public String getSub7() {
        return sub7;
    }

    public DayOfWeek sub7(String sub7) {
        this.sub7 = sub7;
        return this;
    }

    public void setSub7(String sub7) {
        this.sub7 = sub7;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public DayOfWeek timeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
        return this;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
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
        DayOfWeek dayOfWeek = (DayOfWeek) o;
        if (dayOfWeek.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dayOfWeek.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
            "id=" + getId() +
            ", sub1='" + getSub1() + "'" +
            ", sub2='" + getSub2() + "'" +
            ", sub3='" + getSub3() + "'" +
            ", sub4='" + getSub4() + "'" +
            ", sub5='" + getSub5() + "'" +
            ", sub6='" + getSub6() + "'" +
            ", sub7='" + getSub7() + "'" +
            "}";
    }
}
