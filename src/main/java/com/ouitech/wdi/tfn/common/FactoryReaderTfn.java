package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.reader.xml.surefire.SurefireReportXmlReader;

public class FactoryReaderTfn {


    public static final String XML_SUREFIRE_REPORT = "XML_SUREFIRE_REPORT";

    public static ReaderTfn create(final String reader){

        switch (reader){

            case XML_SUREFIRE_REPORT: return new SurefireReportXmlReader();

        }

        throw new IllegalArgumentException("Il n'existe pas de Reader TfnXmlResult correspondant !");

    }

}
