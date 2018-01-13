package com.ouitech.wdi.tfn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MyProperties {

    private static final String SEPARATOR = ";";
    public static final String CON_TEST_SUITE = "testSuite.tagName";
    public static final String CON_TEST_CASE = "testCase.tagName";
    public static final String PARENT_FOLDER_PATH = "parentFolder.path";
    public static final String FILES = "files";
    public static final String INTERFACES = "interfaces";
    public static final String ATRIBUT_FAIL_ON_ERROR = "atribut.failOnError";
    public static final String ATRIBUT_NAME = "atribut.name";
    public static final String RESULTS_CSV_PATH = "results.csv.path";

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

    public static String getOutputResultsCsvPath(){
        return props.getProperty(RESULTS_CSV_PATH);
    }

    public static String getAtributFailOnError(){
        return props.getProperty(ATRIBUT_FAIL_ON_ERROR);
    }

    public static String getAtributName(){
        return props.getProperty(ATRIBUT_NAME);
    }

    public static String getTestSuiteTagName(){
        return props.getProperty(CON_TEST_SUITE);
    }

    public static String getTestCaseTagName(){
        return props.getProperty(CON_TEST_CASE);
    }

    public static String getFolderParentName(){
        return props.getProperty(PARENT_FOLDER_PATH);
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
