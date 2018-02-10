package com.ouitech.wdi.tfn.common;

public interface TfnStatusManager<LAUNCH extends TfnLaunch> {

    TfnStateEnum define(LAUNCH tfnLaunch);

}
