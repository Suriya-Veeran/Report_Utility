package com.p3.ads.core.interfaces;

import com.itextpdf.layout.Document;

import java.io.IOException;

public interface ReportComponent {
    void render(Document document) throws IOException;
}
