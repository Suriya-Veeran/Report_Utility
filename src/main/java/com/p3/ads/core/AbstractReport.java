package com.p3.ads.core;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.p3.ads.core.utils.FileUtility;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class AbstractReport {

    protected PageSize pageSize = PageSize.A4;
    protected Document document;

    AbstractReport(String outputPath) throws IOException {
        initialize(outputPath);
    }

    AbstractReport(String outputPath, String filename) throws IOException {
        initialize(outputPath, filename);
    }

    private void initialize(String outputPath) throws IOException {
        File outputPdfFile = FileUtility.createOutputFile(outputPath);
        document =
                new Document(
                        new PdfDocument(new PdfWriter(outputPdfFile.getAbsolutePath())), pageSize, false);
    }

    private void initialize(String outputPath, String filename) throws IOException {
        File outputPdfFile = FileUtility.createOutputFile(outputPath, filename);
        document =
                new Document(
                        new PdfDocument(new PdfWriter(outputPdfFile.getAbsolutePath())), pageSize, false);

    }


}
