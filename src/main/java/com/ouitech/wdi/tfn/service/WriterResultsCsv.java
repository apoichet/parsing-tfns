package com.ouitech.wdi.tfn.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsCsvPath;

import com.ouitech.wdi.tfn.domain.Request;
import com.ouitech.wdi.tfn.domain.Tfn;

public class WriterResultsCsv implements WriterResults<Writer> {

    public static final String TITLE = "File Name;Project Name;Test Suite;Test Case;Status;Interface;Request\n";

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

    private String printTfn(Tfn tfn){
        return tfn.getFileName()+";"+
                tfn.getProjectName()+";"+
                tfn.getTestSuite()+";"+
                tfn.getTestCase()+";"+
                tfn.getStatus()+";"+
                tfn.getTfnInterface()+";"+
                printRequest(tfn.getRequests())+"\n";
    }

    private String printRequest(List<Request> requests){

        StringBuilder print = new StringBuilder();

        requests.forEach(request -> print.append(request.getName()).append(";"));

        return print.toString();

    }
}
