package report_utility.components;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.HeaderBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.event_handler.HeaderEventHandler;

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
