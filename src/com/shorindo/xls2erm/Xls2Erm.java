package com.shorindo.xls2erm;

import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

public class Xls2Erm {
    public static void main(String args[]) {
        Diagram diagram = new Diagram();
        Writer writer = new OutputStreamWriter(System.out);
        JAXB.marshal(diagram, writer);
    }
}
