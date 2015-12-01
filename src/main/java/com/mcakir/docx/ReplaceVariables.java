package com.mcakir.docx;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.util.HashMap;

public class ReplaceVariables {

    public static void replace(String docx, String outputDocx, HashMap<String, String> variableMappings) {

        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(docx));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            documentPart.variableReplace(variableMappings);

            wordMLPackage.save(new File(outputDocx));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
