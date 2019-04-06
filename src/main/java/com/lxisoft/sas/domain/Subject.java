package com.lxisoft.sas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sas.domain.enumeration.Semester;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_code")
    private String subjectCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @OneToMany(mappedBy = "subject")
    private Set<StudyMaterial> materials = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public Subject subjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        return this;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Semester getSemester() {
        return semester;
    }

    public Subject semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<StudyMaterial> getMaterials() {
        return materials;
    }

    public Subject materials(Set<StudyMaterial> studyMaterials) {
        this.materials = studyMaterials;
        return this;
    }

    public Subject addMaterials(StudyMaterial studyMaterial) {
        this.materials.add(studyMaterial);
        studyMaterial.setSubject(this);
        return this;
    }

    public Subject removeMaterials(StudyMaterial studyMaterial) {
        this.materials.remove(studyMaterial);
        studyMaterial.setSubject(null);
        return this;
    }

    public void setMaterials(Set<StudyMaterial> studyMaterials) {
        this.materials = studyMaterials;
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
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", semester='" + getSemester() + "'" +
            "}";
    }
}
