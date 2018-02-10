package com.ouitech.wdi.tfn.builder.xml.output.surefire;

import com.ouitech.wdi.tfn.MyProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Optional.*;
import static java.util.stream.Collectors.toList;

public class SurefireReaderTfnXmlOutput {

    private static final String XML_SUFFIX = ".xml";
    private static final String TEST_SUITE_TAG_NAME = "testsuite";
    private static final String TEST_CASE_TAG_NAME = "testcase";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String TIME_ATTRIBUTE = "time";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String TYPE_ATTRIBUTE = "type";

    public List<TfnOutputXml> parsing() {

        File file = new File(MyProperties.getFolderOutputParentName());

        //Recupération des dossiers interface
        List<File> interfaceFolders = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> MyProperties.getInterfacesToScan().contains(f.getName()))
                .collect(toList());

        //Récupération des fichiers xml
        Map<String, List<File>> mapXmlFiles = getXmlFiles(interfaceFolders);

        //Construction et Mapping des Tfns
        return parsingXmlTfnFiles(mapXmlFiles);
    }

    private Map<String, List<File>> getXmlFiles(List<File> interfaceFolders) {
        Map<String, List<File>> mapXmlFiles = new HashMap<>();

        for (File interfaceFolder : interfaceFolders) {

            String folder = interfaceFolder.getName();

            List<File> xmlFiles = findXmlFiles(interfaceFolder);

            mapXmlFiles.put(folder, xmlFiles);
        }

        return mapXmlFiles;
    }

    private List<File> findXmlFiles(File folder) {

        List<File> xmlFiles = new ArrayList<>();

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(XML_SUFFIX)) {
                xmlFiles.add(file);
            } else if (file.isDirectory()) {
                xmlFiles.addAll(findXmlFiles(file));
            }
        }

        return xmlFiles;
    }

    private List<TfnOutputXml> parsingXmlTfnFiles(Map<String, List<File>> xmlTfnFiles) {
        List<TfnOutputXml> tfns = new ArrayList<>();

        xmlTfnFiles.forEach((key, value) -> value
                .forEach(file -> tfns.addAll(parsingXmlTfnFile(key, file))));

        return tfns;
    }

    private List<TfnOutputXml> parsingXmlTfnFile(String folderInterface, File xmlFile) {

        List<TfnOutputXml> tfns = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element testSuite = (Element) doc.getElementsByTagName(TEST_SUITE_TAG_NAME).item(0);
            NodeList testCases = doc.getElementsByTagName(TEST_CASE_TAG_NAME);

            for (int i = 0; i < testCases.getLength(); i++) {

                Element testCase = (Element) testCases.item(i);

                String testCaseName = testCase.getAttribute(NAME_ATTRIBUTE);
                int indexEquals = testCaseName.indexOf('=');
                int indexEndProfil = testCaseName.indexOf(']', indexEquals);
                String profil = testCaseName.substring(indexEquals+1, indexEndProfil);
                testCaseName = testCaseName.substring(indexEndProfil+1);

                TfnOutputXml.Builder builderOuptut = TfnOutputXml.builder()
                        .withFileName(xmlFile.getName())
                        .withTfnInterface(folderInterface)
                        .withTestSuite(testSuite.getAttribute(NAME_ATTRIBUTE))
                        .withTestCase(testCase.getAttribute(NAME_ATTRIBUTE))
                        .withTime(testCase.getAttribute(TIME_ATTRIBUTE))
                        .withProfile(profil)
                        .withTestCase(testCaseName)
                        .withEvent(buildEventTestCase(testCase));

                tfns.add(builderOuptut.build());
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return tfns;
    }

    private Optional<Event> buildEventTestCase(Element testCase) {

        Node testCaseChild = testCase.getFirstChild();

        if (testCaseChild!=null){

            Element testCaseEvent = (Element) testCaseChild;
            Optional<String> type = ofNullable(testCaseEvent.getAttribute(TYPE_ATTRIBUTE));
            Optional<String> message = ofNullable(testCaseEvent.getAttribute(MESSAGE_ATTRIBUTE));

            Event event = new Event(testCaseChild.getNodeName(), type, message);

            return of(event);
        }
        return empty();
    }

}
