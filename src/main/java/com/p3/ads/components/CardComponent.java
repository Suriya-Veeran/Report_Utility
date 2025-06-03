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
import com.p3.ads.beans.CardBean;
import com.p3.ads.beans.MultipleCardBean;
import com.p3.ads.beans.SingleCardBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.utils.RoundedTableRenderer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.constants.FontSizeConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardComponent implements ReportComponent {

  private CardBean inputBean;

  @Override
  public void render(Document document) throws IOException {

    inputBean = mergeWithDefaults(inputBean);
    if (inputBean instanceof SingleCardBean) {
      renderSingleCardBean((SingleCardBean) inputBean, document);

    } else if (inputBean instanceof MultipleCardBean) {
      renderMultipleCardBean((MultipleCardBean) inputBean, document);
    }
  }

  private void renderSingleCardBean(SingleCardBean inputBean, Document document)
      throws IOException {

    addEmptyLines(2, document);

    Table headerTable = new Table(2);
    headerTable.setWidth(UnitValue.createPercentValue(100));
    headerTable.setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()));
    headerTable.setBorder(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
    headerTable.setBorderTopLeftRadius(new BorderRadius(4f));
    headerTable.setBorderTopRightRadius(new BorderRadius(4f));
    headerTable.addCell(
        new Cell()
            .add(
                new Paragraph(inputBean.getHeader())
                    .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                    .setFontColor(hexaDecimalToRGB(inputBean.getHeaderFontColor()))
                    .setFontSize(inputBean.getHeaderFontSize()))
            .setBorder(Border.NO_BORDER)
            .setPadding(PADDING_SEVEN_FONT_SIZE)
                .setPaddingLeft(11f)
            .setTextAlignment(TextAlignment.LEFT));

    headerTable.addCell(
        new Cell()
            .add(
                new Paragraph(inputBean.getGeneratedTime())
                    .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                    .setFontColor(hexaDecimalToRGB(inputBean.getHeaderFontColor()))
                    .setFontSize(inputBean.getHeaderFontSize()))
            .setBorder(Border.NO_BORDER)
            .setPadding(PADDING_SEVEN_FONT_SIZE)
                .setPaddingRight(10f)
            .setTextAlignment(TextAlignment.RIGHT));
    headerTable.setMarginLeft(MARGIN_LEFT);
    headerTable.setMarginRight(MARGIN_LEFT);
    document.add(headerTable);

    Table contentTable = new Table(1);
    contentTable.setWidth(UnitValue.createPercentValue(100));
    contentTable.setBorderLeft(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
    contentTable.setBorderRight(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
    contentTable.setBorderBottom(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));

    Cell contentCell =
        new Cell()
            .add(
                new Paragraph(inputBean.getValue())
                    .setFont(loadFont(inputBean.getValueFontFamily().getValue()))
                    .setFontSize(inputBean.getValueFontSize())
                        .setMultipliedLeading(1f)
                    .setFontColor(hexaDecimalToRGB(inputBean.getValueFontColor())))
            .setBorder(Border.NO_BORDER)
            .setPadding(PADDING_TEN_FONT_SIZE);
    contentTable.setMarginLeft(MARGIN_LEFT);
    contentTable.setMarginRight(MARGIN_LEFT);
    contentTable.addCell(contentCell);

    document.add(contentTable);
  }

  private void renderMultipleCardBean(MultipleCardBean inputBean, Document document)
      throws IOException {

    addEmptyLines(1, document);
    if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
      document.add(
              new Paragraph(inputBean.getTitle())
                      .setTextAlignment(TextAlignment.LEFT)
                      .setFontColor(hexaDecimalToRGB(inputBean.getTitleFontColor()))
                      .setFontSize(inputBean.getTitleFontSize())
                      .setMarginLeft(MARGIN_LEFT)
                      .setMarginTop(0)
                      .setMarginBottom(0)
                      .setPadding(0)
                      .setFont(loadFont(inputBean.getTitleFontFamily().getValue())));

      if (inputBean.getIsTitleDividerNeeded()) {
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
      }
    }

    addEmptyLines(1, document);
    float borderWidth = 1f;
    Color borderColor = hexaDecimalToRGB(DIVIDER_GRAY_COLOR);
    if (inputBean.getValues() != null && !inputBean.getValues().isEmpty()) {
      Table parameterTable = new Table(inputBean.getValueRowCount());
      parameterTable.setWidth(UnitValue.createPercentValue(100));
      parameterTable.setFixedLayout();
//      parameterTable.setBorderBottom(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
//      parameterTable.setBorderLeft(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
//      parameterTable.setBorderRight(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
      parameterTable.setMarginLeft(MARGIN_LEFT);
      parameterTable.setMarginRight(MARGIN_RIGHT);
      parameterTable.setKeepTogether(true);

//      parameterTable.setBorderTopLeftRadius(new BorderRadius(inputBean.getBorderRadius()));
//      parameterTable.setBorderTopRightRadius(new BorderRadius(inputBean.getBorderRadius()));
      Paragraph paragraph = new Paragraph();
      paragraph
          .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
          .setTextAlignment(TextAlignment.LEFT);
      paragraph.add(
              new Paragraph(inputBean.getHeaderValue())
                      .setFontSize(inputBean.getHeaderValueFontSize())
                      .setFontColor(hexaDecimalToRGB(inputBean.getHeaderValueFontColor()))
                      .setFont(loadFont(inputBean.getHeaderValueFontFamily().getValue()))
                      .setFixedLeading(15f));

      Cell paragraphCell =
          new Cell(1, 3)
              .add(paragraph)
                  .add(
                          new Paragraph(inputBean.getSubHeaderValue().trim())
                                  .setFontSize(inputBean.getSubHeaderValueFontSize())
                                  .setFont(loadFont(inputBean.getSubHeaderValueFontFamily().getValue()))
                                  .setFontColor(hexaDecimalToRGB(inputBean.getSubHeaderValueFontColor())))
//                                  .setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()))
                                   .setPaddingLeft(PADDING_SEVENTY_FONT_SIZE)
                                   .setBorderBottom(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH))
                                   .setBorder(Border.NO_BORDER)
              ;
      paragraphCell.setKeepTogether(true);
      paragraphCell.setPaddingBottom(5f);
      parameterTable.addCell(paragraphCell);
      int currentRow = 1;
      for (Map.Entry<String, String> entry : inputBean.getValues().entrySet()) {
        String header = entry.getKey();
        String value = entry.getValue();

        Color valueFinalColor =
            "Success".equalsIgnoreCase(value)
                ? hexaDecimalToRGB("007D2B") :
                    "Failure".equalsIgnoreCase(value) ?
                            hexaDecimalToRGB("#FF0000"):
                            hexaDecimalToRGB(inputBean.getValueFontColor());

        Cell cell =
            new Cell()
                .add(
                    new Paragraph(new Text(header))
                        .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                        .setFontColor(hexaDecimalToRGB(inputBean.getHeaderFontColor()))
                        .setPaddingLeft(currentRow !=1 ? -60f: PADDING_TWELVE_FONT_SIZE)
                        .setPaddingTop(5)
                        .setFontSize(inputBean.getHeaderFontSize()))
                .add(
                    new Paragraph(new Text(value))
                        .setFont(loadFont(inputBean.getValueFontFamily().getValue()))
                        .setFontColor(valueFinalColor)
                            .setMarginTop(-6f)
                            .setPaddingLeft(currentRow !=1 ? -60f : PADDING_TWELVE_FONT_SIZE)
                        .setFontSize(inputBean.getValueFontSize()))
                    .setPaddingBottom(5)
//                .setBackgroundColor(hexaDecimalToRGB(WHITE_FONT_COLOR))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.TOP);

        cell.setKeepTogether(true);
        parameterTable.addCell(cell);
//        parameterTable.setBorder(new SolidBorder(hexaDecimalToRGB(BACKGROUND_COLOR), BORDER_WIDTH));
       currentRow++;
      }

//
      parameterTable.setNextRenderer(
              new RoundedTableRenderer(
                      parameterTable,
                      inputBean.getBorderRadius(),
                      borderColor,
                      borderWidth,
                      hexaDecimalToRGB(WHITE_FONT_COLOR),
                      hexaDecimalToRGB(LIGHT_BLUE_CARD_COLOR), 30f));
      document.add(parameterTable);

      if (inputBean.getIsBottomDividerNeeded()) {
        addEmptyLines(3,document);
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);            }
      }
  }

  private CardBean mergeWithDefaults(CardBean inputBean) {
    if (inputBean == null) {
      throw new IllegalArgumentException(
          "inputBean must not be null. Please provide either a SingleCardBean or MultipleCardBean.");
    }

    if (inputBean instanceof SingleCardBean) {
      SingleCardBean singleCard = (SingleCardBean) inputBean;
      return buildSingleCardBean(singleCard);
    } else if (inputBean instanceof MultipleCardBean) {
      MultipleCardBean multipleCard = (MultipleCardBean) inputBean;

      return buildMultipleCardBean(multipleCard);
    }

    throw new IllegalArgumentException(
        "Invalid inputBean type. Must be either SingleCardBean or MultipleCardBean.");
  }

  private CardBean buildMultipleCardBean(MultipleCardBean multipleCard) {
    return MultipleCardBean.builder()
      .title(
          multipleCard.getTitle() != null
              ? multipleCard.getTitle()
              : MultipleCardBean.DEFAULT_CONFIG.getTitle())
          .titleFontColor(
                  multipleCard.getTitleFontColor() != null
                          ? multipleCard.getTitleFontColor()
                          : MultipleCardBean.DEFAULT_CONFIG.getTitleFontColor())
          .titleFontFamily(
                  multipleCard.getTitleFontFamily() != null
                          ? multipleCard.getTitleFontFamily()
                          : MultipleCardBean.DEFAULT_CONFIG.getTitleFontFamily())
          .titleFontSize(
                  multipleCard.getTitleFontSize() != 0f
                          ? multipleCard.getTitleFontSize()
                          : MultipleCardBean.DEFAULT_CONFIG.getTitleFontSize())
      .headerValue(
          multipleCard.getHeaderValue() != null
              ? multipleCard.getHeaderValue()
              : MultipleCardBean.DEFAULT_CONFIG.getHeaderValue())
          .headerValueFontColor(
                  multipleCard.getHeaderValueFontColor() != null
                          ? multipleCard.getHeaderValueFontColor()
                          : MultipleCardBean.DEFAULT_CONFIG.getHeaderValueFontColor())
          .headerValueFontSize(
                  multipleCard.getHeaderValueFontSize() != 0f
                          ? multipleCard.getHeaderValueFontSize()
                          : MultipleCardBean.DEFAULT_CONFIG.getHeaderValueFontSize())
          .headerValueFontFamily(
                  multipleCard.getHeaderValueFontFamily() != null
                          ? multipleCard.getHeaderValueFontFamily()
                          : MultipleCardBean.DEFAULT_CONFIG.getHeaderValueFontFamily())
      .subHeaderValue(
          multipleCard.getSubHeaderValue() != null
              ? multipleCard.getSubHeaderValue()
              : MultipleCardBean.DEFAULT_CONFIG.getSubHeaderValue())
          .subHeaderValueFontColor(
                  multipleCard.getSubHeaderValueFontColor() != null
                          ? multipleCard.getSubHeaderValueFontColor()
                          : MultipleCardBean.DEFAULT_CONFIG.getSubHeaderValueFontColor())
          .subHeaderValueFontFamily(
                  multipleCard.getSubHeaderValueFontFamily() != null
                          ? multipleCard.getSubHeaderValueFontFamily()
                          : MultipleCardBean.DEFAULT_CONFIG.getSubHeaderValueFontFamily())
          .subHeaderValueFontSize(
                  multipleCard.getSubHeaderValueFontSize() != 0
                          ? multipleCard.getSubHeaderValueFontSize()
                          : MultipleCardBean.DEFAULT_CONFIG.getSubHeaderValueFontSize())
        .values(
            multipleCard.getValues() != null
                ? multipleCard.getValues()
                : MultipleCardBean.DEFAULT_CONFIG.getValues())
        .headerFontColor(
            multipleCard.getHeaderFontColor() != null
                ? multipleCard.getHeaderFontColor()
                : MultipleCardBean.DEFAULT_CONFIG.getHeaderFontColor())
        .headerFontSize(
            multipleCard.getHeaderFontSize() != 0
                ? multipleCard.getHeaderFontSize()
                : MultipleCardBean.DEFAULT_CONFIG.getHeaderFontSize())
        .headerFontFamily(
            multipleCard.getHeaderFontFamily() != null
                ? multipleCard.getHeaderFontFamily()
                : MultipleCardBean.DEFAULT_CONFIG.getHeaderFontFamily())
        .valueFontFamily(
            multipleCard.getValueFontFamily() != null
                ? multipleCard.getValueFontFamily()
                : MultipleCardBean.DEFAULT_CONFIG.getValueFontFamily())
        .valueFontColor(
            multipleCard.getValueFontColor() != null
                ? multipleCard.getValueFontColor()
                : MultipleCardBean.DEFAULT_CONFIG.getValueFontColor())
        .valueFontSize(
            multipleCard.getValueFontSize() != 0
                ? multipleCard.getValueFontSize()
                : MultipleCardBean.DEFAULT_CONFIG.getValueFontSize())
        .cardBackgroundColor(
            multipleCard.getCardBackgroundColor() != null
                ? multipleCard.getCardBackgroundColor()
                : MultipleCardBean.DEFAULT_CONFIG.getCardBackgroundColor())
            .valueRowCount(
                    multipleCard.getValueRowCount() != null
                            ? multipleCard.getValueRowCount()
                            : MultipleCardBean.DEFAULT_CONFIG.getValueRowCount())
            .borderRadius(
            multipleCard.getBorderRadius() != 0
                    ? multipleCard.getBorderRadius()
                    : MultipleCardBean.DEFAULT_CONFIG.getBorderRadius())
            .isTitleDividerNeeded(
                    multipleCard.getIsTitleDividerNeeded() != null
                            ? multipleCard.getIsTitleDividerNeeded()
                            : MultipleCardBean.DEFAULT_CONFIG.getIsTitleDividerNeeded())
            .isBottomDividerNeeded(
                    multipleCard.getIsBottomDividerNeeded() != null
                            ? multipleCard.getIsBottomDividerNeeded()
                            : MultipleCardBean.DEFAULT_CONFIG.getIsBottomDividerNeeded())
        .build();
  }

  private CardBean buildSingleCardBean(SingleCardBean singleCard) {
    return SingleCardBean.builder()
        .header(
            singleCard.getHeader() != null
                ? singleCard.getHeader()
                : SingleCardBean.DEFAULT_CONFIG.getHeader())
        .generatedTime(
            singleCard.getGeneratedTime() != null
                ? singleCard.getGeneratedTime()
                : SingleCardBean.DEFAULT_CONFIG.getGeneratedTime())
        .value(
            singleCard.getValue() != null
                ? singleCard.getValue()
                : SingleCardBean.DEFAULT_CONFIG.getValue())
        .headerFontColor(
            singleCard.getHeaderFontColor() != null
                ? singleCard.getHeaderFontColor()
                : SingleCardBean.DEFAULT_CONFIG.getHeaderFontColor())
        .headerFontSize(
            singleCard.getHeaderFontSize() != 0
                ? singleCard.getHeaderFontSize()
                : SingleCardBean.DEFAULT_CONFIG.getHeaderFontSize())
        .headerFontFamily(
            singleCard.getHeaderFontFamily() != null
                ? singleCard.getHeaderFontFamily()
                : SingleCardBean.DEFAULT_CONFIG.getHeaderFontFamily())
        .valueFontFamily(
            singleCard.getValueFontFamily() != null
                ? singleCard.getValueFontFamily()
                : SingleCardBean.DEFAULT_CONFIG.getValueFontFamily())
        .valueFontColor(
            singleCard.getValueFontColor() != null
                ? singleCard.getValueFontColor()
                : SingleCardBean.DEFAULT_CONFIG.getValueFontColor())
        .valueFontSize(
            singleCard.getValueFontSize() != 0
                ? singleCard.getValueFontSize()
                : SingleCardBean.DEFAULT_CONFIG.getValueFontSize())
        .cardBackgroundColor(
            singleCard.getCardBackgroundColor() != null
                ? singleCard.getCardBackgroundColor()
                : SingleCardBean.DEFAULT_CONFIG.getCardBackgroundColor())
        .build();
  }
}
