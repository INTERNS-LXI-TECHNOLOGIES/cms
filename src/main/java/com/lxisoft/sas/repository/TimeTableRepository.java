package com.lxisoft.sas.repository;

import com.lxisoft.sas.domain.TimeTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimeTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

}
