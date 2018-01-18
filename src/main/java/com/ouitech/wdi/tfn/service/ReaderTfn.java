package com.ouitech.wdi.tfn.service;

import java.io.File;
import java.util.List;

import com.ouitech.wdi.tfn.domain.Tfn;

public interface ReaderTfn<F extends File> {

    List<Tfn> parsing(F file);

}
