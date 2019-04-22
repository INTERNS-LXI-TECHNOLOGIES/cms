package com.lxisoft.sas.repository;
import java.util.List;
import com.lxisoft.sas.domain.Qualification;
import com.lxisoft.sas.domain.UserDomain;

import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Qualification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
	public Page<Qualification> findAllByUserDomain(UserDomain user, org.springframework.data.domain.Pageable pageable);
}
