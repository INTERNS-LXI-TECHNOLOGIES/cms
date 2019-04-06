package com.lxisoft.sas.repository;

import com.lxisoft.sas.domain.ExamHall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ExamHall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamHallRepository extends JpaRepository<ExamHall, Long> {

}
