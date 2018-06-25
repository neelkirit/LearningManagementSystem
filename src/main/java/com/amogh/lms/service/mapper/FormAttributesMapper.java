package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.FormAttributesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FormAttributes and its DTO FormAttributesDTO.
 */
@Mapper(componentModel = "spring", uses = {FormMapper.class})
public interface FormAttributesMapper extends EntityMapper<FormAttributesDTO, FormAttributes> {

    @Mapping(source = "form.id", target = "formId")
    @Mapping(source = "form.name", target = "formName")
    FormAttributesDTO toDto(FormAttributes formAttributes);

    @Mapping(source = "formId", target = "form")
    FormAttributes toEntity(FormAttributesDTO formAttributesDTO);

    default FormAttributes fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormAttributes formAttributes = new FormAttributes();
        formAttributes.setId(id);
        return formAttributes;
    }
}
