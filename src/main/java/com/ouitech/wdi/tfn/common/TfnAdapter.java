package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.builder.xml.TfnXmlResult;

public interface TfnAdapter<LAUNCH extends TfnLaunch, RESULT extends TfnXmlResult> {


    RESULT adapt(LAUNCH tfnLaunch);

}
