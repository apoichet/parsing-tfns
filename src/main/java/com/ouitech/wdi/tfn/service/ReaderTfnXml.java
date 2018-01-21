package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.MyProperties;
import com.ouitech.wdi.tfn.domain.Request;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ouitech.wdi.tfn.MyProperties.*;
import static com.ouitech.wdi.tfn.domain.Tfn.compare;
import static java.util.stream.Collectors.toList;

public class ReaderTfnXml implements ReaderTfn<File> {

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
    private static final String TIME_ATTRIBUTE = "time";

    @Override
    public List<Tfn> parsing(File file) {
        //Recupération des dossiers interface
        List<File> interfaceFolders = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> MyProperties.getInterfacesToScan().contains(f.getName()))
                .collect(toList());

        //Récupération des fichiers xml
        Map<String, List<File>> mapXmlFiles = getXmlFiles(interfaceFolders);

        //Construction et Mapping des Tfns
        List<Tfn> tfns = parsingXmlTfnFiles(mapXmlFiles);

        //Retour avec tri
        return tfns.stream()
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

    private List<Tfn> parsingXmlTfnFiles(Map<String, List<File>> xmlTfnFiles){
        List<Tfn> tfns = new ArrayList<>();

        xmlTfnFiles.forEach((key, value) -> value
                .forEach(file -> tfns.addAll(parsingXmlTfnFile(key, file))));

        return tfns;
    }

    private List<Tfn> parsingXmlTfnFile(String folderInterface, File xmlFile) {

        List<Tfn> tfns = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Element project = (Element) doc.getElementsByTagName(PROJECT_TAG_NAME).item(0);
            String projectName = project.getAttribute(NAME_ATTRIBUTE);
            NodeList testSuites = doc.getElementsByTagName(TEST_SUITE_TAG_NAME);

            for (int i = 0; i < testSuites.getLength(); i++) {

                NodeList testSuiteChildNodes = testSuites.item(i).getChildNodes();

                for (int j = 0; j < testSuiteChildNodes.getLength(); j++) {

                    Node testSuiteNode = testSuiteChildNodes.item(j);

                    if (testSuiteNode.getNodeType() == Node.ELEMENT_NODE &&
                            TEST_CASE_TAG_NAME.equals(testSuiteNode.getNodeName())){

                        Element testCase = (Element) testSuiteNode;

                        Element testSuite = (Element) testSuites.item(i);

                        Tfn tfn = Tfn.builder()
                                .withFileName(xmlFile.getName())
                                .withProjectName(projectName)
                                .withInterfaces(folderInterface)
                                .withTestSuite(testSuite.getAttribute(NAME_ATTRIBUTE))
                                .withTestCase(testCase.getAttribute(NAME_ATTRIBUTE))
                                .withInactiveStatus( isInactive(testCase, testSuite))
                                .build();

                        //Request
                        NodeList testCaseChildNodes = testSuiteNode.getChildNodes();

                        List<Request> requests = new ArrayList<>();
                        for (int k = 0; k < testCaseChildNodes.getLength(); k++) {


                            if (testCaseChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE &&
                                    TEST_STEP_TAG_NAME.equals(testCaseChildNodes.item(k).getNodeName())){

                                Element testStep = (Element) testCaseChildNodes.item(k);

                                String type = testStep.getAttribute(TYPE_ATTRIBUTE);

                                if (StringUtils.isNotEmpty(type) && type.equals(REQUEST_VALUE)){

                                    String name = testStep.getAttribute(NAME_ATTRIBUTE);
                                    boolean three = false;
                                    if (name.length() > 2){
                                        three = StringUtils.isAllUpperCase(name.substring(0, 3));
                                    }
                                    name = three ? name.substring(0,3) : name.substring(0,2);

                                    NodeList configNodeList = testStep.getElementsByTagName(CONFIG_TAG_NAME);
                                    Request request = Request.builder().build();
                                    requests.add(request);

                                    for (int l = 0; l < configNodeList.getLength(); l++) {
                                        NodeList childNodes1 = configNodeList.item(l).getChildNodes();

                                        for (int m = 0; m < childNodes1.getLength(); m++) {

                                            Node item = childNodes1.item(m);

                                            if (item.getNodeType() == Node.ELEMENT_NODE &&
                                                    OPERATION_TAG_NAME.equals(item.getNodeName())){

                                                    Element operation = (Element) childNodes1.item(m);
                                                    request.setName(name+"."+operation.getTextContent());


                                            }

                                        }


                                    }



                                }


                            }

                        }


                        tfn.setRequests(requests);
                        tfns.add(tfn);
                    }
                }
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return tfns;
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

    private void fillResultsRequest(List<Tfn> tfns){
        File folderResult = new File("/home/alex/Bureau/surefire-reports");

        List<File> fileResults = Arrays.asList(Objects.requireNonNull(folderResult.listFiles()));

        tfns.forEach(tfn -> buildResult(tfn, fileResults));

    }

    private void buildResult(Tfn tfn, List<File> fileResults){

       List<File> xmlResultsFile = fileResults.stream()
               .filter(file -> file.getPath().endsWith(".xml"))
               .collect(toList());

        for (File xmlResultFile : xmlResultsFile) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlResultFile);
                doc.getDocumentElement().normalize();

                boolean tfnFind = doc.getTextContent().contains(tfn.getProjectName())
                        && doc.getTextContent().contains(tfn.getTestSuite())
                        && doc.getTextContent().contains(tfn.getTfnInterface());

                if (tfnFind){
                    NodeList testcases = doc.getElementsByTagName(TEST_CASE_TAG_NAME);
                    for (int i = 0; i < testcases.getLength(); i++) {

                        Element elmntTestCase = (Element) testcases.item(i);
                        tfn.setTime(elmntTestCase.getAttribute(TIME_ATTRIBUTE));

                        //Construction de la requête








                    }


                }



            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }


        }


    }



}
