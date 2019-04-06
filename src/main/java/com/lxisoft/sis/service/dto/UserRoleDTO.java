package com.lxisoft.sis.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.lxisoft.sis.domain.enumeration.Role;

/**
 * A DTO for the UserRole entity.
 */
public class UserRoleDTO implements Serializable {

    private Long id;

    private Role role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRoleDTO userRoleDTO = (UserRoleDTO) o;
        if (userRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRoleDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
