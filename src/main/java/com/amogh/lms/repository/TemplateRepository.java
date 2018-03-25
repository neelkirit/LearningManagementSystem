package com.amogh.lms.repository;

import com.amogh.lms.domain.Template;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    /**
     * Find the template with the given name
     * @param templateName the template name
     * @return Template object saved
     */
    Template findByName(String templateName);

}
