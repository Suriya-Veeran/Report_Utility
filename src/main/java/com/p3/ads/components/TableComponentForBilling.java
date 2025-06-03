package com.p3.ads.components;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.p3.ads.beans.GridTableBean;
import com.p3.ads.beans.JobStatusInputBean;
import com.p3.ads.beans.TableBean;
import com.p3.ads.constants.FontSizeConstants;
import com.p3.ads.core.Report;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.TableType;
import com.p3.ads.utils.RoundedTableRenderer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TableComponentForBilling implements ReportComponent {


        private GridTableBean gridTableBean;
        private TableBean tableBean;


        @Override
        public void render(Document document) throws IOException {

            gridTableBean = mergeWithDefaults(gridTableBean);
            String backgroundColor = gridTableBean.getTableType() == TableType.HEADER
                    ? "F9F9F9"
                    : WHITE_FONT_COLOR;


            if (gridTableBean.getTableType() == TableType.HEADER) {
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
            }

            if(gridTableBean.getIsEmptySpaceNeededAtFront()){
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
            }

            if (gridTableBean.getTitle() != null && !gridTableBean.getTitle().isEmpty()) {
                addEmptyLines(1, document);
                document.add(new Paragraph(gridTableBean.getTitle())
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontColor(hexaDecimalToRGB(gridTableBean.getTitleFontColor()))
                        .setFontSize(gridTableBean.getTitleFontSize())
                        .setMarginLeft(MARGIN_LEFT)
                        .setMarginTop(0)
                        .setMarginBottom(0)
                        .setPadding(0)
                        .setFont(loadFont(gridTableBean.getTitleFontFamily().getValue()))
                );
                drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
            }

            if (Boolean.TRUE.equals(gridTableBean.getIsJobStatusInclusion()) && gridTableBean.getJobStatusInputBean() != null) {
                renderJobStatus(document, gridTableBean.getJobStatusInputBean());
            }

            if (gridTableBean.getGridValues() != null && !gridTableBean.getGridValues().isEmpty()) {
                renderGridTable(document, gridTableBean);
            }



            //table bean
            tableBean = mergeWithDefaults(tableBean);

            float topMargin= Report.isChartAdded()?12f:0f;//This Line Added For Space Adjustment In SecondPage

            if (tableBean.getTitle() != null && !tableBean.getTitle().isEmpty()) {

                document.add(
                        new Paragraph(tableBean.getTitle())
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
                                .setFontSize(13)
                                .setMarginLeft(MARGIN_LEFT)
                                .setMarginTop(topMargin)
                                .setMarginBottom(0)
                                .setPadding(0)
                                .setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue())));


                if(tableBean.getDrawDividerNeed().equalsIgnoreCase("true")) {
                    drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
                }
            }

            if(tableBean.getEmptyLineNeedAtFront().equalsIgnoreCase("true") ) {
                addEmptyLines(2, document);
            }


            if (tableBean.getHeaders() != null && !tableBean.getHeaders().isEmpty()) {
                Table table = new Table(tableBean.getHeaders().size());
                table.setWidth(UnitValue.createPercentValue(100));
                table.setKeepTogether(false);
                table.setMarginLeft(MARGIN_LEFT);
                table.setMarginRight(MARGIN_RIGHT);
                table.setPaddingTop(-20);
                float borderRadius = tableBean.getBorderRadius();
                float borderWidth = 1f;
                Color borderColor = hexaDecimalToRGB(BACKGROUND_COLOR);

                int currentHeader = 1;
                for (String header : tableBean.getHeaders()) {

                    Paragraph paragraph = new Paragraph();
                    paragraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    paragraph.setMultipliedLeading(0.8f);//added for space between lines in header handling
                    paragraph.setFont(loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue()));

                    String[] parts = header.split(",", 2);
                    paragraph
                            .add(new Paragraph(parts[0]))
                            .setFontSize(tableBean.getHeaderFontSize());

                    if (parts.length > 1) {
                        paragraph
                                .add("\n")
                                .add(
                                        new Paragraph(parts[1].trim())
                                                .setPaddingTop(-25f)
                                                .setFontSize(tableBean.getHeaderFontSize()));
                    }

                    Cell headerCell =
                            new Cell()
                                    .add(paragraph)
                                    .setBorder(Border.NO_BORDER)
                                    .setTextAlignment(TextAlignment.LEFT)
                                    .setFontSize(tableBean.getHeaderFontSize())
                                    .setBorderTop(new SolidBorder(borderColor, 0.5f))
                                    .setBorderBottom(new SolidBorder(borderColor, borderWidth))
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                                    .setWidth(8f)
                                    .setPaddingLeft(15f)
                            ;
                    if(currentHeader==1){
                        headerCell.setBorderTopLeftRadius(new BorderRadius(borderRadius));
                    }
                    if(currentHeader == tableBean.getHeaders().size()){
                        headerCell.setBorderTopRightRadius(new BorderRadius(borderRadius));
                    }
                    table.addHeaderCell(headerCell);
                    currentHeader++;
                }

                int rowCount = tableBean.getValues().size();
                for (int i = 0; i < rowCount; i++) {
                    List<String> rowValues = tableBean.getValues().get(i);
                    int j=0;
                    for (String originalValue : rowValues) {

                        Color fontColor = retrieveCellFontColor(originalValue, tableBean);
                        Paragraph paragraph = new Paragraph();
                        String[] parts = originalValue.split(",", 3);
                        String input=parts[0];

                        int openParenIndex = input.indexOf('(');
                        int closeParenIndex = input.indexOf(')', openParenIndex);

                        if(tableBean.getHasPercentageColorHighlight() && input.contains("(") && input.contains(")") && input.contains("%")) {
                            if (openParenIndex != -1 && closeParenIndex != -1) {
                                String part1 = input.substring(0, openParenIndex).trim();                           // "24 TB"
                                String part2 = input.substring(openParenIndex, openParenIndex + 1);                 // "("
                                paragraph.add(
                                        new Text(part1 + " " + part2)
                                                .setFontSize(tableBean.getCellFontSize())
                                );
                                String part3 = input.substring(openParenIndex + 1, closeParenIndex).trim();         // "80%"
                                paragraph.add(
                                        new Text(part3)
                                                .setFontSize(tableBean.getCellFontSize())
                                                .setFontColor(hexaDecimalToRGB(tableBean.getPercentageColorHighlight()))
                                );
                                String part4 = input.substring(closeParenIndex, closeParenIndex + 1);
                                paragraph.add(
                                        new Text(part4)
                                                .setFontSize(tableBean.getCellFontSize())
                                );
                            }
                        }
                        else {
                            paragraph.add(
                                    new Text(parts[0])
                                            .setFontSize(tableBean.getCellFontSize())
                            );
                        }
                        float fontSize= (j==0)? (tableBean.getCellFontSize()-5.5f): (tableBean.getCellFontSize()-2f);
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
                                        .setFont(loadFont(tableBean.getCellFontFamily().getValue()))
                                        .setBorderTop(Border.NO_BORDER)
                                        .setBorderLeft(Border.NO_BORDER)
                                        .setBorderRight(Border.NO_BORDER)
                                        .setFontColor(fontColor)
                                        .setFontSize(tableBean.getCellFontSize())
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
                checkAndAddDividerBasedOnSpacing(tableBean, document);
            }
        }

    private void checkAndAddDividerBasedOnSpacing(TableBean tableBean, Document document) {
        if(tableBean.getDrawDividerNeedAtBottom().equalsIgnoreCase("true")) {
            float yPosition = document.getRenderer()
                    .getCurrentArea()
                    .getBBox()
                    .getHeight();
            float availableHeight = yPosition ;

            float rowHeight = 80;  // estimated per-row height
            float requiredHeight = rowHeight * 2;  // need at least space for 2 rows

            if (availableHeight >= requiredHeight) {
                document.add(new Paragraph());
                document.add(new Paragraph());
                document.add(new Paragraph());
                drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);            }
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


        private void renderGridTable(Document document,
                                     GridTableBean inputBean) throws IOException {
            String backgroundColor = inputBean.getTableType() == TableType.HEADER
                    ? "F9F9F9"
                    : WHITE_FONT_COLOR;

            Table table = new Table(UnitValue.createPercentArray(inputBean.getColumnWidths()));
            table.setMarginLeft(-36);
            table.setWidth(PageSize.A4.getWidth());
            table.setPaddingTop(5);
            if(inputBean.getTableType() == TableType.HEADER){
                table.setMarginTop(0);
            }
            else{
                checkAndAddSpacing(document, table);
            }
            table.setMarginBottom(0);
            table.setBorder(Border.NO_BORDER);
            table.setBackgroundColor(hexaDecimalToRGB(backgroundColor));
            if (inputBean.getGridValues() != null && !inputBean.getGridValues().isEmpty()) {
                setCellValues(table, inputBean.getGridValues(), inputBean);
            }
            table.setSkipLastFooter(true);
            checkAndAddBasedOnSpacing(inputBean, document);
            document.add(table);
            if (inputBean.getTableType() == TableType.HEADER) {
                drawDivider(document,  LINE_WIDTH_1L, DIVIDER_GRAY_COLOR, "Header Grid");
            }
        }

    private void checkAndAddSpacing(Document document, Table table) {
                float yPosition = document.getRenderer()
                        .getCurrentArea()
                        .getBBox()
                        .getHeight();
                float availableHeight = yPosition ;


                if (availableHeight <= 150) {
                        table.setMarginTop(-15);
               }
                else{
                    table.setMarginTop(5);
                }
        }

    private void checkAndAddBasedOnSpacing(GridTableBean inputBean, Document document) {
        if(Boolean.TRUE.equals(inputBean.getStartAtNewPage())){
            float yPosition = document.getRenderer()
                    .getCurrentArea()
                    .getBBox()
                    .getHeight();
            float availableHeight = yPosition ;

            float rowHeight = 80;  // estimated per-row height
            float requiredHeight = rowHeight * 2;  // need at least space for 2 rows

            if (availableHeight < requiredHeight) {
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }
        }
    }

    private void setCellValues(Table table,
                                   Map<String, String> gridParameters,
                                   GridTableBean inputBean) throws IOException {

            String backgroundColor = inputBean.getTableType() == TableType.HEADER
                    ? "F9F9F9"
                    : WHITE_FONT_COLOR;

            float valueFontSize = inputBean.getValueFontSize();
            float headerFontSize = inputBean.getHeaderFontSize();

            PdfFont headerFont = loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue());
            PdfFont valueFont = loadFont(inputBean.getFontFamilyType().getValue());

            for (Map.Entry<String, String> entry : gridParameters.entrySet()) {
                String header = entry.getKey();
                String value = entry.getValue();
                Cell cell =
                        new Cell()
                                .add(
                                        new Paragraph(new Text(header))
                                                .setFont(headerFont)
                                                .setFontColor(hexaDecimalToRGB("2C2C2C"))
                                                .setPaddingLeft(17)
//                                                .setBold()
                                                .setBorder(Border.NO_BORDER)
                                                .setFontSize(headerFontSize))
                                .add(
                                        new Paragraph(new Text(value))
                                                .setFont(valueFont)
                                                .setFontColor(hexaDecimalToRGB("000000"))
//                                               .setBold()
                                                .setMarginTop(-6f)
                                                .setPaddingLeft(17)
                                                .setBorder(Border.NO_BORDER)
                                                .setFontSize(valueFontSize))
                                .setBackgroundColor(hexaDecimalToRGB(backgroundColor))
                                .setBorder(Border.NO_BORDER)
                                .setTextAlignment(TextAlignment.LEFT)
                                .setVerticalAlignment(VerticalAlignment.TOP);
                table.addCell(cell);
                table.setMarginBottom(5f);
            }
        }


        private void renderJobStatus(Document document,
                                     JobStatusInputBean jobStatusInputBean) throws IOException {

            Table table = new Table(UnitValue.createPercentArray(new float[]{450L, 450L, 450L}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setKeepTogether(false);
            table.setMarginLeft(MARGIN_LEFT);
            table.setMarginRight(MARGIN_RIGHT);
            table.setMarginTop(-10);
            table.setBorder(Border.NO_BORDER);
            setJobStatusCellValue(jobStatusInputBean, table);
            document.add(table);

        }

        private void setJobStatusCellValue(JobStatusInputBean jobStatusInputBean,
                                           Table table) throws IOException {

            Cell jobStatusCell = new Cell(1, 3);

            Color fontColor =
                    gridTableBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Success")
                            ? hexaDecimalToRGB("007D2B")
                            : gridTableBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Failed")?
                            hexaDecimalToRGB("D60000"): hexaDecimalToRGB("FFAA1D");

            jobStatusCell.add(
                    new Paragraph(new Text("Job Status : "
                            + gridTableBean.getJobStatusInputBean().getJobStatus().getStatus()))
                            .setFont(loadFont(jobStatusInputBean.getJobStatusFontFamily().getValue()))
                            .setFontSize(gridTableBean.getJobStatusInputBean().getFontSize())
                            .setFontColor(hexaDecimalToRGB(WHITE_FONT_COLOR))
                            .setTextAlignment(TextAlignment.LEFT));
            jobStatusCell.setWidth(UnitValue.createPercentValue(100));
            jobStatusCell.setBackgroundColor(fontColor);
            jobStatusCell.setBorder(Border.NO_BORDER);
            jobStatusCell.setPadding(0);
            jobStatusCell.setPaddingLeft(6f);
            jobStatusCell.setPaddingBottom(1.7f);
            table.addCell(jobStatusCell);

            if (gridTableBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Failed")) {
                Cell errorCell = new Cell(1, 3);
                errorCell.add(new Paragraph(new Text("Error Message : "
                                + gridTableBean.getJobStatusInputBean().getErrorMessage()))
                                .setFont(loadFont(jobStatusInputBean.getErrorMessageFontFamily().getValue())).setMultipliedLeading(1f))
                        .setFontSize(FontSizeConstants.NINE_FONT_SIZE)
                        .setFontColor(fontColor)
                        .setOpacity(1f)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setMarginLeft(4f);
                errorCell.setWidth(UnitValue.createPercentValue(100));
                errorCell.setBackgroundColor(hexaDecimalToRGB("FFEAEA"));
                errorCell.setBorder(Border.NO_BORDER);
                errorCell.setPadding(0);
                errorCell.setPaddingLeft(6f);
                errorCell.setPaddingTop(1.6f);
                errorCell.setPaddingBottom(3.5f);
                table.addCell(errorCell);
            }
            table.setMarginTop(3);
            table.setMarginBottom(5);
            table.setFixedLayout();

        }

        private GridTableBean mergeWithDefaults(GridTableBean gridTableBean) {
            if (gridTableBean == null) {
                return GridTableBean.DEFAULT_CONFIG;
            }

            return GridTableBean.builder()
                    .title(gridTableBean.getTitle() != null ? gridTableBean.getTitle() : GridTableBean.DEFAULT_CONFIG.getTitle())
                    .titleFontFamily(gridTableBean.getTitleFontFamily() != null ? gridTableBean.getTitleFontFamily() : GridTableBean.DEFAULT_CONFIG.getTitleFontFamily())
                    .titleFontSize(gridTableBean.getTitleFontSize() != 0 ? gridTableBean.getTitleFontSize() : GridTableBean.DEFAULT_CONFIG.getTitleFontSize())
                    .titleFontColor(gridTableBean.getTitleFontColor() != null ? gridTableBean.getTitleFontColor() : GridTableBean.DEFAULT_CONFIG.getTitleFontColor())
                    .gridValues(gridTableBean.getGridValues() != null ? gridTableBean.getGridValues() : new LinkedHashMap<>())
                    .fontFamilyType(gridTableBean.getFontFamilyType() != null ? gridTableBean.getFontFamilyType() : GridTableBean.DEFAULT_CONFIG.getFontFamilyType())
                    .valueFontSize(gridTableBean.getValueFontSize() != 0 ? gridTableBean.getValueFontSize() : GridTableBean.DEFAULT_CONFIG.getValueFontSize())
                    .headerFontSize(gridTableBean.getHeaderFontSize() != 0 ? gridTableBean.getHeaderFontSize() : GridTableBean.DEFAULT_CONFIG.getHeaderFontSize())
                    .fontColor(gridTableBean.getFontColor() != null ? gridTableBean.getFontColor() : GridTableBean.DEFAULT_CONFIG.getFontColor())
                    .isJobStatusInclusion(gridTableBean.getIsJobStatusInclusion() != null ? gridTableBean.getIsJobStatusInclusion() : GridTableBean.DEFAULT_CONFIG.getIsJobStatusInclusion())
                    .jobStatusInputBean(gridTableBean.getJobStatusInputBean() != null ? gridTableBean.getJobStatusInputBean() : GridTableBean.DEFAULT_CONFIG.getJobStatusInputBean())
                    .tableType(gridTableBean.getTableType() != null ? gridTableBean.getTableType() : GridTableBean.DEFAULT_CONFIG.getTableType())
                    .columnWidths(gridTableBean.getColumnWidths() != null ? gridTableBean.getColumnWidths() : GridTableBean.DEFAULT_CONFIG.getColumnWidths())
                    .isEmptySpaceNeededAtFront(gridTableBean.getIsEmptySpaceNeededAtFront() != null ? gridTableBean.getIsEmptySpaceNeededAtFront() : GridTableBean.DEFAULT_CONFIG.getIsEmptySpaceNeededAtFront())
                    .startAtNewPage(gridTableBean.getStartAtNewPage() !=null ? gridTableBean.getStartAtNewPage() : GridTableBean.DEFAULT_CONFIG.getStartAtNewPage()
                    )
                    .build();
        }


}
