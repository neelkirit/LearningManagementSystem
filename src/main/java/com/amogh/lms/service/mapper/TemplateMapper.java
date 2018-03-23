package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {



    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
