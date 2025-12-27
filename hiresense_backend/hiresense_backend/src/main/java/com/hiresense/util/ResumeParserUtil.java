package com.hiresense.util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;

public class ResumeParserUtil {

    public static String extractText(File file) {
        try {
            String fileName = file.getName().toLowerCase();

            if (fileName.endsWith(".pdf")) {
                PDDocument document = PDDocument.load(file);
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                document.close();
                return text;
            }

            if (fileName.endsWith(".docx")) {
                FileInputStream fis = new FileInputStream(file);
                XWPFDocument doc = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                String text = extractor.getText();
                extractor.close();
                return text;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
