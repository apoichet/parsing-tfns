package com.ouitech.wdi.tfn.common;

public interface TfnPrinter<TFN extends TfnResult> {

	String print(TFN tfn);

}
