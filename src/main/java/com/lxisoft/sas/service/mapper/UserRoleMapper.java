package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.UserRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserRole and its DTO UserRoleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {


    @Mapping(target = "userIds", ignore = true)
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
