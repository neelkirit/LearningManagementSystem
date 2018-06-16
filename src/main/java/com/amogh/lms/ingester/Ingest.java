package com.amogh.lms.ingester;

import com.amogh.lms.domain.enumeration.ContentType;
import com.amogh.lms.ingester.model.IngestModel;
import com.amogh.lms.service.CourseService;
import com.amogh.lms.service.ExerciseService;
import com.amogh.lms.service.TemplateService;
import com.amogh.lms.service.TopicService;
import com.amogh.lms.service.dto.CourseDTO;
import com.amogh.lms.service.dto.ExerciseDTO;
import com.amogh.lms.service.dto.TemplateDTO;
import com.amogh.lms.service.dto.TopicDTO;
import com.amogh.lms.service.mapper.TemplateMapper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class Ingest implements IBaseIngester {

    @Autowired
    CourseService courseService;

    @Autowired
    TopicService topicService;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    TemplateService templateService;

    @Autowired
    TemplateMapper templateMapper;

    /**
     * Reads the excel file and formats the row found
     *
     * @param fileName the excel file name
     * @return List of Ingest Models to be used for persisting
     */
    protected List<IngestModel> readExcel(String fileName) {
        List<IngestModel> ingestModels = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new File(fileName))) {
            System.out.println("Workbook " + fileName + " has " + workbook.getNumberOfSheets());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() != 0) {
                    ingestModels.add(this.parseRow(row));
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingestModels;
    }

    @Override
    public List<IngestModel> process(String fileName) {
        return this.readExcel(fileName);
    }

    @Override
    public void persist(List<IngestModel> ingestModels) {
        for (IngestModel ingestModel : ingestModels) {
            CourseDTO courseDTO = this.saveCourse(ingestModel.getCourseName());
            TopicDTO topicDTO = this.saveTopic(courseDTO, ingestModel.getTopicName());
            String templateName = ingestModel.getTemplateName();
            TemplateDTO templateDTO = this.templateService.findByName(templateName);
            if (templateDTO != null) {
                this.saveExercise(ingestModel.getContent(), ingestModel.getContentType(), topicDTO, templateDTO);
            }
        }
    }

    /**
     * Saves the course to DB
     *
     * @param courseName the course name
     * @return Course DTO object
     */
    private CourseDTO saveCourse(String courseName) {
        System.out.println("Saving Course [" + courseName + "]...");
        CourseDTO courseDTO = this.courseService.findByName(courseName);
        if (courseDTO == null) {
            courseDTO = new CourseDTO();
            courseDTO.setName(courseName);
            courseDTO = this.courseService.save(courseDTO);
            if (courseDTO != null) {
                System.out.println("Saved Course [" + courseName + "]...");
            } else {
                System.out.println("FAILED!! CHECK!! Save Course [" + courseName + "]...");
            }
        }
        return courseDTO;
    }


    /**
     * Saves the topic to DB
     *
     * @param courseDTO the course DTO to which this topic belongs
     * @param topicName the topic name
     * @return TopicDTO the topic DTO object saved
     */
    private TopicDTO saveTopic(CourseDTO courseDTO, String topicName) {
        System.out.println("Saving Topic [" + topicName + "]...");
        TopicDTO topicDTO = this.topicService.findByName(topicName);
        if (topicDTO == null) {
            topicDTO = new TopicDTO();
            topicDTO.setName(topicName);
            topicDTO.setCourseId(courseDTO.getId());
            topicDTO.setCourseName(courseDTO.getName());
            topicDTO = this.topicService.save(topicDTO);
            if (topicDTO != null) {
                System.out.println("Saved Topic [" + topicName + "]...");
            } else {
                System.out.println("FAILED!! CHECK!! Save Topic [" + topicName + "]...");
            }
        }
        return topicDTO;
    }

    /**
     * Save the exercise question
     *
     * @param content     the content of the question
     * @param contentType the content type
     * @param topicDTO    the topic DTO to which the question belongs
     * @param templateDTO the template DTO to be applied
     * @return Exercise DTO just saved
     */
    private ExerciseDTO saveExercise(
        String content,
        ContentType contentType,
        TopicDTO topicDTO,
        TemplateDTO templateDTO
    ) {
        ExerciseDTO exerciseDTO = this.exerciseService.findByTemplateAndContentTypeAndContent(
            this.templateMapper.toEntity(templateDTO),
            templateDTO.getContentType(),
            content
        );
        if (exerciseDTO != null) {
            System.out.println("Skipping exercise. Duplicate exists already for content ["
                + content
                + "], content type ["
                + contentType
                + "] and template ["
                + templateDTO.getName()
                + "].."
            );
        } else {
            exerciseDTO = new ExerciseDTO();
            exerciseDTO.setContent(content);
            exerciseDTO.setContentType(contentType);
            exerciseDTO.setTemplateId(templateDTO.getId());
            exerciseDTO.setTopicId(topicDTO.getId());
            exerciseDTO = this.exerciseService.save(exerciseDTO);
            if (exerciseDTO == null) {
                System.out.println("Saved the exercise successfully..");
            } else {
                System.out.println("FAILED!! CHECK!! Save the exercise failed..");
            }
        }
        return exerciseDTO;
    }

    protected IngestModel parseRow(Row row) {
        IngestModel model = new IngestModel();
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Cell> cellIterator = row.cellIterator();
        int i = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);

            switch (i) {
                case 0:
                    model.setCourseName(cellValue);
                    break;
                case 1:
                    model.setTopicName(cellValue);
                    break;
                case 2:
                    model.setContent(cellValue);
                    break;
                case 3:
                    ContentType enumValue = ContentType.valueOf(cellValue);
                    model.setContentType(enumValue);
                    break;
                case 4:
                    model.setTemplateName(cellValue);
                    break;
                case 5:
                    model.setAnswer(cellValue);
                    break;
                default:
                    System.out.println("Should not have more columns than this!!!");
            }
            i++;
        }
        return model;
    }
}
