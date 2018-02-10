package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.writer.csv.CsvFileWriterTfnXml;
import com.ouitech.wdi.tfn.writer.json.JsonFileWriterTfnXml;

public class FactoryWriterTfn {

    public static final String CSV_FILE = "CSV_FILE";
    public static final String ELSATIC_SEARCH = "ELSATIC_SEARCH";
    public static final String JSON_FILE = "JSON_FILE";

    public static TfnWriter create(final String writer){

        switch (writer){

            case CSV_FILE : return new CsvFileWriterTfnXml();
            case JSON_FILE : return new JsonFileWriterTfnXml();
            case ELSATIC_SEARCH: return null;

        }

        throw new IllegalArgumentException("Pas de writer correspondant !");
    }


}
