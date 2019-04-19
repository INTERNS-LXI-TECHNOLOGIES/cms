package com.lxisoft.sis.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lxisoft.sis.domain.Qualification;
import com.lxisoft.sis.domain.UserDomain;


/**
 * Spring Data  repository for the Qualification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
	public Page<Qualification> findAllByUserDomain(UserDomain user, Pageable pageable);
}
