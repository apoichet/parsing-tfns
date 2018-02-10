package com.ouitech.wdi.tfn.builder.xml.output.surefire;

import java.util.Optional;

public class TfnOutputXml {

    private String fileName;
    private String testSuite;
    private String testCase;
    private String tfnInterface;
    private String profile;
    private String time;
    private Optional<Event> event;

    public String getFileName() {
        return fileName;
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

    public String getProfile() {
        return profile;
    }

    public String getTime() {
        return time;
    }

    public Optional<Event> getEvent() {
        return event;
    }

    public TfnOutputXml(Builder builder) {
        this.fileName = builder.fileName;
        this.testSuite = builder.testSuite;
        this.testCase = builder.testCase;
        this.tfnInterface = builder.tfnInterface;
        this.profile = builder.profile;
        this.time = builder.time;
        this.event = builder.event;
        this.event = builder.event;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String fileName;
        private String testSuite;
        private String testCase;
        private String tfnInterface;
        private String profile;
        private String time;
        private Optional<Event> event;

        public Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder withTestSuite(String testSuite) {
            this.testSuite = testSuite;
            return this;
        }

        public Builder withTestCase(String testCase) {
            this.testCase = testCase;
            return this;
        }

        public Builder withTfnInterface(String tfnInterface) {
            this.tfnInterface = tfnInterface;
            return this;
        }

        public Builder withProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public Builder withTime(String time) {
            this.time = time;
            return this;
        }

        public Builder withEvent(Optional<Event> event) {
            this.event = event;
            return this;
        }

        public TfnOutputXml build(){
            return new TfnOutputXml(this);
        }
    }






}
