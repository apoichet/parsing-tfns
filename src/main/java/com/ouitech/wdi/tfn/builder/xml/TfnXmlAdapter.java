package com.ouitech.wdi.tfn.builder.xml;

import com.ouitech.wdi.tfn.builder.xml.TfnStateEnum;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlLaunch;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlResult;
import com.ouitech.wdi.tfn.common.TfnAdapter;

import org.apache.commons.lang.StringUtils;

public class TfnXmlAdapter implements TfnAdapter<TfnXmlLaunch, TfnXmlResult>{

    @Override
    public TfnXmlResult adapt(TfnXmlLaunch tfnLaunch) {

        TfnXmlResult.Builder tfnResultBuilder = TfnXmlResult.builder()
                .withProjectName(tfnLaunch.getInput().getProjectName())
                .withFileName(tfnLaunch.getInput().getFileName())
                .withInterfaces(tfnLaunch.getInput().getTfnInterface())
                .withTestSuite(tfnLaunch.getInput().getTestSuite())
                .withTestCase(tfnLaunch.getInput().getTestCase());

        TfnStateEnum status = TfnStateEnum.NONE;

        if (tfnLaunch.getOutput().isPresent()){

            tfnResultBuilder.withProfil(tfnLaunch.getOutput().get().getProfile())
                    .withTime(tfnLaunch.getOutput().get().getTime());

            if (tfnLaunch.getOutput().get().isSkipped()){
                status = TfnStateEnum.SKIPPED;
            }
            else if(tfnLaunch.getOutput().get().isFailed()){
                status = TfnStateEnum.FAILED;
            }
            else if(tfnLaunch.getOutput().get().isError()){
                status = TfnStateEnum.ERROR;
            }

            tfnResultBuilder.withCause(
                    tfnLaunch.getOutput().get().getCause().isPresent() ?
                            tfnLaunch.getOutput().get().getCause().get().getMessage() :
                            StringUtils.EMPTY);

        }
        else{

            if (tfnLaunch.getInput().isInactive()){
                status = TfnStateEnum.INACTIVE;
            }
        }

        return tfnResultBuilder
                .withStatus(status)
                .build();
    }
}
