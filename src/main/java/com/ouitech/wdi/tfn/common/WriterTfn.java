package com.ouitech.wdi.tfn.common;

import java.util.Collection;

public interface WriterTfn<TFN extends TfnResult> {

    /**
     * Permet d'enregistrer les résultats de la validation des Tfns
     * @param tfns les résultats des tfns
     */
    void save(Collection<TFN> tfns);

}
