package com.lxisoft.sas.repository;

import com.lxisoft.sas.domain.UserDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDomainRepository extends JpaRepository<UserDomain, Long> {

    @Query(value = "select distinct user_domain from UserDomain user_domain left join fetch user_domain.roles",
        countQuery = "select count(distinct user_domain) from UserDomain user_domain")
    Page<UserDomain> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_domain from UserDomain user_domain left join fetch user_domain.roles")
    List<UserDomain> findAllWithEagerRelationships();

    @Query("select user_domain from UserDomain user_domain left join fetch user_domain.roles where user_domain.id =:id")
    Optional<UserDomain> findOneWithEagerRelationships(@Param("id") Long id);
    
    Optional<UserDomain> findOneByRegNum(String regNum);

}
