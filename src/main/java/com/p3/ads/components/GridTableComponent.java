    package com.p3.ads.components;

    import com.itextpdf.kernel.colors.Color;
    import com.itextpdf.kernel.font.PdfFont;
    import com.itextpdf.kernel.geom.PageSize;
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
    import com.p3.ads.beans.GridTableBean;
    import com.p3.ads.beans.JobStatusInputBean;
    import com.p3.ads.constants.FontSizeConstants;
    import com.p3.ads.core.interfaces.ReportComponent;
    import com.p3.ads.enums.FontFamilyType;
    import com.p3.ads.enums.TableType;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.IOException;
    import java.util.LinkedHashMap;
    import java.util.Map;

    import static com.p3.ads.constants.ColorConstants.WHITE_FONT_COLOR;
    import static com.p3.ads.constants.CommonConstants.*;
    import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
    import static com.p3.ads.utils.CommonUtils.*;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class GridTableComponent implements ReportComponent {

        private GridTableBean inputBean;


        @Override
        public void render(Document document) throws IOException {

            inputBean = mergeWithDefaults(inputBean);

            String backgroundColor = inputBean.getTableType() == TableType.HEADER
                    ? "F9F9F9"
                    : WHITE_FONT_COLOR;


            if (inputBean.getTableType() == TableType.HEADER) {
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
            }

            if(inputBean.getIsEmptySpaceNeededAtFront()){
                document.add(new Paragraph().setBackgroundColor(hexaDecimalToRGB(backgroundColor)));
            }

            if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
                addEmptyLines(1, document);
                document.add(new Paragraph(inputBean.getTitle())
                        .setTextAlignment(TextAlignment.LEFT)
                        .setFontColor(hexaDecimalToRGB(inputBean.getTitleFontColor()))
                        .setFontSize(inputBean.getTitleFontSize())
                        .setMarginLeft(MARGIN_LEFT)
                        .setMarginTop(0)
                        .setMarginBottom(0)
                        .setPadding(0)
                        .setFont(loadFont(inputBean.getTitleFontFamily().getValue()))
                );
                drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, "BCBCBC");
            }

            if (Boolean.TRUE.equals(inputBean.getIsJobStatusInclusion()) && inputBean.getJobStatusInputBean() != null) {
                renderJobStatus(document, inputBean.getJobStatusInputBean());
            }

            if (inputBean.getGridValues() != null && !inputBean.getGridValues().isEmpty()) {
                renderGridTable(document, inputBean);
            }

        }

        private void renderGridTable(Document document,
                                     GridTableBean inputBean) throws IOException {
            String backgroundColor = inputBean.getTableType() == TableType.HEADER
                    ? "F9F9F9"
                    : WHITE_FONT_COLOR;
            Table table = new Table(UnitValue.createPercentArray(inputBean.getColumnWidths()));
            table.setMarginLeft(-36);
            table.setBackgroundColor(hexaDecimalToRGB(backgroundColor));
            table.setWidth(PageSize.A4.getWidth());
//            table.setPaddingTop(5);
            if(inputBean.getTableType() == TableType.HEADER){
                table.setMarginTop(-5);
            }
            table.setBorder(Border.NO_BORDER);
            table.setBackgroundColor(hexaDecimalToRGB(backgroundColor));
            if (inputBean.getGridValues() != null && !inputBean.getGridValues().isEmpty()) {
                setCellValues(table, inputBean.getGridValues(), inputBean);
            }
            table.setSkipLastFooter(true);
            document.add(table);
            if (inputBean.getTableType() == TableType.HEADER) {
                drawDivider(document,  LINE_WIDTH_1L, "#BCBCBC", "Header Grid");
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
                                .setBorder(Border.NO_BORDER)
                                .setBackgroundColor(hexaDecimalToRGB(backgroundColor))
                                .setBorderBottom(new SolidBorder( hexaDecimalToRGB(backgroundColor), 4f))
                                .setBorderTop(new SolidBorder( hexaDecimalToRGB(backgroundColor), 4f))
                                .setTextAlignment(TextAlignment.LEFT)
                                .setVerticalAlignment(VerticalAlignment.TOP);
                table.addCell(cell);
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
                    inputBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Success")
                            ? hexaDecimalToRGB("007D2B")
                            : inputBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Failed")?
                            hexaDecimalToRGB("D60000"): hexaDecimalToRGB("FFAA1D");

            jobStatusCell.add(
                    new Paragraph(new Text("Job Status : "
                            + inputBean.getJobStatusInputBean().getJobStatus().getStatus()))
                           .setFont(loadFont(jobStatusInputBean.getJobStatusFontFamily().getValue()))
                            .setFontSize(inputBean.getJobStatusInputBean().getFontSize())
                            .setFontColor(hexaDecimalToRGB(WHITE_FONT_COLOR))
                            .setTextAlignment(TextAlignment.LEFT));
            jobStatusCell.setWidth(UnitValue.createPercentValue(100));
            jobStatusCell.setBackgroundColor(fontColor);
            jobStatusCell.setBorder(Border.NO_BORDER);
            jobStatusCell.setPadding(0);
            jobStatusCell.setPaddingLeft(6f);
            jobStatusCell.setPaddingBottom(1.7f);
            table.addCell(jobStatusCell);

            if (inputBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Failed")) {
                Cell errorCell = new Cell(1, 3);
                errorCell.add(new Paragraph(new Text("Error Message : "
                                + inputBean.getJobStatusInputBean().getErrorMessage()))
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
                    .titleFontFamily(inputBean.getTitleFontFamily() != null ? gridTableBean.getTitleFontFamily() : GridTableBean.DEFAULT_CONFIG.getTitleFontFamily())
                    .titleFontSize(inputBean.getTitleFontSize() != 0 ? gridTableBean.getTitleFontSize() : GridTableBean.DEFAULT_CONFIG.getTitleFontSize())
                    .titleFontColor(inputBean.getTitleFontColor() != null ? gridTableBean.getTitleFontColor() : GridTableBean.DEFAULT_CONFIG.getTitleFontColor())
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
