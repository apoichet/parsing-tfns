package com.ouitech.wdi.tfn.domain;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Tfn implements Serializable{

    private String testCase;
    private String testSuite;
    private String fileName;
    private boolean inactive;
    private List<String> interfaces;

    private Tfn(Builder builder) {
        this.testCase = builder.testCase;
        this.testSuite = builder.testSuite;
        this.fileName = builder.fileName;
        this.inactive = builder.inactive;
        this.interfaces = builder.interfaces;
    }

    public boolean is(Predicate<Tfn> predicate){
        return predicate.evaluate(this);
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

    public boolean isInactive() {
        return inactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tfn tfn = (Tfn) o;

        return testCase.equals(tfn.testCase);
    }

    public String isInactive(boolean inactive){

        return inactive?"Desactive":"Active";

    }

    public int nbrInterface(){
        return CollectionUtils.isEmpty(interfaces) ? 0 : interfaces.size();

    }

    public boolean addInterface(String tfnInterface){
        return CollectionUtils.isNotEmpty(interfaces) && interfaces.add(tfnInterface);
    }

    public static Comparator<Tfn> compare() {
        return Comparator.comparing(Tfn::nbrInterface)
                .thenComparing(Tfn::getFileName)
                .thenComparing(Tfn::getTestSuite)
                .thenComparing((Function<? super Tfn, ? extends Boolean>) Tfn::isInactive);
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String testCase;
        private String testSuite;
        private String fileName;
        private boolean inactive;
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

        public Builder withInactive(String inactive) {
            this.inactive = Boolean.parseBoolean(inactive);
            return this;
        }

        public Builder withInactive(boolean inactive) {
            this.inactive = inactive;
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
