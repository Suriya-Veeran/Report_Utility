package report_utility.components;


import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.TableBeanForPurge;
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
public class TableComponentForPurge implements ReportComponent {

    private TableBeanForPurge inputBean;

    @Override
    public void render(Document document) throws IOException {

        inputBean = mergeWithDefaults(inputBean);

        addEmptyLines(1, document);
        if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
            document.add(
                    new Paragraph(inputBean.getTitle())
                            .setTextAlignment(TextAlignment.LEFT)
                            .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
                            .setFontSize(13)
                            .setMarginLeft(MARGIN_LEFT)
                            .setMarginTop(0)
                            .setMarginBottom(0)
                            .setPadding(0)
                            .setFont(loadFont(FontFamilyType.ROBOTO_BOLD.getValue())));
            drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
        }

        addEmptyLines(2, document);
        if (inputBean.getHeaders() != null && !inputBean.getHeaders().isEmpty()) {
             float[]columnWidths={1.2f,1.2f,1f,1f,1f,0.8f,1.2f};
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));
            table.setKeepTogether(false);
            table.setMarginLeft(MARGIN_LEFT);
            table.setMarginRight(MARGIN_RIGHT);
            table.setMarginTop(-2.2f);
            table.setPadding(0  );

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
//            .add(new Paragraph(parts[0]).setFixedLeading(15f))
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
                                .setPaddingLeft(10f)
                        ;
                table.setFixedLayout();
                table.addCell(headerCell);
            }

            int rowCount = inputBean.getValues().size();
            for (int i = 0; i < rowCount; i++) {
                List<String> rowValues = inputBean.getValues().get(i);
                for (String originalValue : rowValues) {
                    Color fontColor = retrieveCellFontColor(originalValue, inputBean);
                    Paragraph paragraph = new Paragraph();
//                    String[] parts = originalValue.split(",", 8);
//                    paragraph.add(
//                            new Paragraph(parts[0])
//                                    .setFixedLeading(15f)
//                                    .setMultipliedLeading(1.2f)//this line added for purge,check with table optimization as well
//                                    .setFontSize(inputBean.getFontSize() - 2)
//                    );
//
//                    if (parts.length > 1) {
//                        paragraph
//                                .add("\n")
//                                .add(
//                                        new Paragraph(parts[1].trim())
//                                                .setPaddingTop(-10)
//                                                .setFontSize(inputBean.getFontSize() - 2));//before that it has 1
//                    }
//                    if (parts.length>2 && parts.length<4) {
//                        paragraph
//                                .add("\n")
//                                .add(
//                                        new Paragraph(parts[2].trim())
//                                                .setPaddingTop(-8)
//                                                .setFontSize(inputBean.getFontSize() - 2));//before that it has 1
//                    }
                    paragraph.add(
                            new Paragraph(originalValue)
                                    .setFixedLeading(15f)
                                    .setFontSize(inputBean.getFontSize()-2)
                                    .setMultipliedLeading(1.2f)
                    );


                    Cell cell =
                            new Cell()
                                    .add(paragraph)
                                    .setFont(loadFont(FontFamilyType.HELVETICA.getValue()))
                                    .setBorderTop(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER)
                                    .setFontColor(fontColor)
                                    .setFontSize(inputBean.getFontSize())
//                                    .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.BLUE)
                                    .setPadding(0)
                                    .setMargin(0)
                                    .setPaddingRight(0)
                                    .setPaddingTop(7)
                                    .setPaddingBottom(4)
                                    .setMarginRight(8)
                                    .setPaddingLeft(10f);

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

    private TableBeanForPurge mergeWithDefaults(TableBeanForPurge tableBean) {
        if (tableBean == null) {
            return TableBeanForPurge.DEFAULT_CONFIG;
        }

        return TableBeanForPurge.builder()
                .title(
                        tableBean.getTitle() != null
                                ? tableBean.getTitle()
                                : TableBeanForPurge.DEFAULT_CONFIG.getTitle())
                .fontSize(
                        tableBean.getFontSize() != 0
                                ? tableBean.getFontSize()
                                : TableBeanForPurge.DEFAULT_CONFIG.getFontSize())
                .headers(
                        tableBean.getHeaders() != null
                                ? tableBean.getHeaders()
                                : TableBeanForPurge.DEFAULT_CONFIG.getHeaders())
                .values(
                        tableBean.getValues() != null
                                ? tableBean.getValues()
                                : TableBeanForPurge.DEFAULT_CONFIG.getValues())
                .headerBackgroundColor(
                        tableBean.getHeaderBackgroundColor() != null
                                ? tableBean.getHeaderBackgroundColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getHeaderBackgroundColor())
                .valueBackgroundColor(
                        tableBean.getValueBackgroundColor() != null
                                ? tableBean.getValueBackgroundColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getValueBackgroundColor())
                .headerValueFontColor(
                        tableBean.getHeaderValueFontColor() != null
                                ? tableBean.getHeaderValueFontColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getValueFontColor())
                .valueFontColor(
                        tableBean.getValueFontColor() != null
                                ? tableBean.getValueFontColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getValueFontColor())
                .successFontColor(
                        tableBean.getSuccessFontColor() != null
                                ? tableBean.getSuccessFontColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getSuccessFontColor())
                .errorFontColor(
                        tableBean.getErrorFontColor() != null
                                ? tableBean.getErrorFontColor()
                                : TableBeanForPurge.DEFAULT_CONFIG.getErrorFontColor())
                .build();
    }

    private static Color retrieveCellFontColor(String value, TableBeanForPurge inputBean) {
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

