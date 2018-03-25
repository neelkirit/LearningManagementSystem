package com.amogh.lms.ingester;

import com.amogh.lms.AmoghServerApp;
import com.amogh.lms.ApplicationWebXml;
import com.amogh.lms.config.DefaultProfileUtil;
import com.amogh.lms.ingester.model.IngestModel;
import io.swagger.models.auth.In;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.XmlEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class RunIngest extends AmoghServerApp {

    public RunIngest(Environment env) {
        super(env);
    }

    public static void main(String[] args) {
        for (String arg :args) {
            System.out.println("Args passed in [" + args +"]");
        }
        String excelFilePath = args[ 0 ];
        String environment = args[ 1 ];

        SpringApplication app = new SpringApplication(RunIngest.class);
        DefaultProfileUtil.addDefaultProfile(app);
        ConfigurableApplicationContext context = app.run(args);
        Ingest ingester = context.getBean(Ingest.class);
        try{
            System.out.println("Got the ingest instance... FINALLY!!!");
            List<IngestModel> ingestModels = ingester.process(excelFilePath);
            ingester.persist(ingestModels);
            System.out.println("Exiting process... SUCCESS!!");
        } catch (Exception e) {
            System.out.println("Exiting process... Failed!!");
        }
        System.exit(0);
    }
}
