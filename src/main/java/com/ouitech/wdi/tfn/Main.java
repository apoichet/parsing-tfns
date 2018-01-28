package com.ouitech.wdi.tfn;

import com.ouitech.wdi.tfn.common.FactoryReaderTfn;
import com.ouitech.wdi.tfn.common.FactoryWriterTfn;
import com.ouitech.wdi.tfn.common.ReaderTfn;
import com.ouitech.wdi.tfn.common.WriterTfn;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;

import java.util.List;

import static com.ouitech.wdi.tfn.common.FactoryReaderTfn.XML_SUREFIRE_REPORT;
import static com.ouitech.wdi.tfn.common.FactoryWriterTfn.CSV_FILE;

public class Main {

    public static void main(String[] args) {

        //Factory reader
        ReaderTfn reader = FactoryReaderTfn.create(XML_SUREFIRE_REPORT);

        //Façade transformation en objet java
        List<TfnXmlResult> parsingTfnResults = reader.parsing();

        //Factory writer
        WriterTfn writer = FactoryWriterTfn.create(CSV_FILE);

        //Facçade export resultats
        writer.save(parsingTfnResults);

    }
}
