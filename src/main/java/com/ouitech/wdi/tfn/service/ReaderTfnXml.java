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

public class ReaderTfnXml implements ReaderTfn<File> {

    @Override
    public List<Tfn> parsing(File file) {
        //Recupération des dossiers interface
        List<File> interfaceFolders = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(f -> MyProperties.getInterfacesToScan().contains(f.getName()))
                .collect(Collectors.toList());

        //Récupération des fichiers xml
        Map<String, List<File>> mapXmlFiles = getXmlFiles(interfaceFolders);

        //Construction et Mapping des Tfns
        List<Tfn> tfns = parsingXmlTfnFiles(mapXmlFiles);

        //Retour avec tri
        return tfns.stream()
                .sorted(getTfnComparator())
                .collect(Collectors.toList());

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

            Element project = (Element) doc.getElementsByTagName("con:soapui-project").item(0);
            String projectName = project.getAttribute("name");
            NodeList testSuite = doc.getElementsByTagName(getTestSuiteTagName());

            for (int i = 0; i < testSuite.getLength(); i++) {

                NodeList childNodes = testSuite.item(i).getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {

                    Node nNode = childNodes.item(j);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE &&
                            getTestCaseTagName().equals(nNode.getNodeName())){

                        Element testCase = (Element) nNode;

                        Element testSuiteElement = (Element) testSuite.item(i);

                        String testCaseDisabled = testCase.getAttribute("disabled");
                        String failOnError = testCase.getAttribute("failOnError");
                        String failTestCaseOnErrors = testCase.getAttribute("failTestCaseOnErrors");
                        String testSuiteDisabled = testSuiteElement.getAttribute("disabled");

                        boolean inactive = isInactiveTfn(testCaseDisabled, failOnError, failTestCaseOnErrors, testSuiteDisabled);

                        Tfn tfn = Tfn.builder()
                                .withFileName(xmlFile.getName())
                                .withProjectName(projectName)
                                .withInterfaces(folderInterface)
                                .withTestSuite(testSuiteElement.getAttribute(getAtributName()))
                                .withTestCase(testCase.getAttribute(getAtributName()))
                                .withInactiveStatus(inactive)
                                .build();

                        //Request
                        NodeList testCaseChildNodes = nNode.getChildNodes();

                        List<Request> requests = new ArrayList<>();
                        for (int k = 0; k < testCaseChildNodes.getLength(); k++) {


                            if (testCaseChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE &&
                                    "con:testStep".equals(testCaseChildNodes.item(k).getNodeName())){

                                Element testStep = (Element) testCaseChildNodes.item(k);

                                String type = testStep.getAttribute("type");

                                if (StringUtils.isNotEmpty(type) && type.equals("request")){

                                    String name = testStep.getAttribute("name");
                                    boolean three = false;
                                    if (name.length() > 2){
                                        three = StringUtils.isAllUpperCase(name.substring(0, 3));
                                    }
                                    name = three ? name.substring(0,3) : name.substring(0,2);

                                    NodeList configNodeList = testStep.getElementsByTagName("con:config");
                                    Request request = Request.builder().build();
                                    requests.add(request);

                                    for (int l = 0; l < configNodeList.getLength(); l++) {
                                        NodeList childNodes1 = configNodeList.item(l).getChildNodes();

                                        for (int m = 0; m < childNodes1.getLength(); m++) {

                                            Node item = childNodes1.item(m);

                                            if (item.getNodeType() == Node.ELEMENT_NODE &&
                                                    "con:operation".equals(item.getNodeName())){

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

    private boolean isInactiveTfn(String testCaseDisabled, String failOnError, String failTestCaseOnErrors, String testSuiteDisabled) {
        return StringUtils.isNotEmpty(testSuiteDisabled) && testCaseDisabled.equals("true")
                || StringUtils.isNotEmpty(testCaseDisabled) && testCaseDisabled.equals("true")
                || StringUtils.isNotEmpty(failOnError) && failOnError.equals("false")
                || StringUtils.isNotEmpty(failTestCaseOnErrors) && failTestCaseOnErrors.equals("false");

    }

    private Comparator<Tfn> getTfnComparator() {
        return Comparator.comparing(Tfn::getTfnInterface)
                .thenComparing(Tfn::getFileName)
                .thenComparing(Tfn::getTestSuite)
                .thenComparing(Tfn::getStatus);
    }

    private void fillResultsRequest(List<Tfn> tfns){
        File folderResult = new File("/home/alex/Bureau/surefire-reports");

        List<File> fileResults = Arrays.asList(Objects.requireNonNull(folderResult.listFiles()));

        tfns.forEach(tfn -> buildResult(tfn, fileResults));

    }

    private void buildResult(Tfn tfn, List<File> fileResults){

       List<File> xmlResultsFile = fileResults.stream()
               .filter(file -> file.getPath().endsWith(".xml"))
               .collect(Collectors.toList());

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
                    NodeList testcases = doc.getElementsByTagName("testcase");
                    for (int i = 0; i < testcases.getLength(); i++) {

                        Element elmntTestCase = (Element) testcases.item(i);
                        tfn.setTime(elmntTestCase.getAttribute("time"));

                        //Construction de la requête








                    }


                }



            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }


        }


    }



}
