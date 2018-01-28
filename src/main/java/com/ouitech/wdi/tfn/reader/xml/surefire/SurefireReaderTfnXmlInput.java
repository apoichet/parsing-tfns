package com.ouitech.wdi.tfn.reader.xml.surefire;

import com.ouitech.wdi.tfn.MyProperties;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.Request;
import com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnInputXml;
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

import static com.ouitech.wdi.tfn.MyProperties.getFileTfnToScan;
import static java.util.stream.Collectors.toList;

public class SurefireReaderTfnXmlInput{

    private static final String PROJECT_TAG_NAME = "con:soapui-project";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String TEST_SUITE_TAG_NAME = "con:testSuite";
    private static final String DISABLED_ATTRIBUTE = "disabled";
    private static final String FAIL_ON_ERROR_ATTRIBUTE = "failOnError";
    private static final String FAIL_TEST_CASE_ON_ERRORS_TAG_NAME = "failTestCaseOnErrors";
    private static final String TEST_CASE_TAG_NAME = "con:testCase";
    private static final String TRUE_VALUE = "true";
    private static final String FALSE_VALUE = "false";
    private static final String TEST_STEP_TAG_NAME = "con:testStep";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String REQUEST_VALUE = "request";
    private static final String CONFIG_TAG_NAME = "con:config";
    private static final String OPERATION_TAG_NAME = "con:operation";

    public List<TfnInputXml> parsing() {

        File file = new File(MyProperties.getFolderInputParentName());

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

            if (getFileTfnToScan().contains(file.getName())) {
                xmlFiles.add(file);
            } else if (file.isDirectory()) {
                xmlFiles.addAll(findXmlFiles(file));
            }

        }

        return xmlFiles;
    }

    private List<TfnInputXml> parsingXmlTfnFiles(Map<String, List<File>> xmlTfnFiles) {
        List<TfnInputXml> tfns = new ArrayList<>();

        xmlTfnFiles.forEach((key, value) -> value
                .forEach(file -> tfns.addAll(parsingXmlTfnFile(key, file))));

        return tfns;
    }

    private List<TfnInputXml> parsingXmlTfnFile(String folderInterface, File xmlFile) {

        List<TfnInputXml> tfns = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element project = (Element) doc.getElementsByTagName(PROJECT_TAG_NAME).item(0);
            String projectName = project.getAttribute(NAME_ATTRIBUTE);
            NodeList testSuites = doc.getElementsByTagName(TEST_SUITE_TAG_NAME);

            buildTfnInput(folderInterface, xmlFile, tfns, projectName, testSuites);

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return tfns;
    }

    private void buildTfnInput(String folderInterface, File xmlFile, List<TfnInputXml> tfns, String projectName, NodeList testSuites) {
        for (int i = 0; i < testSuites.getLength(); i++) {

            NodeList testSuiteChildNodes = testSuites.item(i).getChildNodes();

            for (int j = 0; j < testSuiteChildNodes.getLength(); j++) {

                Node testSuiteNode = testSuiteChildNodes.item(j);

                if (testSuiteNode.getNodeType() == Node.ELEMENT_NODE &&
                        TEST_CASE_TAG_NAME.equals(testSuiteNode.getNodeName())) {

                    Element testCase = (Element) testSuiteNode;

                    Element testSuite = (Element) testSuites.item(i);

                    List<Request> requests = buildRequests(testSuiteNode);

                    TfnInputXml tfn = TfnInputXml.builder()
                            .withFileName(xmlFile.getName())
                            .withProjectName(projectName)
                            .withInterfaces(folderInterface)
                            .withTestSuite(testSuite.getAttribute(NAME_ATTRIBUTE))
                            .withTestCase(testCase.getAttribute(NAME_ATTRIBUTE))
                            .withInactive(isInactive(testCase, testSuite))
                            .withRequest(requests)
                            .build();

                    tfns.add(tfn);
                }
            }
        }
    }

    private List<Request> buildRequests(Node testCase) {

        //Request
        NodeList testCaseChildNodes = testCase.getChildNodes();
        List<Request> requests = new ArrayList<>();

        for (int i = 0; i < testCaseChildNodes.getLength(); i++) {


            if (testCaseChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE &&
                    TEST_STEP_TAG_NAME.equals(testCaseChildNodes.item(i).getNodeName())) {

                Element testStep = (Element) testCaseChildNodes.item(i);

                requests.add(buildRequest(testStep));

            }

        }
        return requests;
    }

    private Request buildRequest(Element testStep) {

        String type = testStep.getAttribute(TYPE_ATTRIBUTE);
        Request.Builder requestBuilder = Request.builder();

        if (StringUtils.isNotEmpty(type) && type.equals(REQUEST_VALUE)) {

            NodeList configNodeList = testStep.getElementsByTagName(CONFIG_TAG_NAME);

            for (int i = 0; i < configNodeList.getLength(); i++) {

                NodeList requestChildNodes = configNodeList.item(i).getChildNodes();

                for (int j = 0; j < requestChildNodes.getLength(); j++) {

                    Node requestNode = requestChildNodes.item(j);

                    if (requestNode.getNodeType() == Node.ELEMENT_NODE &&
                            OPERATION_TAG_NAME.equals(requestNode.getNodeName())) {

                        Element requestOperation = (Element) requestChildNodes.item(j);

                        requestBuilder.withService(testStep.getAttribute(NAME_ATTRIBUTE));
                        requestBuilder.withOperation(requestOperation.getTextContent());

                    }
                }
            }
        }

        return requestBuilder.build();
    }

    private boolean isInactive(Element testCase, Element testSuite) {
        String testCaseDisabled = testCase.getAttribute(DISABLED_ATTRIBUTE);
        String failOnError = testCase.getAttribute(FAIL_ON_ERROR_ATTRIBUTE);
        String failTestCaseOnErrors = testCase.getAttribute(FAIL_TEST_CASE_ON_ERRORS_TAG_NAME);
        String testSuiteDisabled = testSuite.getAttribute(DISABLED_ATTRIBUTE);

        return StringUtils.isNotEmpty(testSuiteDisabled) && testCaseDisabled.equals(TRUE_VALUE)
                || StringUtils.isNotEmpty(testCaseDisabled) && testCaseDisabled.equals(TRUE_VALUE)
                || StringUtils.isNotEmpty(failOnError) && failOnError.equals(FALSE_VALUE)
                || StringUtils.isNotEmpty(failTestCaseOnErrors) && failTestCaseOnErrors.equals(FALSE_VALUE);
    }

}
