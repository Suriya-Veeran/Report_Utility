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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.TableBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.utils.RoundedBorderCellRenderer;

import java.io.IOException;
import java.util.List;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.drawDivider;
import static report_utility.utils.CommonUtils.loadFont;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableComponent implements ReportComponent {

    private TableBean inputBean;

    @Override
    public void render(Document document) throws IOException {

        inputBean = mergeWithDefaults(inputBean);

        Table table = new Table(inputBean.getHeaders().size());
        table.setWidth(UnitValue.createPercentValue(100));
        table.setKeepTogether(false);
        table.setMarginLeft(-18f);
        table.setMarginRight(-18f);
        table.setPadding(0);

        if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
            document.add(new Paragraph(inputBean.getTitle())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(hexaDecimalToRGB("030303"))
                    .setFontSize(13)
                    .setMarginLeft(-17)
                    .setPaddingTop(5)
                    .setFont(loadFont(inputBean.getFontFamily().getValue()))
            );
            drawDivider(document, -18, -18, 1L, "BCBCBC");
        }

        for (String header : inputBean.getHeaders()) {

            Paragraph paragraph = new Paragraph();
            paragraph.setFont(loadFont(inputBean.getFontFamily().getValue()));

            String[] parts = header.split(",", 2);
            paragraph
                    .add(new Paragraph(parts[0]).setFixedLeading(10f))
                    .setFontSize(inputBean.getFontSize());

            if (parts.length > 1) {
                paragraph
                        .add("\n")
                        .add(
                                new Paragraph(parts[1].trim())
                                        .setPaddingTop(-45f)
                                        .setFontSize(inputBean.getFontSize()));
            }

            Cell headerCell =
                    new Cell()
                            .add(paragraph)
                            .setBorder(Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.LEFT)
                            .setFontSize(inputBean.getFontSize())
                            .setBackgroundColor(hexaDecimalToRGB("DFEAFF"))
                            .setPadding(10f);
            headerCell.setNextRenderer(new RoundedBorderCellRenderer(headerCell, hexaDecimalToRGB("000000"), 1.5f, 8f));
            table.addCell(headerCell);
        }

        int rowCount = inputBean.getValues().size();
        for (int i = 0; i < rowCount; i++) {
            List<String> rowValues = inputBean.getValues().get(i);
            for (String originalValue : rowValues) {
                Color fontColor = retrieveCellFontColor(originalValue, inputBean);
                Paragraph paragraph = new Paragraph();
                String[] parts = originalValue.split(",", 2);
                paragraph.add(
                        new Paragraph(parts[0]).setFontSize(inputBean.getFontSize()));

                if (parts.length > 1) {
                    paragraph
                            .add("\n")
                            .add(
                                    new Paragraph(parts[1].trim())
                                            .setPaddingLeft(-10)
                                            .setFontSize(inputBean.getFontSize() - 2f));
                }

                Cell cell =
                        new Cell()
                                .add(paragraph)
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontColor(fontColor)
                                .setPadding(10f);

                if (i == rowCount - 1) {
                    cell.setBorderBottom(Border.NO_BORDER);
                } else {
                    cell.setBorderBottom(new SolidBorder(hexaDecimalToRGB("DCDCDC"), 1));
                }
                cell.setNextRenderer(new RoundedBorderCellRenderer(cell, hexaDecimalToRGB("DCDCDC"), 1.5f, 8f));

                table.addCell(cell);
            }
        }
        document.add(table);
    }

    private TableBean mergeWithDefaults(TableBean tableBean) {
        if (tableBean == null) {
            return TableBean.DEFAULT_CONFIG;
        }

        return TableBean.builder()
                .title(tableBean.getTitle() != null ? tableBean.getTitle() : TableBean.DEFAULT_CONFIG.getTitle())
                .fontSize(tableBean.getFontSize() != 0 ? tableBean.getFontSize() : TableBean.DEFAULT_CONFIG.getFontSize())
                .fontFamily(tableBean.getFontFamily() != null ? tableBean.getFontFamily() : TableBean.DEFAULT_CONFIG.getFontFamily())
                .headers(tableBean.getHeaders() != null ? tableBean.getHeaders() : TableBean.DEFAULT_CONFIG.getHeaders())
                .values(tableBean.getValues() != null ? tableBean.getValues() : TableBean.DEFAULT_CONFIG.getValues())
                .build();
    }

    private static Color retrieveCellFontColor(String value, TableBean inputBean) {
        Color fontColor;
        if (value.equalsIgnoreCase("Disposed Success") || value.equalsIgnoreCase("Success")) {
            fontColor = hexaDecimalToRGB("007D2B");
        } else if (value.equalsIgnoreCase("Disposed Failure") || value.equalsIgnoreCase("Failed")) {
            fontColor = hexaDecimalToRGB("D60000");
        } else {
            fontColor = hexaDecimalToRGB("000000");
        }

        return fontColor;
    }
}
