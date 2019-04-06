package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.ExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exam and its DTO ExamDTO.
 */
@Mapper(componentModel = "spring", uses = {ExamScheduleMapper.class, SubjectMapper.class, ExamHallMapper.class})
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {

    @Mapping(source = "examSchedule.id", target = "examScheduleId")
    @Mapping(source = "subject.id", target = "subjectId")
    ExamDTO toDto(Exam exam);

    @Mapping(source = "examScheduleId", target = "examSchedule")
    @Mapping(source = "subjectId", target = "subject")
    Exam toEntity(ExamDTO examDTO);

    default Exam fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exam exam = new Exam();
        exam.setId(id);
        return exam;
    }
}
