package com.ouitech.wdi.tfn.writer.csv;

import com.ouitech.wdi.tfn.common.WriterTfn;
import com.ouitech.wdi.tfn.builder.xml.input.Request;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.ouitech.wdi.tfn.MyProperties.getOutputResultsCsvPath;

public class CsvResultTfnWriter implements WriterTfn<TfnXmlResult> {

    public static final String TITLE = "File Name;Project Name;Test Suite;Test Case;Status;Interface;Request\n";

    @Override
    public void save(Collection<TfnXmlResult> tfnResults) {
        Writer writer = null;
        try {
            writer = new FileWriter(getOutputResultsCsvPath());

            //Ecriture des titres
            writer.append(TITLE);

            //Ecriture des resultats
            for (TfnXmlResult tfnResult : tfnResults) {
                writer.append(printTfn(tfnResult));
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

    private String printTfn(TfnXmlResult tfnResult){
        return tfnResult.getFileName()+";"+
                tfnResult.getProjectName()+";"+
                tfnResult.getTestSuite()+";"+
                tfnResult.getTestCase()+";"+
                tfnResult.getStatus()+";"+
                tfnResult.getTfnInterface()+";"+
                printRequest(tfnResult.getRequests())+"\n";
    }

    private String printRequest(List<Request> requests){

        StringBuilder print = new StringBuilder();

        requests.forEach(request -> print.append(request.getService()).append(";"));

        return print.toString();

    }

}
