package com.amogh.lms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Anvitha G K Bhat on 13/5/18.
 */
@Getter
@AllArgsConstructor
public class CourseStatDTO
{
    long correct;
    long incorrect;
    long skipped;
}
