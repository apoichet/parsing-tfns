package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;

import java.util.List;

public interface WriterTfn<TFN extends TfnXmlResult> {

    /**
     * Permet d'enregistrer les résultats de la validation des Tfns
     * @param tfns les résultats des tfns
     */
    void save(List<TFN> tfns);

}
