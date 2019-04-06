package com.lxisoft.sis.repository;

import com.lxisoft.sis.domain.ExamHall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExamHall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamHallRepository extends JpaRepository<ExamHall, Long> {

}
