package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.domain.Tfn;

import java.io.Writer;
import java.util.List;

public interface WriterResults<F extends Writer> {

    F saveResults(List<Tfn> tfns);

}
