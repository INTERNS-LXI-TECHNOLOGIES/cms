package com.lxisoft.sis.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lxisoft.sis.domain.enumeration.Department;
import com.lxisoft.sis.domain.enumeration.Semester;

/**
 * A DTO for the UserDomain entity.
 */
public class UserDomainDTO implements Serializable {

    private Long id;

    private String regNum;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Department department;

    private Semester semester;

    private Long contactNumber;


    private Long addressId;

    private Set<UserRoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Set<UserRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleDTO> userRoles) {
        this.roles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDomainDTO userDomainDTO = (UserDomainDTO) o;
        if (userDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDomainDTO{" +
            "id=" + getId() +
            ", regNum='" + getRegNum() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", department='" + getDepartment() + "'" +
            ", semester='" + getSemester() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", address=" + getAddressId() +
            "}";
    }
}
