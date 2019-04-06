package com.lxisoft.sas.repository;

import com.lxisoft.sas.domain.Attatchment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Attatchment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttatchmentRepository extends JpaRepository<Attatchment, Long> {

}
