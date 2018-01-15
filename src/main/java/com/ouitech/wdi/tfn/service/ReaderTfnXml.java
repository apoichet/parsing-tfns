package com.ouitech.wdi.tfn.service;

import com.ouitech.wdi.tfn.MyProperties;
import com.ouitech.wdi.tfn.domain.Tfn;
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
        Map<String, Tfn> tfnMap = parsingXmlTfnFiles(mapXmlFiles);

        //Retour avec tri
        return tfnMap.values().stream()
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

            NodeList testSuite = doc.getElementsByTagName(getTestSuiteTagName());

            for (int i = 0; i < testSuite.getLength(); i++) {

                NodeList childNodes = testSuite.item(i).getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {

                    Node nNode = childNodes.item(j);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE &&
                            getTestCaseTagName().equals(nNode.getNodeName())){

                        Element testCase = (Element) nNode;

                        String key = testCase.getAttribute(getAtributName());

                        if (tfnMap.containsKey(key)){
                            Tfn tfn = tfnMap.get(key);
                            tfn.addInterface(folderInterface);
                        }
                        else {
                            Element testSuiteElement = (Element) testSuite.item(i);

                            Tfn tfn = Tfn.builder()
                                    .withFileName(xmlFile.getName())
                                    .withFirstInterface(folderInterface)
                                    .withTestSuite(testSuiteElement.getAttribute(getAtributName()))
                                    .withTestCase(testCase.getAttribute(getAtributName()))
                                    .withActive(testCase.getAttribute(getAtributFailOnError()))
                                    .build();

                            tfnMap.put(key, tfn);
                        }
                    }
                }
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Comparator<Tfn> getTfnComparator() {
        return Comparator.comparing(Tfn::nbrInterface)
                .thenComparing(Tfn::getFileName)
                .thenComparing(Tfn::getTestSuite)
                .thenComparing((Function<Tfn, Boolean>) Tfn::isActive);
    }



}
