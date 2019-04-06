package com.lxisoft.sis.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Attatchment entity.
 */
public class AttatchmentDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] file;

    private String fileContentType;

    private Long leaveApplicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(Long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttatchmentDTO attatchmentDTO = (AttatchmentDTO) o;
        if (attatchmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attatchmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttatchmentDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", leaveApplication=" + getLeaveApplicationId() +
            "}";
    }
}
