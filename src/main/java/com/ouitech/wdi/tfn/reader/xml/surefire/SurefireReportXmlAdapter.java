package com.ouitech.wdi.tfn.reader.xml.surefire;

import com.ouitech.wdi.tfn.common.TfnAdapter;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnStateEnum;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlLaunch;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;
import org.apache.commons.lang.StringUtils;

import static com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnStateEnum.*;

public class SurefireReportXmlAdapter implements TfnAdapter<TfnXmlLaunch, TfnXmlResult>{

    @Override
    public TfnXmlResult adapt(TfnXmlLaunch tfnLaunch) {

        TfnXmlResult.Builder tfnResultBuilder = TfnXmlResult.builder()
                .withProjectName(tfnLaunch.getInput().getProjectName())
                .withFileName(tfnLaunch.getInput().getFileName())
                .withInterfaces(tfnLaunch.getInput().getTfnInterface())
                .withTestSuite(tfnLaunch.getInput().getTestSuite())
                .withTestCase(tfnLaunch.getInput().getTestCase());

        TfnStateEnum status = NONE;

        if (tfnLaunch.getOutput().isPresent()){

            tfnResultBuilder.withProfil(tfnLaunch.getOutput().get().getProfile())
                    .withTime(tfnLaunch.getOutput().get().getTime());

            if (tfnLaunch.getOutput().get().isSkipped()){
                status = SKIPPED;
            }
            else if(tfnLaunch.getOutput().get().isFailed()){
                status = FAILED;
            }
            else if(tfnLaunch.getOutput().get().isError()){
                status = ERROR;
            }

            tfnResultBuilder.withCause(
                    tfnLaunch.getOutput().get().getCause().isPresent() ?
                            tfnLaunch.getOutput().get().getCause().get().getMessage() :
                            StringUtils.EMPTY);

        }
        else{

            if (tfnLaunch.getInput().isInactive()){
                status = INACTIVE;
            }
        }

        return tfnResultBuilder
                .withStatus(status)
                .build();
    }
}
