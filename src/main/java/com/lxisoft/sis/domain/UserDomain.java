package com.lxisoft.sis.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sis.domain.enumeration.Department;

import com.lxisoft.sis.domain.enumeration.Semester;

/**
 * A UserDomain.
 */
@Entity
@Table(name = "user_domain")
public class UserDomain implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "jhi_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Column(name = "contact_number")
    private Long contactNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "userDomain")
    private Set<Qualification> qualifications = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "user_domain_roles",
               joinColumns = @JoinColumn(name = "user_domain_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<UserRole> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDomain firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDomain lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserDomain email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public UserDomain password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public UserDomain department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() {
        return semester;
    }

    public UserDomain semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public UserDomain contactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Address getAddress() {
        return address;
    }

    public UserDomain address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public UserDomain qualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
        return this;
    }

    public UserDomain addQualifications(Qualification qualification) {
        this.qualifications.add(qualification);
        qualification.setUserDomain(this);
        return this;
    }

    public UserDomain removeQualifications(Qualification qualification) {
        this.qualifications.remove(qualification);
        qualification.setUserDomain(null);
        return this;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public UserDomain roles(Set<UserRole> userRoles) {
        this.roles = userRoles;
        return this;
    }

    public UserDomain addRoles(UserRole userRole) {
        this.roles.add(userRole);
        userRole.getUserDomains().add(this);
        return this;
    }

    public UserDomain removeRoles(UserRole userRole) {
        this.roles.remove(userRole);
        userRole.getUserDomains().remove(this);
        return this;
    }

    public void setRoles(Set<UserRole> userRoles) {
        this.roles = userRoles;
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
        UserDomain userDomain = (UserDomain) o;
        if (userDomain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDomain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDomain{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", department='" + getDepartment() + "'" +
            ", semester='" + getSemester() + "'" +
            ", contactNumber=" + getContactNumber() +
            "}";
    }
}
