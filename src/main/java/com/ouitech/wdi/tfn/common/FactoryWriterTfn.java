package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.writer.csv.CsvResultTfnWriter;
import com.ouitech.wdi.tfn.writer.json.JsonResultWriterTfn;

public class FactoryWriterTfn {

    public static final String CSV_FILE = "CSV_FILE";
    public static final String ELSATIC_SEARCH = "ELSATIC_SEARCH";
    public static final String JSON_FILE = "JSON_FILE";

    public static WriterTfn create(final String writer){

        switch (writer){

            case CSV_FILE : return new CsvResultTfnWriter();
            case JSON_FILE : return new JsonResultWriterTfn();
            case ELSATIC_SEARCH: return null;

        }

        throw new IllegalArgumentException("Pas de writer correspondant !");
    }


}
