package com.lxisoft.sas.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DayOfWeek entity.
 */
public class DayOfWeekDTO implements Serializable {

    private Long id;

    private String sub1;

    private String sub2;

    private String sub3;

    private String sub4;

    private String sub5;

    private String sub6;

    private String sub7;


    private Long timeTableId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

    public String getSub4() {
        return sub4;
    }

    public void setSub4(String sub4) {
        this.sub4 = sub4;
    }

    public String getSub5() {
        return sub5;
    }

    public void setSub5(String sub5) {
        this.sub5 = sub5;
    }

    public String getSub6() {
        return sub6;
    }

    public void setSub6(String sub6) {
        this.sub6 = sub6;
    }

    public String getSub7() {
        return sub7;
    }

    public void setSub7(String sub7) {
        this.sub7 = sub7;
    }

    public Long getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(Long timeTableId) {
        this.timeTableId = timeTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DayOfWeekDTO dayOfWeekDTO = (DayOfWeekDTO) o;
        if (dayOfWeekDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dayOfWeekDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DayOfWeekDTO{" +
            "id=" + getId() +
            ", sub1='" + getSub1() + "'" +
            ", sub2='" + getSub2() + "'" +
            ", sub3='" + getSub3() + "'" +
            ", sub4='" + getSub4() + "'" +
            ", sub5='" + getSub5() + "'" +
            ", sub6='" + getSub6() + "'" +
            ", sub7='" + getSub7() + "'" +
            ", timeTable=" + getTimeTableId() +
            "}";
    }
}
