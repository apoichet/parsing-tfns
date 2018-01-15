package com.ouitech.wdi.tfn;

import com.ouitech.wdi.tfn.domain.Results;
import com.ouitech.wdi.tfn.domain.Tfn;
import com.ouitech.wdi.tfn.service.*;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.ouitech.wdi.tfn.MyProperties.getFolderParentName;

public class Main {

    public static void main(String[] args) {

        File parentFolder = new File(getFolderParentName());

        ReaderTfn<File> reader = new ReaderTfnXml();

        List<Tfn> tfns = reader.parsing(parentFolder);

        WriterResults<Writer> writer = new WriterResultsCsv();

        writer.saveResults(tfns);

    }

    private static void buildWriterResults(Results results, Writer writer){

        String deactivate = String.valueOf(results.totalTfns-results.activateTotalTfns);
        String deactivate5 = String.valueOf(results.totalTfns5-results.activateTotalTfns5);
        String deactivate4 = String.valueOf(results.totalTfns4-results.activateTotalTfns4);
        String deactivate3 = String.valueOf(results.totalTfns3-results.activateTotalTfns3);
        String deactivate2 = String.valueOf(results.totalTfns2-results.activateTotalTfns2);
        String deactivate1 = String.valueOf(results.totalTfns1-results.activateTotalTfns1);

        try {
            writer.append("\n");
            writer.append(";Total;5;4;3;2;1");
            writer.append("\n");
            writer.append("Total;"+results.totalTfns+";"+results.totalTfns5+";"+results.totalTfns4+";"+results.totalTfns3+";"+results.totalTfns2+";"+results.totalTfns1);
            writer.append("\n");
            writer.append("Activate;"+results.activateTotalTfns+";"+results.activateTotalTfns5+";"+results.activateTotalTfns4+";"+results.activateTotalTfns3+";"+results.activateTotalTfns2+";"+results.activateTotalTfns1);
            writer.append("\n");
            writer.append("Deactivate;"+deactivate+";"+deactivate5+";"+deactivate4+";"+deactivate3+";"+deactivate2+";"+deactivate1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
