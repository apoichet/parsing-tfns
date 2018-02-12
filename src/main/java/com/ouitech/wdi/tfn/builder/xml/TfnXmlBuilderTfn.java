package com.ouitech.wdi.tfn.builder.xml;

import com.ouitech.wdi.tfn.builder.xml.input.SurefireReaderTfnXmlInput;
import com.ouitech.wdi.tfn.builder.xml.input.TfnInputXml;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.SurefireReaderTfnXmlOutput;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.TfnOutputXml;
import com.ouitech.wdi.tfn.common.AbstractBuilderTfn;
import com.ouitech.wdi.tfn.common.TfnConverter;
import com.ouitech.wdi.tfn.common.TfnStatusManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TfnXmlBuilderTfn extends AbstractBuilderTfn<TfnXmlLaunch, TfnXmlResult> {

    private SurefireReaderTfnXmlInput readerInput = new SurefireReaderTfnXmlInput();
    private SurefireReaderTfnXmlOutput readerOutput = new SurefireReaderTfnXmlOutput();

    public TfnXmlBuilderTfn(TfnConverter<TfnXmlLaunch, TfnXmlResult> converter, TfnStatusManager<TfnXmlLaunch> manager) {
        super(converter, manager);
    }

    @Override
    public List<TfnXmlLaunch> load() {

        List<TfnInputXml> tfnInput = readerInput.parsing();

        List<TfnOutputXml> tfnOutput = readerOutput.parsing();

        return merge(tfnInput, tfnOutput);
    }

    private List<TfnXmlLaunch> merge(List<TfnInputXml> tfnInputXmls, List<TfnOutputXml> tfnOutputXmls) {
        List<TfnXmlLaunch> tfnXmlLaunches = new ArrayList<>();

        for (TfnInputXml tfnInputXml : tfnInputXmls) {

            Optional<TfnOutputXml> tfnOutputXmlutput = tfnOutputXmls.stream()
                    .filter(output -> output.getTfnInterface().equals(tfnInputXml.getTfnInterface()))
                    .filter(output -> output.getTestCase().equals(tfnInputXml.getTestCase()))
                    .findFirst();

            tfnXmlLaunches.add(new TfnXmlLaunch(tfnInputXml, tfnOutputXmlutput));
        }
        return tfnXmlLaunches;
    }

    @Override
    public Comparator<TfnXmlResult> compare() {
        return Comparator.comparing(TfnXmlResult::getTfnInterface)
                .thenComparing(TfnXmlResult::getFileName)
                .thenComparing(TfnXmlResult::getTestSuite)
                .thenComparing(TfnXmlResult::getTestCase)
                .thenComparing(TfnXmlResult::getStatus);
    }
}
