package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.StudyMaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudyMaterial and its DTO StudyMaterialDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface StudyMaterialMapper extends EntityMapper<StudyMaterialDTO, StudyMaterial> {

    @Mapping(source = "subject.id", target = "subjectId")
    StudyMaterialDTO toDto(StudyMaterial studyMaterial);

    @Mapping(source = "subjectId", target = "subject")
    StudyMaterial toEntity(StudyMaterialDTO studyMaterialDTO);

    default StudyMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudyMaterial studyMaterial = new StudyMaterial();
        studyMaterial.setId(id);
        return studyMaterial;
    }
}
