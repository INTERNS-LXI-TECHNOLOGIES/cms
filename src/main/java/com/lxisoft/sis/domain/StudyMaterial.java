package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StudyMaterial.
 */
@Entity
@Table(name = "study_material")
public class StudyMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "module")
    private Integer module;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToOne
    @JsonIgnoreProperties("materials")
    private Subject subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public StudyMaterial title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getModule() {
        return module;
    }

    public StudyMaterial module(Integer module) {
        this.module = module;
        return this;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public byte[] getFile() {
        return file;
    }

    public StudyMaterial file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public StudyMaterial fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Subject getSubject() {
        return subject;
    }

    public StudyMaterial subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        StudyMaterial studyMaterial = (StudyMaterial) o;
        if (studyMaterial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studyMaterial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudyMaterial{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", module=" + getModule() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
