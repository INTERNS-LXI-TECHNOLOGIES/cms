package com.lxisoft.sas.repository;

import com.lxisoft.sas.domain.StudyMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudyMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {

}
