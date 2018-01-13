package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.domain.Tfn;

import java.io.File;
import java.util.List;

public interface ReaderTfn<F extends File> {

    List<Tfn> parsing(F file);

}
