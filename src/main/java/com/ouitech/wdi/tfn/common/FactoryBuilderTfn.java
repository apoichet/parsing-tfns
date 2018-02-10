package com.ouitech.wdi.tfn.common;

import com.ouitech.wdi.tfn.builder.xml.TfnXmlBuilderTfn;
import com.ouitech.wdi.tfn.builder.xml.TfnXmlConverter;
import com.ouitech.wdi.tfn.builder.xml.business.TfnXmlStatusManager;

public class FactoryBuilderTfn {


    public static final String XML_SUREFIRE_REPORT = "XML_SUREFIRE_REPORT";

    public static AbstractBuilderTfn create(final String builder){

        switch (builder){

            case XML_SUREFIRE_REPORT: return new TfnXmlBuilderTfn(new TfnXmlConverter(), new TfnXmlStatusManager());

        }

        throw new IllegalArgumentException("No existing Builder !");

    }

}
