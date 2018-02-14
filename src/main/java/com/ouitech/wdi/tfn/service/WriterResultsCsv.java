package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.domain.Tfn;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsCsvPath;

public class WriterResultsCsv implements WriterResults<Writer> {

    public static final String TITLE = "File Name;Test Suite;Test Case;Activate;Interfaces;Occurrence\n";

    @Override
    public Writer saveResults(List<Tfn> tfns) {
        Writer writer = null;
        try {
            writer = new FileWriter(getOutputResultsCsvPath());

            //Ecriture des titres
            writer.append(TITLE);

            //Ecriture des resultats
            for (Tfn tfn : tfns) {
                writer.append(printTfn(tfn));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    private String printTfn(final Tfn tfn){
        return tfn.getFileName()+ ";"
                + tfn.getTestSuite() + ";"
                + tfn.getTestCase() + ";"
                + tfn.isInactive(tfn.isInactive())+ ";"
                + tfn.getInterfaces()+ ";"
                + tfn.nbrInterface() + "\n";
    }
}
