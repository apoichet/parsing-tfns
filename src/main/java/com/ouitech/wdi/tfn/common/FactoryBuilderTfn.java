package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.builder.xml.TfnXmlBuilder;

public class FactoryBuilderTfn {


    public static final String XML_SUREFIRE_REPORT = "XML_SUREFIRE_REPORT";

    public static AbstractTfnResultBuilder create(final String builder){

        switch (builder){

            case XML_SUREFIRE_REPORT: return new TfnXmlBuilder();

        }

        throw new IllegalArgumentException("Il n'existe pas de Builder Tfn correspondant !");

    }

}
