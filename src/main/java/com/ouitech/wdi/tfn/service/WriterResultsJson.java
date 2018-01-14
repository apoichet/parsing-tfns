package com.ouitech.wdi.tfn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ouitech.wdi.tfn.domain.Tfn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsJsonPath;

public class WriterResultsJson implements WriterResults<Writer>{

    @Override
    public Writer saveResults(List<Tfn> tfns) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        FileWriter writer = null;

        try {
            writer = new FileWriter(getOutputResultsJsonPath());

            String results = mapper.writeValueAsString(tfns);

            writer.write(results);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }
}
