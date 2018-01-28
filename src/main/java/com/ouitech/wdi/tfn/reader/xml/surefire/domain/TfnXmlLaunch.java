package com.ouitech.wdi.tfn.reader.xml.surefire.domain;

import com.ouitech.wdi.tfn.common.TfnLaunch;

import java.util.Optional;

public class TfnXmlLaunch extends TfnLaunch{

    private TfnInputXml input;
    private Optional<TfnOutputXml> output;

    public TfnXmlLaunch(TfnInputXml input, Optional<TfnOutputXml> output) {
        this.input = input;
        this.output = output;
    }

    public TfnXmlLaunch(TfnInputXml input) {
        this.input = input;
    }

    public TfnInputXml getInput() {
        return input;
    }

    public void setInput(TfnInputXml input) {
        this.input = input;
    }

    public Optional<TfnOutputXml> getOutput() {
        return output;
    }

    public void setOutput(Optional<TfnOutputXml> output) {
        this.output = output;
    }

    @Override
    public String getKey() {
        return input.getFileName()+"/"+
                input.getProjectName()+"/"+
                input.getTfnInterface()+"/"+
                input.getTestSuite()+"/"+
                input.getTestCase();
    }
}
