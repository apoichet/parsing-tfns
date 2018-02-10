package com.ouitech.wdi.tfn;

import static com.ouitech.wdi.tfn.common.FactoryBuilderTfn.XML_SUREFIRE_REPORT;
import static com.ouitech.wdi.tfn.common.FactoryWriterTfn.CSV_FILE;

import java.util.Collection;

import com.ouitech.wdi.tfn.common.AbstractBuilderTfn;
import com.ouitech.wdi.tfn.common.FactoryBuilderTfn;
import com.ouitech.wdi.tfn.common.FactoryWriterTfn;
import com.ouitech.wdi.tfn.common.TfnWriter;

public class Main {

    public static void main(String[] args) {

        //Factory Builder
        AbstractBuilderTfn builder = FactoryBuilderTfn.create(XML_SUREFIRE_REPORT);

        //Build Tfn results
        Collection tfnResults = builder.build();

        //Factory writer
        TfnWriter writer = FactoryWriterTfn.create(CSV_FILE);

        //Export results
        writer.save(tfnResults);

    }
}
