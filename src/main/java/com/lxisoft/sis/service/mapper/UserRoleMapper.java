package com.lxisoft.sis.service.mapper;

import com.lxisoft.sis.domain.*;
import com.lxisoft.sis.service.dto.UserRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserRole and its DTO UserRoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {


    @Mapping(target = "users", ignore = true)
    UserRole toEntity(UserRoleDTO userRoleDTO);

    default UserRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserRole userRole = new UserRole();
        userRole.setId(id);
        return userRole;
    }
}
