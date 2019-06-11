package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.TimeTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TimeTable and its DTO TimeTableDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TimeTableMapper extends EntityMapper<TimeTableDTO, TimeTable> {


    @Mapping(target = "days", ignore = true)
    TimeTable toEntity(TimeTableDTO timeTableDTO);

    default TimeTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        TimeTable timeTable = new TimeTable();
        timeTable.setId(id);
        return timeTable;
    }
}
