package com.lxisoft.sas.service.mapper;

import com.lxisoft.sas.domain.*;
import com.lxisoft.sas.service.dto.DayOfWeekDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DayOfWeek and its DTO DayOfWeekDTO.
 */
@Mapper(componentModel = "spring", uses = {TimeTableMapper.class})
public interface DayOfWeekMapper extends EntityMapper<DayOfWeekDTO, DayOfWeek> {

    @Mapping(source = "timeTable.id", target = "timeTableId")
    DayOfWeekDTO toDto(DayOfWeek dayOfWeek);

    @Mapping(source = "timeTableId", target = "timeTable")
    DayOfWeek toEntity(DayOfWeekDTO dayOfWeekDTO);

    default DayOfWeek fromId(Long id) {
        if (id == null) {
            return null;
        }
        DayOfWeek dayOfWeek = new DayOfWeek();
        dayOfWeek.setId(id);
        return dayOfWeek;
    }
}
