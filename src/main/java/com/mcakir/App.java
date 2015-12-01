package com.mcakir;

import com.mcakir.docx.MergeDocs;
import com.mcakir.docx.ReplaceVariables;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

    static final String resourcesPath = System.getProperty("user.dir") + File.separator + "src//main//resources//";
    static final String docxPath = resourcesPath + "docx" + File.separator;

    public static void main(String[] args){

        replaceTest();

    }

    public static void mergeTest(){

        List<String> docs = new ArrayList<>();
        docs.add(docxPath + "exmp1.docx");
        docs.add(docxPath + "exmp2.docx");

        String outputDoc = resourcesPath + "output//merged-doc.docx";

        System.out.println("Merging is started.");

        MergeDocs.merge(docs, outputDoc);

        System.out.println("Merging is finished.");
    }

    public static void replaceTest(){

        String templateDoc = docxPath + "template.docx";
        String outputDoc = resourcesPath + "output//replaced-doc.docx";

        HashMap<String, String> maps = new HashMap<>();
        maps.put("name", "Mary");
        maps.put("how", "How about you?");
        maps.put("me", "Me too.");

        System.out.println("Replacing is started.");

        ReplaceVariables.replace(templateDoc, outputDoc, maps);

        System.out.println("Replacing is finished.");
    }

}
