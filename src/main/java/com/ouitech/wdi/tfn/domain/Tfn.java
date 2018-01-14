package com.ouitech.wdi.tfn.domain;

import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tfn implements Serializable{

    private String testCase;
    private String testSuite;
    private String fileName;
    private boolean active;
    private List<String> interfaces = new ArrayList<>();

    private Tfn(Builder builder) {
        this.testCase = builder.testCase;
        this.testSuite = builder.testSuite;
        this.fileName = builder.fileName;
        this.active = builder.active;
        this.interfaces = builder.interfaces;
    }

    public String getTestCase() {
        return testCase;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public String getTestSuite() {
        return testSuite;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tfn tfn = (Tfn) o;

        return testCase.equals(tfn.testCase);
    }

    public String isActive(boolean active){

        return active?"Active":"Desactive";

    }

    public int nbrInterface(){
        return CollectionUtils.isEmpty(interfaces) ? 0 : interfaces.size();

    }

    public boolean addInterface(String tfnInterface){
        return CollectionUtils.isNotEmpty(interfaces) && interfaces.add(tfnInterface);
    }

    @Override
    public String toString() {
        return fileName + ";"
                + testSuite + ";"
                + testCase + ";"
                + isActive(active) + ";"
                + interfaces + ";"
                + nbrInterface() + "\n";
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String testCase;
        private String testSuite;
        private String fileName;
        private boolean active;
        private List<String> interfaces = new ArrayList<>();

        public Builder withTestCase(String testCase) {
            this.testCase = testCase;
            return this;
        }

        public Builder withTestSuite(String testSuite) {
            this.testSuite = testSuite;
            return this;
        }

        public Builder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder withActive(String active) {
            this.active = Boolean.parseBoolean(active);
            return this;
        }

        public Builder withActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder withFirstInterface(String firstInterface) {
            this.interfaces = new ArrayList<>();
            this.interfaces.add(firstInterface);
            return this;
        }

        public Builder withInterfaces(List<String> interfaces) {
            this.interfaces = interfaces;
            return this;
        }

        public Tfn build(){
            return new Tfn(this);
        }
    }



}
