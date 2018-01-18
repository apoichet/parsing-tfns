package com.ouitech.wdi.tfn.service;

import java.io.Writer;
import java.util.List;

import com.ouitech.wdi.tfn.domain.Tfn;

public interface WriterResults<F extends Writer> {

    F saveResults(List<Tfn> tfns);

}
