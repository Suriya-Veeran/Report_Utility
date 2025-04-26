package report_utility.components;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.client.util.DeferredContentProvider;
import report_utility.beans.TableBean;
import report_utility.constants.ColorConstants;
import report_utility.core.Report;
import report_utility.core.interfaces.ReportComponent;
import report_utility.enums.FontFamilyType;
import report_utility.utils.RoundedTableRenderer;

import java.io.IOException;
import java.util.List;

import static report_utility.constants.ColorConstants.*;
import static report_utility.constants.CommonConstants.*;
import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableComponent implements ReportComponent {

  private TableBean inputBean;

  @Override
  public void render(Document document) throws IOException {

    inputBean = mergeWithDefaults(inputBean);

    addEmptyLines(1, document);

    float topMargin= Report.isChartAdded()?12f:0f;//This Line Added For Space Adjustment In SecondPage

    if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {

      document.add(
          new Paragraph(inputBean.getTitle())
              .setTextAlignment(TextAlignment.LEFT)
              .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
              .setFontSize(13)
              .setMarginLeft(MARGIN_LEFT)
              .setMarginTop(topMargin)
              .setMarginBottom(0)
              .setPadding(0)
              .setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue())));


      if(inputBean.getDrawDividerNeed().equalsIgnoreCase("true")) {
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
      }
    }

    if(inputBean.getEmptyLineNeed().equalsIgnoreCase("true") ) {
      addEmptyLines(2, document);
    }
   else{
      document.add(new LineSeparator(new SolidLine(0)).setMarginBottom(3).setOpacity(0f));
    }

    if (inputBean.getHeaders() != null && !inputBean.getHeaders().isEmpty()) {
      Table table = new Table(inputBean.getHeaders().size());
      table.setWidth(UnitValue.createPercentValue(100));
      table.setKeepTogether(false);
      table.setMarginLeft(MARGIN_LEFT);
      table.setMarginRight(MARGIN_RIGHT);
      table.setMarginTop(-2.2f);
      table.setPadding(0);
      float borderRadius = 2f;
      float borderWidth = 1f;
      Color borderColor = hexaDecimalToRGB(BACKGROUND_COLOR);

      for (String header : inputBean.getHeaders()) {

        Paragraph paragraph = new Paragraph();
        paragraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        paragraph.setMultipliedLeading(0.8f);//added for space between lines in header handling
        paragraph.setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue()));

        String[] parts = header.split(",", 2);
        paragraph
                .add(new Paragraph(parts[0]))
            .setFontSize(inputBean.getFontSize() - 2);

        if (parts.length > 1) {
          paragraph
              .add("\n")
              .add(
                  new Paragraph(parts[1].trim())
                      .setPaddingTop(-25f)
                      .setFontSize(inputBean.getFontSize() - 3));
        }

        Cell headerCell =
            new Cell()
                .add(paragraph)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(inputBean.getFontSize())
                    .setBorderTop(new SolidBorder(borderColor, 0.5f))
                .setBorderBottom(new SolidBorder(borderColor, borderWidth))
                .setBackgroundColor(hexaDecimalToRGB("E8EDF7"))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPaddingTop(3f)
                    .setPaddingBottom(6f)
                .setPaddingLeft(15f)
                ;
        table.setFixedLayout();
        table.addCell(headerCell);
      }

      int rowCount = inputBean.getValues().size();
      for (int i = 0; i < rowCount; i++) {
        List<String> rowValues = inputBean.getValues().get(i);
        int j=0;
        for (String originalValue : rowValues) {

          Color fontColor = retrieveCellFontColor(originalValue, inputBean);
          Paragraph paragraph = new Paragraph();
          String[] parts = originalValue.split(",", 3);
//          paragraph.add(
//              new Paragraph(parts[0])
//                      .setFixedLeading(1f)
//                      .setMultipliedLeading(1.2f)//this line added for purge,check with table optimization as well
//                      .setFontSize(inputBean.getFontSize() - 2)
//          );
//
//          if (parts.length > 1) {
//            paragraph
//                .add("\n")
//                .add(
//                    new Paragraph(parts[1].trim())
//                        .setPaddingTop(-10)
//                        .setFontSize(inputBean.getFontSize() - 5.5f));
//          }


          paragraph.add(
                  new Text(parts[0])
                          .setFontSize(inputBean.getFontSize() - 2)
          );

          float fontSize= (j==0)? (inputBean.getFontSize()-5.5f): (inputBean.getFontSize()-2f);
          j++;

          if (parts.length > 1) {
            paragraph
                    .add("\n")
                    .add(
                            new Text(parts[1].trim())
                                    .setFontSize(fontSize));
          }
         paragraph.setFixedLeading(1f);
          paragraph.setMultipliedLeading(1.5f);


          Cell cell =
              new Cell()
                  .add(paragraph)
                  .setFont(loadFont(FontFamilyType.HELVETICA.getValue()))
                  .setBorderTop(Border.NO_BORDER)
                  .setBorderLeft(Border.NO_BORDER)
                  .setBorderRight(Border.NO_BORDER)
                  .setFontColor(fontColor)
                      .setFontSize(inputBean.getFontSize())
                      .setPadding(10)
                  .setPaddingLeft(15f);

          if (i == rowCount - 1) {
            cell.setBorderBottom(Border.NO_BORDER);
          } else {
            cell.setBorderBottom(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
          }
          table.addCell(cell);

        }
      }
      table.setNextRenderer(
          new RoundedTableRenderer(
              table,
              borderRadius,
              borderColor,
              borderWidth,
              hexaDecimalToRGB(WHITE_FONT_COLOR),
              hexaDecimalToRGB(LIGHT_BLUE_FONT_COLOR)));
      document.add(table);
      addEmptyLines(1, document);

    }
  }

  private TableBean mergeWithDefaults(TableBean tableBean) {
    if (tableBean == null) {
      return TableBean.DEFAULT_CONFIG;
    }

    return TableBean.builder()
        .title(
            tableBean.getTitle() != null
                ? tableBean.getTitle()
                : TableBean.DEFAULT_CONFIG.getTitle())
        .fontSize(
            tableBean.getFontSize() != 0
                ? tableBean.getFontSize()
                : TableBean.DEFAULT_CONFIG.getFontSize())
        .fontFamily(
            tableBean.getFontFamily() != null
                ? tableBean.getFontFamily()
                : TableBean.DEFAULT_CONFIG.getFontFamily())
        .headers(
            tableBean.getHeaders() != null
                ? tableBean.getHeaders()
                : TableBean.DEFAULT_CONFIG.getHeaders())
        .values(
            tableBean.getValues() != null
                ? tableBean.getValues()
                : TableBean.DEFAULT_CONFIG.getValues())
        .headerBackgroundColor(
            tableBean.getHeaderBackgroundColor() != null
                ? tableBean.getHeaderBackgroundColor()
                : TableBean.DEFAULT_CONFIG.getHeaderBackgroundColor())
        .valueBackgroundColor(
            tableBean.getValueBackgroundColor() != null
                ? tableBean.getValueBackgroundColor()
                : TableBean.DEFAULT_CONFIG.getValueBackgroundColor())
        .headerValueFontColor(
            tableBean.getHeaderValueFontColor() != null
                ? tableBean.getHeaderValueFontColor()
                : TableBean.DEFAULT_CONFIG.getValueFontColor())
        .valueFontColor(
            tableBean.getValueFontColor() != null
                ? tableBean.getValueFontColor()
                : TableBean.DEFAULT_CONFIG.getValueFontColor())
        .successFontColor(
            tableBean.getSuccessFontColor() != null
                ? tableBean.getSuccessFontColor()
                : TableBean.DEFAULT_CONFIG.getSuccessFontColor())
        .errorFontColor(
            tableBean.getErrorFontColor() != null
                ? tableBean.getErrorFontColor()
                : TableBean.DEFAULT_CONFIG.getErrorFontColor())
            .drawDividerNeed(
                    tableBean.getDrawDividerNeed() !=null
                    ? tableBean.getDrawDividerNeed()
                    : TableBean.DEFAULT_CONFIG.getDrawDividerNeed())
            .emptyLineNeed(
                    tableBean.getEmptyLineNeed() !=null
                            ? tableBean.getEmptyLineNeed()
                            : TableBean.DEFAULT_CONFIG.getEmptyLineNeed())
        .build();
  }

  private static Color retrieveCellFontColor(String value, TableBean inputBean) {
    Color fontColor;
    if (value.equalsIgnoreCase("Disposed Success") || value.equalsIgnoreCase("Success")) {
      fontColor = hexaDecimalToRGB(inputBean.getSuccessFontColor());
    } else if (value.equalsIgnoreCase("Disposed with error") || value.equalsIgnoreCase("Failed")) {
      fontColor = hexaDecimalToRGB(inputBean.getErrorFontColor());
    } else {
      fontColor = hexaDecimalToRGB(BLACK_FONT_COLOR);
    }

    return fontColor;
  }
}
