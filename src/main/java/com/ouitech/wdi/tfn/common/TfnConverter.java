package com.ouitech.wdi.tfn.common;

public interface TfnConverter<LAUNCH extends TfnLaunch, RESULT extends TfnResult> {

    RESULT transform(LAUNCH tfnLaunch);
}
