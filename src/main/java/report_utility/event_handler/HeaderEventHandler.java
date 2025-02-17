package report_utility.event_handler;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import report_utility.beans.HeaderBean;

import java.io.IOException;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.drawDivider;
import static report_utility.utils.CommonUtils.loadFont;
import static report_utility.utils.ImageUtils.loadImage;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class HeaderEventHandler implements IEventHandler {

    private HeaderBean headerBean;
    private Document document;

    @Override
    public void handleEvent(Event event) {

        try {
            headerBean = mergeWithDefaults(headerBean);
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            float width = pageSize.getWidth();

            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);

            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setFixedPosition(0, pageSize.getHeight() - 45, width)
                    .setBackgroundColor(hexaDecimalToRGB("F9F9F9"));

            PdfFont font = loadFont(headerBean.getFontFamily().getValue());

            Paragraph title = new Paragraph(headerBean.getTitle())
                    .setFontSize(headerBean.getFontSize())
                    .setFont(font)
                    .setFontColor(hexaDecimalToRGB(headerBean.getFontColor()))
                    .setTextAlignment(TextAlignment.LEFT)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMultipliedLeading(1.2f)
                    .setKeepTogether(false);

            Cell titleCell = new Cell().add(title)
                    .setBorder(null)
                    .setPaddingTop(13)
                    .setPaddingBottom(5)
                    .setPaddingLeft(17)
                    .setBackgroundColor(hexaDecimalToRGB("F9F9F9"))
                    .setKeepTogether(false)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(titleCell);


            Image logo = loadImage(headerBean.getProductLogo().getFilePath(),
                    52,
                    25);

            if (logo != null) {
                logo.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                Cell logoCell = new Cell()
                        .add(logo)
                        .setPaddingRight(17f)
                        .setPaddingTop(15f)
                        .setPaddingBottom(3f)
                        .setBackgroundColor(hexaDecimalToRGB("F9F9F9"))
                        .setBorder(null);
                headerTable.addCell(logoCell);
            } else {
                headerTable.addCell(new Cell().setBorder(null));
            }
            canvas.add(headerTable);
            canvas.close();
            drawDivider(document, 1L, "E9E9E9", "Header");
        } catch (IOException e) {
            log.error("Error in HeaderEventHandler: {}", e.getMessage(), e);
        }

    }

    public HeaderBean mergeWithDefaults(HeaderBean bean) {
        if (bean == null) {
            return HeaderBean.DEFAULT_CONFIG;
        }
        return HeaderBean.builder()
                .title(bean.getTitle() != null ? bean.getTitle() : HeaderBean.DEFAULT_CONFIG.getTitle())
                .fontColor(bean.getFontColor() != null ? bean.getFontColor() : HeaderBean.DEFAULT_CONFIG.getFontColor())
                .fontSize(bean.getFontSize() > 0 ? bean.getFontSize() : HeaderBean.DEFAULT_CONFIG.getFontSize())
                .fontFamily(bean.getFontFamily() != null ? bean.getFontFamily() : HeaderBean.DEFAULT_CONFIG.getFontFamily())
                .productLogo(bean.getProductLogo() != null ? bean.getProductLogo() : HeaderBean.DEFAULT_CONFIG.getProductLogo())
                .build();
    }
}
