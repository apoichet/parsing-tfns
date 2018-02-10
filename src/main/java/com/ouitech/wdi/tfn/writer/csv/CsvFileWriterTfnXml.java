package com.ouitech.wdi.tfn.writer.csv;

import com.ouitech.wdi.tfn.common.TfnPrinter;
import com.ouitech.wdi.tfn.common.TfnWriter;
import com.ouitech.wdi.tfn.builder.xml.input.Request;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsCsvPath;

public class CsvFileWriterTfnXml implements TfnWriter<TfnXmlResult>, TfnPrinter<TfnXmlResult> {

    public static final String TITLE = "File Name;Project Name;Test Suite;Test Case;Status;Interface;Time;Profile;Cause;Requests\n";

    @Override
    public void save(Collection<TfnXmlResult> tfnResults) {
        Writer writer = null;
        try {
            writer = new FileWriter(getOutputResultsCsvPath());

            //Ecriture des titres
            writer.append(TITLE);

            //Ecriture des resultats
            for (TfnXmlResult tfnResult : tfnResults) {
                writer.append(print(tfnResult));
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
    }

    @Override
    public String print(TfnXmlResult tfnXmlResult) {
        return tfnXmlResult.getFileName()+";"+
                tfnXmlResult.getProjectName()+";"+
                tfnXmlResult.getTestSuite()+";"+
                tfnXmlResult.getTestCase()+";"+
                tfnXmlResult.getState()+";"+
                tfnXmlResult.getTfnInterface()+";"+
                tfnXmlResult.getTime()+";"+
                tfnXmlResult.getProfil()+";"+
                tfnXmlResult.getCause()+";"+
                printRequest(tfnXmlResult.getRequests())+"\n";

    }

    private String printRequest(List<Request> requests){

        StringBuilder print = new StringBuilder();

        requests.forEach(request -> print.append(request.getService()).append(";"));

        return print.toString();

    }
}
