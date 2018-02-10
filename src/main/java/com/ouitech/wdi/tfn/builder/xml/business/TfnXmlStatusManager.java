package com.ouitech.wdi.tfn.builder.xml.business;

import com.ouitech.wdi.tfn.builder.xml.TfnXmlLaunch;
import com.ouitech.wdi.tfn.builder.xml.input.TfnInputXml;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.Event;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.TfnOutputXml;
import com.ouitech.wdi.tfn.common.TfnStatusManager;
import com.ouitech.wdi.tfn.common.TfnStateEnum;

import static com.ouitech.wdi.tfn.common.TfnStateEnum.*;

public class TfnXmlStatusManager implements TfnStatusManager<TfnXmlLaunch> {

    private static final String SKIPPED_EVENT = "skipped";
    private static final String FAILURE_EVENT = "failure";
    private static final String ERROR_EVENT = "error";

    @Override
    public TfnStateEnum define(TfnXmlLaunch tfnLaunch) {
        if (isInactive(tfnLaunch.getInput())){
            return INACTIVE;
        }
        if (tfnLaunch.getOutput().isPresent()){
            TfnOutputXml outputXml = tfnLaunch.getOutput().get();
            return defineStatusWith(outputXml);
        }
        return NONE;
    }

    private boolean isInactive(TfnInputXml inputXml) {
        return inputXml.isTestSuiteDisabled()
                || inputXml.isDisabled()
                || !inputXml.isFailOnError()
                || !inputXml.isFailTestCaseOnErrors();
    }

    private TfnStateEnum defineStatusWith(TfnOutputXml tfnOutputXml) {

        if (tfnOutputXml.getEvent().isPresent()) {

            Event event = tfnOutputXml.getEvent().get();

            switch (event.state) {
                case FAILURE_EVENT: return FAILED;
                case ERROR_EVENT: return ERROR;
                case SKIPPED_EVENT: return SKIPPED;
                default: return NONE;
            }
        }
        return SUCCESS;
    }
}
