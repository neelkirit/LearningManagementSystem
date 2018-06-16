package com.amogh.lms.repository;

import com.amogh.lms.domain.AadharEnrollForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AadharEnrollForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AadharEnrollFormRepository extends JpaRepository<AadharEnrollForm, Long> {

}
