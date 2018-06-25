package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.FormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Form and its DTO FormDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormMapper extends EntityMapper<FormDTO, Form> {



    default Form fromId(Long id) {
        if (id == null) {
            return null;
        }
        Form form = new Form();
        form.setId(id);
        return form;
    }
}
