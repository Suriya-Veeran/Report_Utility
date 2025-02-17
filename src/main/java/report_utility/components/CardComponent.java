package report_utility.components;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.*;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.IOException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.CardBean;
import report_utility.beans.MultipleCardBean;
import report_utility.beans.SingleCardBean;
import report_utility.core.interfaces.ReportComponent;

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

    addEmptyLines(1, document);

    Table headerTable = new Table(2);
    headerTable.setWidth(UnitValue.createPercentValue(100));
    headerTable.setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()));
    headerTable.setBorderBottom(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
    headerTable.addCell(
        new Cell()
            .add(
                new Paragraph(inputBean.getHeader())
                    .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                    .setFontColor(hexaDecimalToRGB(inputBean.getHeaderFontColor()))
                    .setFontSize(inputBean.getHeaderFontSize()))
            .setBorder(Border.NO_BORDER)
            .setPadding(10)
            .setTextAlignment(TextAlignment.LEFT));

    headerTable.addCell(
        new Cell()
            .add(
                new Paragraph(inputBean.getGeneratedTime())
                    .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                    .setFontColor(hexaDecimalToRGB(inputBean.getHeaderFontColor()))
                    .setFontSize(inputBean.getHeaderFontSize()))
            .setBorder(Border.NO_BORDER)
            .setPadding(10)
            .setTextAlignment(TextAlignment.RIGHT));
    headerTable.setMarginLeft(-18f);
    headerTable.setMarginRight(-18f);
    document.add(headerTable);

    Table contentTable = new Table(1);
    contentTable.setWidth(UnitValue.createPercentValue(100));
    contentTable.setBorderBottom(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
    contentTable.setBorderLeft(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
    contentTable.setBorderRight(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));

    Cell contentCell =
        new Cell()
            .add(
                new Paragraph(inputBean.getValue())
                    .setFont(loadFont(inputBean.getValueFontFamily().getValue()))
                    .setFontSize(inputBean.getValueFontSize())
                    .setFontColor(hexaDecimalToRGB(inputBean.getValueFontColor())))
            .setBorder(Border.NO_BORDER)
            .setPadding(10);
    contentTable.setMarginLeft(-18f);
    contentTable.setMarginRight(-18f);
    contentTable.addCell(contentCell);
    document.add(contentTable);
  }

  private void renderMultipleCardBean(MultipleCardBean inputBean, Document document)
      throws IOException {

    if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
      document.add(
          new Paragraph(inputBean.getTitle())
              .setTextAlignment(TextAlignment.LEFT)
              .setFontColor(hexaDecimalToRGB("030303"))
              .setFontSize(13)
              .setMarginLeft(-17)
              .setMarginTop(0)
              .setMarginBottom(0)
              .setPadding(0)
              .setFont(loadFont(inputBean.getValueFontFamily().getValue())));
      drawDivider(document, -18, -18, 1L, "#B8B8B8");
    }

    addEmptyLines(1, document);
    if (inputBean.getValues() != null && !inputBean.getValues().isEmpty()) {
      Table parameterTable = new Table(3);
      parameterTable.setWidth(UnitValue.createPercentValue(100));
      parameterTable.setFixedLayout();
      parameterTable.setBorderBottom(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
      parameterTable.setBorderLeft(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
      parameterTable.setBorderRight(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
      parameterTable.setMarginLeft(-18f);
      parameterTable.setMarginRight(-18f);
      parameterTable.setKeepTogether(true);

      Paragraph paragraph = new Paragraph();
      paragraph
          .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
          .setTextAlignment(TextAlignment.LEFT);
      paragraph.add(new Paragraph(inputBean.getHeaderValue()).setFontSize(10));
      paragraph.add("\n").add(new Paragraph(inputBean.getSubHeaderValue().trim()).setFontSize(8));

      Cell paragraphCell =
          new Cell(1, 3)
              .add(paragraph)
              .setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()))
              .setBorder(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1));
      paragraphCell.setKeepTogether(true);
      parameterTable.addCell(paragraphCell);
      for (Map.Entry<String, String> entry : inputBean.getValues().entrySet()) {
        String header = entry.getKey();
        String value = entry.getValue();

        Color valueFinalColor =
            "Success".equalsIgnoreCase(value)
                ? hexaDecimalToRGB("007D2B")
                : hexaDecimalToRGB(inputBean.getValueFontColor());

        Cell cell =
            new Cell()
                .add(
                    new Paragraph(new Text(header))
                        .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                        .setFontColor(valueFinalColor)
                        .setFontSize(inputBean.getHeaderFontSize()))
                .add(
                    new Paragraph(new Text(value))
                        .setFont(loadFont(inputBean.getValueFontFamily().getValue()))
                        .setFontColor(valueFinalColor)
                        .setFontSize(inputBean.getValueFontSize()))
                .setBackgroundColor(hexaDecimalToRGB("FFFFFF"))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.TOP);

        cell.setKeepTogether(true);
        parameterTable.addCell(cell);
        parameterTable.setBorder(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1f));
      }
      document.add(parameterTable);
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
        .headerValue(
            multipleCard.getHeaderValue() != null
                ? multipleCard.getHeaderValue()
                : MultipleCardBean.DEFAULT_CONFIG.getHeaderValue())
        .subHeaderValue(
            multipleCard.getSubHeaderValue() != null
                ? multipleCard.getSubHeaderValue()
                : MultipleCardBean.DEFAULT_CONFIG.getSubHeaderValue())
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
