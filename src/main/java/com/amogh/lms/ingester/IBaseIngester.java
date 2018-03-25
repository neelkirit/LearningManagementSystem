package com.amogh.lms.ingester;

import com.amogh.lms.ingester.model.IngestModel;

import java.util.List;

public interface IBaseIngester {

    /**
     * Must have method in all the ingest classes.
     * @param fileName - The name of the excel/CSV file to be ingest
     * @return List of IngestModel which encapsulates information to ve loaded in DB
     */
    List<IngestModel> process(String fileName);

    /**
     * Persist the models built by ingest
     * @param ingestModels list of models to be persisted
     */
    void persist(List<IngestModel> ingestModels);

}
