package com.lxisoft.sas.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lxisoft.sas.domain.enumeration.Role;

/**
 * A UserRole.
 */
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_role")
    private Role role;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<UserDomain> userIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public UserRole role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserDomain> getUserIds() {
        return userIds;
    }

    public UserRole userIds(Set<UserDomain> userDomains) {
        this.userIds = userDomains;
        return this;
    }

    public UserRole addUserId(UserDomain userDomain) {
        this.userIds.add(userDomain);
        userDomain.getRoles().add(this);
        return this;
    }

    public UserRole removeUserId(UserDomain userDomain) {
        this.userIds.remove(userDomain);
        userDomain.getRoles().remove(this);
        return this;
    }

    public void setUserIds(Set<UserDomain> userDomains) {
        this.userIds = userDomains;
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
        UserRole userRole = (UserRole) o;
        if (userRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRole{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
