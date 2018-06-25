package com.amogh.lms.repository;

import com.amogh.lms.domain.FormAttributes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormAttributesRepository extends JpaRepository<FormAttributes, Long> {

    FormAttributes findByFormIdAndNameAndTypeAndIsMandatoryAndContent(
        Long formId,
        String name,
        String type,
        Boolean isMandatory,
        String content
    );

}
