package com.amogh.lms.ingester;

import com.amogh.lms.ingester.model.FormModel;
import com.amogh.lms.service.FormAttributesService;
import com.amogh.lms.service.FormService;
import com.amogh.lms.service.dto.FormAttributesDTO;
import com.amogh.lms.service.dto.FormDTO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FormIngest {

    @Autowired
    private FormService formService;

    @Autowired
    private FormAttributesService formAttributesService;

    public FormIngest(FormService formService, FormAttributesService formAttributesService) {
        this.formService = formService;
        this.formAttributesService = formAttributesService;
    }

    /**
     * Reads the excel file and formats the row found
     *
     * @param fileName the excel file name
     * @return List of Form ingest Models to be used for persisting
     */
    protected List<FormModel> readExcel(String fileName) {
        List<FormModel> ingestModels = new ArrayList<>();
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
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return ingestModels;
    }

    public List<FormModel> process(String fileName) {
        return this.readExcel(fileName);
    }

    public void persist(List<FormModel> ingestModels) {
        for (FormModel formModel : ingestModels) {
            FormDTO formDTO = this.saveForm(formModel.getName());
            this.saveFormAttributes(formDTO, formModel);
        }
    }

    public FormDTO saveForm(String formName) {
        FormDTO formDTO = this.formService.findByName(formName);
        if (formDTO == null) {
            System.out.println("No form entry found with name [ " + formName + " ]. Creating one");
            formDTO = new FormDTO();
            formDTO.setName(formName);
            formDTO = this.formService.save(formDTO);
        }
        System.out.println("Created/found entry for the form [ " + formName + " ].");
        return formDTO;
    }

    public void saveFormAttributes(FormDTO formDTO, FormModel formModel) {
        Long formId = formDTO.getId();
        String name = formModel.getAttrName();
        String type = formModel.getType();
        Boolean isMandatory = formModel.getMandatory();
        String content = formModel.getContent();
        FormAttributesDTO formAttrDTO = this.formAttributesService.findByFormIdAndNameAndTypeAndIsMandatoryAndContent(
            formId,
            name,
            type,
            isMandatory,
            content
        );
        if (formAttrDTO == null) {
            System.out.println("No form attribute entry for attributes " +
                "Form id [" + formId + "] form type [" + type + "] form attr [ " + name + " ]"
            );
            formAttrDTO = new FormAttributesDTO();
            formAttrDTO.setName(name);
            formAttrDTO.setFormId(formId);
            formAttrDTO.setContent(content);
            formAttrDTO.setIsMandatory(isMandatory);
            formAttrDTO.setType(type);
            this.formAttributesService.save(formAttrDTO);
        }
        System.out.println("Found/Created the form attribute entry");
    }

    protected FormModel parseRow(Row row) {
        FormModel model = new FormModel();
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Cell> cellIterator = row.cellIterator();
        int i = 0;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);

            switch (i) {
                case 0:
                    model.setName(cellValue);
                    break;
                case 1:
                    model.setAttrName(cellValue);
                    break;
                case 2:
                    model.setType(cellValue);
                    break;
                case 3:
                    model.setMandatory(Boolean.valueOf(cellValue));
                    break;
                case 4:
                    model.setContent(cellValue);
                    break;
                default:
                    System.out.println("Should not have more columns than this!!!");
            }
            i++;
        }
        return model;
    }
}
