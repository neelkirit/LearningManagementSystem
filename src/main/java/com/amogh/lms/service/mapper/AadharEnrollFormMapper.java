package com.amogh.lms.service.mapper;

import com.amogh.lms.domain.*;
import com.amogh.lms.service.dto.AadharEnrollFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AadharEnrollForm and its DTO AadharEnrollFormDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AadharEnrollFormMapper extends EntityMapper<AadharEnrollFormDTO, AadharEnrollForm> {



    default AadharEnrollForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        AadharEnrollForm aadharEnrollForm = new AadharEnrollForm();
        aadharEnrollForm.setId(id);
        return aadharEnrollForm;
    }
}
