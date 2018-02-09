package com.ouitech.wdi.tfn;

import static com.ouitech.wdi.tfn.common.FactoryBuilderTfn.XML_SUREFIRE_REPORT;
import static com.ouitech.wdi.tfn.common.FactoryWriterTfn.CSV_FILE;

import java.util.Collection;

import com.ouitech.wdi.tfn.common.AbstractTfnResultBuilder;
import com.ouitech.wdi.tfn.common.FactoryBuilderTfn;
import com.ouitech.wdi.tfn.common.FactoryWriterTfn;
import com.ouitech.wdi.tfn.common.TfnResult;
import com.ouitech.wdi.tfn.common.WriterTfn;

public class Main {

    public static void main(String[] args) {

        //Factory Builder
        AbstractTfnResultBuilder builder = FactoryBuilderTfn.create(XML_SUREFIRE_REPORT);

        //Construction en objet java
        Collection tfnResults = builder.build();

        //Factory writer
        WriterTfn writer = FactoryWriterTfn.create(CSV_FILE);

        //Export des résultats
        writer.save(tfnResults);

    }
}
