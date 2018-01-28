package com.ouitech.wdi.tfn.reader.xml.surefire.domain;

import java.util.List;

public class TfnInputXml {

    private String fileName;
    private String projectName;
    private String testSuite;
    private String testCase;
    private String tfnInterface;
    private boolean inactive;
    private List<Request> requests;

    public TfnInputXml(TfnInputXml.Builder builder) {
        this.fileName = builder.fileName;
        this.projectName = builder.projectName;
        this.testSuite = builder.testSuite;
        this.testCase = builder.testCase;
        this.tfnInterface = builder.tfnInterface;
        this.inactive = builder.inactive;
        this.requests = builder.requests;
    }

    public String getFileName() {
        return fileName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTestSuite() {
        return testSuite;
    }

    public String getTestCase() {
        return testCase;
    }

    public String getTfnInterface() {
        return tfnInterface;
    }

    public boolean isInactive() {
        return inactive;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public static TfnInputXml.Builder builder(){
        return new TfnInputXml.Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TfnInputXml tfn = (TfnInputXml) o;

        return testCase.equals(tfn.testCase);
    }

    public static class Builder{
        private String fileName;
        private String projectName;
        private String testSuite;
        private String testCase;
        private String tfnInterface;
        private boolean inactive;
        private List<Request> requests;

        public TfnInputXml.Builder withTestCase(String testCase) {
            this.testCase = testCase;
            return this;
        }

        public TfnInputXml.Builder withTestSuite(String testSuite) {
            this.testSuite = testSuite;
            return this;
        }

        public TfnInputXml.Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public TfnInputXml.Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }


        public TfnInputXml.Builder withInterfaces(String tfnInterface) {
            this.tfnInterface = tfnInterface;
            return this;
        }

        public TfnInputXml.Builder withInactive(boolean inactive){
            this.inactive = inactive;
            return this;
        }

        public TfnInputXml.Builder withRequest(List<Request> requests){
            this.requests = requests;
            return this;
        }

        public TfnInputXml build(){
            return new TfnInputXml(this);
        }
    }

}