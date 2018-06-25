package com.amogh.lms.ingester;

import com.amogh.lms.AmoghServerApp;
import com.amogh.lms.config.DefaultProfileUtil;
import com.amogh.lms.ingester.model.FormModel;
import com.amogh.lms.ingester.model.IngestModel;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.util.List;

public class RunIngest extends AmoghServerApp {

    public RunIngest(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RunIngest.class);
        DefaultProfileUtil.addDefaultProfile(app);
        ConfigurableApplicationContext context = app.run(args);
        String typeOfData = args[0];
        try{
            switch (typeOfData) {
                case "ALL":
                    System.out.println("Running the ingest through generic flow..");
                    RunIngest.runGenericIngest(args, context);
                    break;
                case "FORMS":
                    RunIngest.runFormsIngest(args, context);
                    break;
                default:
                    System.out.println("Please check the arguments. This type is not supported in ingest");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong... Please check!!");
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void runGenericIngest(String[] args, ConfigurableApplicationContext context) {
        Ingest ingester = context.getBean(Ingest.class);
        try{
            for(int i=1; i < args.length; i++) {
                String excelFilePath = args[i];
                System.out.println("Processing excel file: " + excelFilePath);
                List<IngestModel> ingestModels = ingester.process(excelFilePath);
                ingester.persist(ingestModels);
                System.out.println("Processing completed for file: " + excelFilePath);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong... Please check!!");
            e.printStackTrace();
        }
    }

    private static void runFormsIngest(String[] args, ConfigurableApplicationContext context) {
        FormIngest ingester = context.getBean(FormIngest.class);
        System.out.println("Running the ingest through generic flow..");
        try{
            for(int i=1; i < args.length; i++) {
                String excelFilePath = args[i];
                System.out.println("Processing excel file: " + excelFilePath);
                List<FormModel> ingestModels = ingester.process(excelFilePath);
                ingester.persist(ingestModels);
                System.out.println("Processing completed for file: " + excelFilePath);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong... Please check!!");
            e.printStackTrace();
        }
    }
}
