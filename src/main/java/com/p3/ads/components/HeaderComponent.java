package com.p3.ads.components;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.p3.ads.beans.HeaderBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.event_handler.HeaderEventHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderComponent implements ReportComponent {

    private HeaderBean inputBean;

    @Override
    public void render(Document document) throws IOException {
        PdfDocument pdfDocument = document.getPdfDocument();
        pdfDocument.addEventHandler(
                PdfDocumentEvent.START_PAGE, new HeaderEventHandler(inputBean, document));
    }
}
