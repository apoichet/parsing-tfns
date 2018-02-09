package com.ouitech.wdi.tfn.builder.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ouitech.wdi.tfn.builder.xml.input.SurefireReaderTfnXmlInput;
import com.ouitech.wdi.tfn.builder.xml.input.TfnInputXml;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.TfnOutputXml;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.SurefireReaderTfnXmlOutput;
import com.ouitech.wdi.tfn.common.AbstractTfnResultBuilder;
import com.ouitech.wdi.tfn.common.TfnAdapter;

public class TfnXmlBuilder extends AbstractTfnResultBuilder<TfnXmlResult>{

    private TfnAdapter<TfnXmlLaunch, TfnXmlResult> adapter = new TfnXmlAdapter();
    private SurefireReaderTfnXmlInput readerInput = new SurefireReaderTfnXmlInput();
    private SurefireReaderTfnXmlOutput readerOutput = new SurefireReaderTfnXmlOutput();

    @Override
    public List<TfnXmlResult> build() {

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
				.sorted(TfnXmlResult.compare())
                .collect(Collectors.toList());
    }
}
