package com.lxisoft.sis.service.mapper;

import com.lxisoft.sis.domain.*;
import com.lxisoft.sis.service.dto.ExamHallDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamHall and its DTO ExamHallDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamHallMapper extends EntityMapper<ExamHallDTO, ExamHall> {


    @Mapping(target = "exams", ignore = true)
    ExamHall toEntity(ExamHallDTO examHallDTO);

    default ExamHall fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamHall examHall = new ExamHall();
        examHall.setId(id);
        return examHall;
    }
}
