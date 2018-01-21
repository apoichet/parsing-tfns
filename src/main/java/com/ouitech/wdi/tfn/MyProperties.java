package com.ouitech.wdi.tfn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyProperties {

    private static final String SEPARATOR = ";";
    private static final String PARENT_FOLDER_PATH = "parentFolder.path";
    private static final String FILES = "files";
    private static final String INTERFACES = "interfaces";
    private static final String RESULTS_CSV_PATH = "results.csv.path";
    private static final String RESULTS_JSON_PATH = "results.json.path";

    private static Properties props;

    private static final String NAMING_PROPERTIES_FILE = "naming.properties";

    static {
        try {
            InputStream input = ClassLoader.getSystemResourceAsStream(NAMING_PROPERTIES_FILE);
            props = new Properties();
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getOutputResultsJsonPath(){
        return props.getProperty(RESULTS_JSON_PATH);
    }

    public static String getOutputResultsCsvPath(){
        return props.getProperty(RESULTS_CSV_PATH);
    }

    static String getFolderParentName(){
        return props.getProperty(PARENT_FOLDER_PATH);
    }

    public static List<String> getFileTfnToScan(){
        String tfnFiles = props.getProperty(FILES);
        return Arrays.asList(tfnFiles.split(SEPARATOR));
    }

    public static List<String> getInterfacesToScan(){
        String interfaces = props.getProperty(INTERFACES);
        return Arrays.asList(interfaces.split(SEPARATOR));
    }





}
