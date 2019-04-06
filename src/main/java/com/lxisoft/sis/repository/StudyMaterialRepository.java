package com.lxisoft.sis.repository;

import com.lxisoft.sis.domain.StudyMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudyMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

}
