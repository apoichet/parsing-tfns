package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.MyProperties;
import com.ouitech.wdi.tfn.domain.Tfn;
import org.apache.commons.lang.StringUtils;
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

import static com.ouitech.wdi.tfn.MyProperties.*;
import static com.ouitech.wdi.tfn.domain.Tfn.compare;
import static java.util.stream.Collectors.toList;

public class ReaderTfnXml implements ReaderTfn<File> {

    private static final String DISABLED_ATTRIBUTE = "disabled";
    private static final String FAIL_ON_ERROR_ATTRIBUTE = "failOnError";
    private static final String FAIL_TEST_CASE_ON_ERRORS_ATTRIBUTE = "failTestCaseOnErrors";
    private static final String TRUE_VALUE = "true";
    private static final String FALSE_VALUE = "false";
    private static final String TEST_SUITE_TAG_NAME = "con:testSuite";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String TEST_CASE_TAG_NAME = "con:testCase";

    @Override
    public List<Tfn> parsing(File file) {
        //Recupération des dossiers interface
        List<File> interfaceFolders = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> MyProperties.getInterfacesToScan().contains(f.getName()))
                .collect(toList());

        //Récupération des fichiers xml
        Map<String, List<File>> mapXmlFiles = getXmlFiles(interfaceFolders);

        //Construction et Mapping des Tfns
        Map<String, Tfn> tfnMap = parsingXmlTfnFiles(mapXmlFiles);

        //Retour avec tri
        return tfnMap.values().stream()
                .sorted(compare())
                .collect(toList());

    }

    private Map<String, List<File>> getXmlFiles(List<File> interfaceFolders){
        Map<String, List<File>> mapXmlFiles = new HashMap<>();

        for (File interfaceFolder : interfaceFolders) {

            String folder = interfaceFolder.getName();

            List<File> xmlFiles = findXmlFiles(interfaceFolder);

            mapXmlFiles.put(folder, xmlFiles);
        }

        return mapXmlFiles;
    }

    private List<File> findXmlFiles(File folder){

        List<File> xmlFiles = new ArrayList<>();

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            if (getFileTfnToScan().contains(file.getName())){
                xmlFiles.add(file);
            }
            else if(file.isDirectory()){
                xmlFiles.addAll(findXmlFiles(file));
            }

        }

        return xmlFiles;
    }

    private Map<String, Tfn> parsingXmlTfnFiles(Map<String, List<File>> xmlTfnFiles){

        Map<String, Tfn> mapTfns = new HashMap<>();

        xmlTfnFiles.forEach((key, value) -> value
                .forEach(file -> parsingXmlTfnFile(key, file, mapTfns)));

        return mapTfns;


    }

    private void parsingXmlTfnFile(String folderInterface, File xmlFile, Map<String, Tfn> tfnMap) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList testSuites = doc.getElementsByTagName(TEST_SUITE_TAG_NAME);

            for (int i = 0; i < testSuites.getLength(); i++) {

                NodeList testSuiteChildNodes = testSuites.item(i).getChildNodes();
                Element testSuite = (Element) testSuites.item(i);

                buildTfnWithTestSuites(folderInterface, xmlFile, tfnMap, testSuiteChildNodes, testSuite);
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void buildTfnWithTestSuites(String folderInterface, File xmlFile, Map<String, Tfn> tfnMap, NodeList testSuiteChildNodes, Element testSuite) {
        for (int j = 0; j < testSuiteChildNodes.getLength(); j++) {

            Node testSuiteNode = testSuiteChildNodes.item(j);

            if (testSuiteNode.getNodeType() == Node.ELEMENT_NODE &&
                    TEST_CASE_TAG_NAME.equals(testSuiteNode.getNodeName())){

                Element testCase = (Element) testSuiteNode;
                String testCaseName = testCase.getAttribute(NAME_ATTRIBUTE);

                if (tfnMap.containsKey(testCaseName)){
                    Tfn tfn = tfnMap.get(testCaseName);
                    tfn.addInterface(folderInterface);
                }
                else {

                    Tfn tfn = Tfn.builder()
                            .withFileName(xmlFile.getName())
                            .withFirstInterface(folderInterface)
                            .withTestSuite(testSuite.getAttribute(NAME_ATTRIBUTE))
                            .withTestCase(testCase.getAttribute(NAME_ATTRIBUTE))
                            .withInactive(isInactive(testSuite, testCase))
                            .build();

                    tfnMap.put(testCaseName, tfn);
                }
            }
        }
    }

    private boolean isInactive(Element testSuite, Element testCase) {
        String testCaseDisabled = testCase.getAttribute(DISABLED_ATTRIBUTE);
        String failOnError = testCase.getAttribute(FAIL_ON_ERROR_ATTRIBUTE);
        String failTestCaseOnErrors = testCase.getAttribute(FAIL_TEST_CASE_ON_ERRORS_ATTRIBUTE);
        String testSuiteDisabled = testSuite.getAttribute(DISABLED_ATTRIBUTE);

        boolean testSuiteDisabledBool = StringUtils.isNotEmpty(testSuiteDisabled) && testSuiteDisabled.equals(TRUE_VALUE);
        boolean testCaseDisabledBool = StringUtils.isNotEmpty(testCaseDisabled) && testCaseDisabled.equals(TRUE_VALUE);
        boolean failOnErrorBool = StringUtils.isNotEmpty(failOnError) && failOnError.equals(FALSE_VALUE);
        boolean failTestCaseOnErrorsBool = StringUtils.isNotEmpty(failTestCaseOnErrors) && failTestCaseOnErrors.equals(FALSE_VALUE);


        return testSuiteDisabledBool || testCaseDisabledBool;

    }
}
