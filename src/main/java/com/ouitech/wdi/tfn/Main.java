package com.ouitech.wdi.tfn;

import com.ouitech.wdi.tfn.domain.Tfn;
import com.ouitech.wdi.tfn.service.ReaderTfn;
import com.ouitech.wdi.tfn.service.ReaderTfnXml;
import com.ouitech.wdi.tfn.service.WriterResults;
import com.ouitech.wdi.tfn.service.WriterResultsCsv;

import java.io.File;
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

}
