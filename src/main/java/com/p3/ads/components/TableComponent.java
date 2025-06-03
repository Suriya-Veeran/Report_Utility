package com.p3.ads.components;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.p3.ads.beans.TableBean;
import com.p3.ads.core.Report;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.utils.RoundedTableRenderer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.*;

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
              .setPaddingBottom(5)
              .setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue())));


      if(inputBean.getDrawDividerNeed().equalsIgnoreCase("true")) {
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
      }
    }

    if(inputBean.getEmptyLineNeedAtFront().equalsIgnoreCase("true") ) {
      addEmptyLines(2, document);
    }


    if (inputBean.getHeaders() != null && !inputBean.getHeaders().isEmpty()) {
      Table table = new Table(inputBean.getHeaders().size());
      table.setWidth(UnitValue.createPercentValue(100));
      table.setKeepTogether(false);
      table.setMarginLeft(MARGIN_LEFT);
      table.setMarginRight(MARGIN_RIGHT);
      table.setPaddingTop(-20);
      float borderRadius = inputBean.getBorderRadius();
      float borderWidth = 1f;
      Color borderColor = hexaDecimalToRGB(BACKGROUND_COLOR);

      int currentHeader = 1;
      for (String header : inputBean.getHeaders()) {

        Paragraph paragraph = new Paragraph();
        paragraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        paragraph.setMultipliedLeading(0.8f);//added for space between lines in header handling
        paragraph.setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue()));

        String[] parts = header.split(",", 2);
        paragraph
                .add(new Paragraph(parts[0]))
            .setFontSize(inputBean.getHeaderFontSize());

        if (parts.length > 1) {
          paragraph
              .add("\n")
              .add(
                  new Paragraph(parts[1].trim())
                      .setPaddingTop(-25f)
                      .setFontSize(inputBean.getHeaderFontSize()));
        }

        Cell headerCell =
            new Cell()
                .add(paragraph)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontSize(inputBean.getHeaderFontSize())
                    .setBorderTop(new SolidBorder(borderColor, 0.5f))
                .setBorderBottom(new SolidBorder(borderColor, borderWidth))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setWidth(8f)
                    .setPaddingLeft(15f)
                ;
        if(currentHeader==1){
          headerCell.setBorderTopLeftRadius(new BorderRadius(borderRadius));
        }
        if(currentHeader == inputBean.getHeaders().size()){
          headerCell.setBorderTopRightRadius(new BorderRadius(borderRadius));
        }
        table.addHeaderCell(headerCell);
        currentHeader++;
      }

      int rowCount = inputBean.getValues().size();
      for (int i = 0; i < rowCount; i++) {
        List<String> rowValues = inputBean.getValues().get(i);
        int j=0;
        for (String originalValue : rowValues) {

          Color fontColor = retrieveCellFontColor(originalValue, inputBean);
          Paragraph paragraph = new Paragraph();
          String[] parts = originalValue.split(",", 3);

          String input=parts[0];

          int openParenIndex = input.indexOf('(');
          int closeParenIndex = input.indexOf(')', openParenIndex);

          if(inputBean.getHasPercentageColorHighlight() && input.contains("(") && input.contains(")") && input.contains("%")) {
            if (openParenIndex != -1 && closeParenIndex != -1) {
              String part1 = input.substring(0, openParenIndex).trim();                           // "24 TB"
              String part2 = input.substring(openParenIndex, openParenIndex + 1);                 // "("
              paragraph.add(
                      new Text(part1 + " " + part2)
                              .setFontSize(inputBean.getCellFontSize())
              );
              String part3 = input.substring(openParenIndex + 1, closeParenIndex).trim();         // "80%"
              paragraph.add(
                      new Text(part3)
                              .setFontSize(inputBean.getCellFontSize())
                              .setFontColor(hexaDecimalToRGB(inputBean.getPercentageColorHighlight()))
              );
              String part4 = input.substring(closeParenIndex, closeParenIndex + 1);
              paragraph.add(
                      new Text(part4)
                              .setFontSize(inputBean.getCellFontSize())
              );
            }
          }
          else {
            paragraph.add(
                    new Text(parts[0])
                            .setFontSize(inputBean.getCellFontSize())
            );
          }
          float fontSize= (j==0)? (inputBean.getCellFontSize()-5.5f): (inputBean.getCellFontSize()-2f);
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
                  .setFont(loadFont(inputBean.getCellFontFamily().getValue()))
                  .setBorderTop(Border.NO_BORDER)
                  .setBorderLeft(Border.NO_BORDER)
                  .setBorderRight(Border.NO_BORDER)
                  .setFontColor(fontColor)
                      .setFontSize(inputBean.getCellFontSize())
                      .setWidth(8f)
                      .setPaddingLeft(15f);
          if (i == rowCount - 1) {
            cell.setBorderBottom(Border.NO_BORDER);
          } else {
            cell.setBorderBottom(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
          }
          table.addCell(cell);

        }
      }
      table.setAutoLayout();
      table.setNextRenderer(
          new RoundedTableRenderer(
              table,
              borderRadius,
              borderColor,
              borderWidth,
              hexaDecimalToRGB(WHITE_FONT_COLOR),
              hexaDecimalToRGB(LIGHT_BLUE_CARD_COLOR), 26f));
      document.add(table);
      if(inputBean.getDrawDividerNeedAtBottom().equalsIgnoreCase("true")) {
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(new Paragraph());
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
      }

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
            .borderRadius(
            tableBean.getBorderRadius() != 0f
                    ? tableBean.getBorderRadius()
                    : TableBean.DEFAULT_CONFIG.getBorderRadius())
        .headerFontSize(
            tableBean.getHeaderFontSize() != 0
                ? tableBean.getHeaderFontSize()
                : TableBean.DEFAULT_CONFIG.getHeaderFontSize())
        .cellFontSize(
            tableBean.getCellFontSize() != 0
                ? tableBean.getCellFontSize()
                : TableBean.DEFAULT_CONFIG.getCellFontSize())
        .headerFontFamily(
                tableBean.getHeaderFontFamily() != null
                        ? tableBean.getHeaderFontFamily()
                        : TableBean.DEFAULT_CONFIG.getHeaderFontFamily())
        .cellFontFamily(
                tableBean.getCellFontFamily() != null
                        ? tableBean.getCellFontFamily()
                        : TableBean.DEFAULT_CONFIG.getCellFontFamily())
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
            .drawDividerNeedAtBottom(
                    tableBean.getDrawDividerNeedAtBottom() !=null
                            ? tableBean.getDrawDividerNeedAtBottom()
                            : TableBean.DEFAULT_CONFIG.getDrawDividerNeedAtBottom())
            .emptyLineNeedAtFront(
                    tableBean.getEmptyLineNeedAtFront() !=null
                            ? tableBean.getEmptyLineNeedAtFront()
                            : TableBean.DEFAULT_CONFIG.getEmptyLineNeedAtFront())
            .hasPercentageColorHighlight(
                    tableBean.getHasPercentageColorHighlight() !=null
                            ? tableBean.getHasPercentageColorHighlight()
                            : TableBean.DEFAULT_CONFIG.getHasPercentageColorHighlight()
            )
            .percentageColorHighlight(
                    tableBean.getPercentageColorHighlight() !=null
                            ? tableBean.getPercentageColorHighlight()
                            : TableBean.DEFAULT_CONFIG.getPercentageColorHighlight()
            )
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
