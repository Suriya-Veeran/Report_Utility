package report_utility.components;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.drawDivider;
import static report_utility.utils.CommonUtils.loadFont;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.FooterBean;
import report_utility.core.interfaces.ReportComponent;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FooterComponent implements ReportComponent {

  private FooterBean inputBean;

  @Override
  public void render(Document document) throws IOException {

    inputBean = mergeWithDefault(inputBean);
    int numberOfPages = document.getPdfDocument().getNumberOfPages();
    for (int i = 1; i <= numberOfPages; i++) {
      applyFooterToPage(document, i, numberOfPages);
    }
    document.flush();
  }

  private void applyFooterToPage(Document document, int pageIndex, int numberOfPages)
      throws IOException {

    drawDivider(document, 1L, "#E2E2E2", "Footer", pageIndex, 20f + 10);

    Rectangle pageSize = document.getPdfDocument().getPage(pageIndex).getPageSize();
    float width = pageSize.getWidth();

    Rectangle rectangle = new Rectangle(0, 0);

    Paragraph footerText = createFooterParagraph(inputBean, rectangle);
    document.showTextAligned(
        footerText, 18, 10, pageIndex, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);

    String pageText = "Page " + pageIndex + " of " + numberOfPages;
    Paragraph pageNumberParagraph =
        new Paragraph(pageText)
            .setFontSize(inputBean.getFontSize())
            .setFont(loadFont(inputBean.getFontFamily().getValue()))
            .setFontColor(hexaDecimalToRGB(inputBean.getFontColor()))
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.BOTTOM);
    document.showTextAligned(
        pageNumberParagraph,
        width - 60,
        10f,
        pageIndex,
        TextAlignment.LEFT,
        VerticalAlignment.BOTTOM,
        0);
  }

  private Paragraph createFooterParagraph(FooterBean footerInputBean, Rectangle rectangle)
      throws IOException {

    PdfLinkAnnotation annotation = new PdfLinkAnnotation(rectangle);
    annotation.setBorder(new PdfArray(new int[] {0, 0, 0}));

    PdfAction action = PdfAction.createURI("https://platform3solutions.com/");
    annotation.setAction(action);

    Link link = new Link("Platform 3 Solutions .", annotation);
    link.setBorder(Border.NO_BORDER);

    return new Paragraph()
        .setWidth(UnitValue.createPercentValue(100))
        .setTextAlignment(TextAlignment.LEFT)
        .setVerticalAlignment(VerticalAlignment.BOTTOM)
        .setFontSize(footerInputBean.getFontSize())
        .setBorder(Border.NO_BORDER)
        .setFont(loadFont(inputBean.getFontFamily().getValue()))
        .setFontColor(hexaDecimalToRGB(footerInputBean.getFontColor()))
        .add("Copyright © 2024. ")
        .add(link)
        .add(" All rights reserved.");
  }

  private FooterBean mergeWithDefault(FooterBean footerBean) {
    if (footerBean == null) {
      return FooterBean.DEFAULT_CONFIG;
    }

    return FooterBean.builder()
        .fontSize(
            inputBean.getFontSize() != 0
                ? inputBean.getFontSize()
                : FooterBean.DEFAULT_CONFIG.getFontSize())
        .fontColor(
            inputBean.getFontColor() != null
                ? inputBean.getFontColor()
                : FooterBean.DEFAULT_CONFIG.getFontColor())
        .fontFamily(
            inputBean.getFontFamily() != null
                ? inputBean.getFontFamily()
                : footerBean.getFontFamily())
        .build();
  }
}
