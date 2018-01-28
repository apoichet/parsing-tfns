package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;

import java.util.List;

public interface ReaderTfn<TFN extends TfnXmlResult> {

    /**
     * Permet de trasformer les résultats de validation des tests fonctionnels
     * en objet Java
     *
     * @return Liste d'objet TfnXmlResult
     */
    List<TFN> parsing();


}
