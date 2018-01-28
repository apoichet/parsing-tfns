package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;

public interface TfnAdapter<LAUNCH extends TfnLaunch, RESULT extends TfnXmlResult> {


    RESULT adapt(LAUNCH tfnLaunch);

}
