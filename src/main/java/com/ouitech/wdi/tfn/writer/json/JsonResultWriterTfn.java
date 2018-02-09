package com.ouitech.wdi.tfn.writer.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ouitech.wdi.tfn.common.WriterTfn;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsJsonPath;

public class JsonResultWriterTfn implements WriterTfn<TfnXmlResult>{


    @Override
    public void save(List<TfnXmlResult> tfnResults) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        FileWriter writer = null;

        try {
            writer = new FileWriter(getOutputResultsJsonPath());

            String results = mapper.writeValueAsString(tfnResults);

            writer.write(results);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
