package com.ouitech.wdi.tfn.adapter;

import com.ouitech.wdi.tfn.domain.Tfn;
import com.ouitech.wdi.tfn.domain.TfnInput;
import com.ouitech.wdi.tfn.domain.TfnOutput;

public interface TfnAdapter<I extends TfnInput, O extends TfnOutput> {


    Tfn adapt(I input, O output);

}
