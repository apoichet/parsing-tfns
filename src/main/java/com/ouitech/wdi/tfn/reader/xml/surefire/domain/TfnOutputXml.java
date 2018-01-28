package com.ouitech.wdi.tfn.reader.xml.surefire.domain;

import java.util.Optional;

public class TfnOutputXml {

    private String fileName;
    private String testSuite;
    private String testCase;
    private String tfnInterface;
    private String profile;
    private String time;
    private boolean skipped;
    private boolean failed;
    private boolean error;
    private Optional<Cause> cause;

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

    public boolean isSkipped() {
        return skipped;
    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isError() {
        return error;
    }

    public Optional<Cause> getCause() {
        return cause;
    }

    public TfnOutputXml(Builder builder) {
        this.fileName = builder.fileName;
        this.testSuite = builder.testSuite;
        this.testCase = builder.testCase;
        this.tfnInterface = builder.tfnInterface;
        this.profile = builder.profile;
        this.time = builder.time;
        this.skipped = builder.skipped;
        this.failed = builder.failed;
        this.error = builder.error;
        this.cause = builder.cause;
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
        private boolean skipped;
        private boolean failed;
        private boolean error;
        private Optional<Cause> cause;

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

        public Builder withSkipped(boolean skipped) {
            this.skipped = skipped;
            return this;
        }

        public Builder withFailed(boolean failed) {
            this.failed = failed;
            return this;
        }

        public Builder withError(boolean error) {
            this.error = error;
            return this;
        }

        public Builder withCause(Optional<Cause> cause) {
            this.cause = cause;
            return this;
        }

        public TfnOutputXml build(){
            return new TfnOutputXml(this);
        }
    }






}
