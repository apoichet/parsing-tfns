package com.ouitech.wdi.tfn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyProperties {

    private static final String SEPARATOR = ";";
    public static final String PARENT_INPUT_FOLDER_PATH = "parentFolder.input.path";
    public static final String PARENT_OUTPUT_FOLDER_PATH = "parentFolder.output.path";
    public static final String FILES = "files";
    public static final String INTERFACES = "interfaces";
    public static final String RESULTS_CSV_PATH = "results.csv.path";
    public static final String RESULTS_JSON_PATH = "results.json.path";

    private static Properties props;

    static {
        try {
            InputStream input = ClassLoader.getSystemResourceAsStream("naming.properties");
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

    public static String getFolderInputParentName(){
        return props.getProperty(PARENT_INPUT_FOLDER_PATH);
    }

    public static String getFolderOutputParentName(){
        return props.getProperty(PARENT_OUTPUT_FOLDER_PATH);
    }

    public static List<String> getFileTfnToScan(){
        String tfns = props.getProperty(FILES);
        return Arrays.asList(tfns.split(SEPARATOR));
    }

    public static List<String> getInterfacesToScan(){
        String interfaces = props.getProperty(INTERFACES);
        return Arrays.asList(interfaces.split(SEPARATOR));
    }





}
