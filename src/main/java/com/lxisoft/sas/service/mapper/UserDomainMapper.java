package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.UserDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserDomain and its DTO UserDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, UserRoleMapper.class})
public interface UserDomainMapper extends EntityMapper<UserDomainDTO, UserDomain> {

    @Mapping(source = "address.id", target = "addressId")
    UserDomainDTO toDto(UserDomain userDomain);

    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "qualifications", ignore = true)
    UserDomain toEntity(UserDomainDTO userDomainDTO);

    default UserDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDomain userDomain = new UserDomain();
        userDomain.setId(id);
        return userDomain;
    }
}
