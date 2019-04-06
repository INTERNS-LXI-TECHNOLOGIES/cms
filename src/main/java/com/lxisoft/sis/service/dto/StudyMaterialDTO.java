package com.lxisoft.sis.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the StudyMaterial entity.
 */
public class StudyMaterialDTO implements Serializable {

    private Long id;

    private String title;

    private Integer module;

    @Lob
    private byte[] file;

    private String fileContentType;

    private Long subjectId;

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

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudyMaterialDTO studyMaterialDTO = (StudyMaterialDTO) o;
        if (studyMaterialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studyMaterialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudyMaterialDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", module=" + getModule() +
            ", file='" + getFile() + "'" +
            ", subject=" + getSubjectId() +
            "}";
    }
}
