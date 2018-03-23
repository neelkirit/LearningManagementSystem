package com.amogh.lms.ingester.model;

import com.amogh.lms.domain.Exercise;

import java.util.List;
import java.util.Map;

public class IngesterModel {

    String courseName;

    Map<String, List<Exercise>> excercisesByTopic;
}
