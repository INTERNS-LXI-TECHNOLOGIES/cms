package com.lxisoft.sas.repository;


import com.lxisoft.sas.domain.Subject;
import com.lxisoft.sas.domain.enumeration.Department;
import com.lxisoft.sas.domain.enumeration.Semester;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Subject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {


	 Page<Subject> findAllBySemesterAndDepartment(Pageable pageable,Semester s, Department d);

}
