package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.ExamScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamSchedule and its DTO ExamScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamScheduleMapper extends EntityMapper<ExamScheduleDTO, ExamSchedule> {


    @Mapping(target = "exams", ignore = true)
    ExamSchedule toEntity(ExamScheduleDTO examScheduleDTO);

    default ExamSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamSchedule examSchedule = new ExamSchedule();
        examSchedule.setId(id);
        return examSchedule;
    }
}
