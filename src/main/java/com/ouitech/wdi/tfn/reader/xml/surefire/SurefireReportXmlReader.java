package com.ouitech.wdi.tfn.reader.xml.surefire;

import com.ouitech.wdi.tfn.common.ReaderTfn;
import com.ouitech.wdi.tfn.common.TfnAdapter;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnInputXml;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnOutputXml;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlLaunch;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnXmlResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SurefireReportXmlReader implements ReaderTfn<TfnXmlResult> {

    private TfnAdapter<TfnXmlLaunch, TfnXmlResult> adapter = new SurefireReportXmlAdapter();
    private SurefireReaderTfnXmlInput readerInput = new SurefireReaderTfnXmlInput();
    private SurefireReaderTfnXmlOutput readerOutput = new SurefireReaderTfnXmlOutput();

    @Override
    public List<TfnXmlResult> parsing() {

        //Input
        List<TfnInputXml> tfnInputXmls = readerInput.parsing();

        //Output
        List<TfnOutputXml> tfnOutputXmls = readerOutput.parsing();

        //Merge
        List<TfnXmlLaunch> tfnXmlLaunches = new ArrayList<>();

        for (TfnInputXml tfnInputXml : tfnInputXmls) {

            Optional<TfnOutputXml> tfnOutputXmlutput = tfnOutputXmls.stream()
                    .filter(output -> output.getTfnInterface().equals(tfnInputXml.getTfnInterface()))
                    .filter(output -> output.getTestCase().equals(tfnInputXml.getTestCase()))
                    .findFirst();

            tfnXmlLaunches.add(new TfnXmlLaunch(tfnInputXml, tfnOutputXmlutput));
        }

        //Adapt et trie
        return tfnXmlLaunches.stream()
                .map(adapter::adapt)
                .collect(Collectors.toList());
    }
}
