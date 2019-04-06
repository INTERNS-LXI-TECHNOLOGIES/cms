package com.lxisoft.sis.repository;

import com.lxisoft.sis.domain.ExamSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExamSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {

}
