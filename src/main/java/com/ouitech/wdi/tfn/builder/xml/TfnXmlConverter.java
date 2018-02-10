package com.ouitech.wdi.tfn.builder.xml;

import com.ouitech.wdi.tfn.builder.xml.business.TfnXmlStatusManager;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.TfnOutputXml;
import com.ouitech.wdi.tfn.common.TfnStatusManager;
import com.ouitech.wdi.tfn.common.TfnConverter;
import org.apache.commons.lang.StringUtils;

public class TfnXmlConverter implements TfnConverter<TfnXmlLaunch, TfnXmlResult> {

    private TfnStatusManager<TfnXmlLaunch> tfnStatusManager = new TfnXmlStatusManager();

    @Override
    public TfnXmlResult transform(TfnXmlLaunch tfnLaunch) {

        TfnXmlResult.Builder tfnResultBuilder = TfnXmlResult.builder()
                .withProjectName(tfnLaunch.getInput().getProjectName())
                .withFileName(tfnLaunch.getInput().getFileName())
                .withInterfaces(tfnLaunch.getInput().getTfnInterface())
                .withTestSuite(tfnLaunch.getInput().getTestSuite())
                .withTestCase(tfnLaunch.getInput().getTestCase())
                .withRequest(tfnLaunch.getInput().getRequests());

        completeWithOutput(tfnLaunch, tfnResultBuilder);

        return tfnResultBuilder.build();
    }

    private void completeWithOutput(TfnXmlLaunch tfnLaunch, TfnXmlResult.Builder tfnResultBuilder) {
        if (tfnLaunch.getOutput().isPresent()){
            TfnOutputXml tfnOutputXml = tfnLaunch.getOutput().get();

            tfnResultBuilder.withProfil(tfnOutputXml.getProfile())
                    .withTime(tfnOutputXml.getTime())
                    .withCause(getCause(tfnOutputXml));
        }
    }

    private String getCause(TfnOutputXml tfnOutputXml) {
        StringBuilder cause = new StringBuilder();
        tfnOutputXml.getEvent().ifPresent(e-> cause.append(e.message.orElse(StringUtils.EMPTY)));
        return cause.toString();
    }

}
