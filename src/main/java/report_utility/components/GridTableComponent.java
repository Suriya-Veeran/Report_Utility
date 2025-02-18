    package report_utility.components;

    import com.itextpdf.kernel.colors.Color;
    import com.itextpdf.kernel.font.PdfFont;
    import com.itextpdf.kernel.geom.PageSize;
    import com.itextpdf.layout.Document;
    import com.itextpdf.layout.borders.Border;
    import com.itextpdf.layout.element.Cell;
    import com.itextpdf.layout.element.Paragraph;
    import com.itextpdf.layout.element.Table;
    import com.itextpdf.layout.element.Text;
    import com.itextpdf.layout.properties.TextAlignment;
    import com.itextpdf.layout.properties.UnitValue;
    import com.itextpdf.layout.properties.VerticalAlignment;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import report_utility.beans.GridTableBean;
    import report_utility.beans.JobStatusInputBean;
    import report_utility.core.interfaces.ReportComponent;
    import report_utility.enums.FontFamilyType;
    import report_utility.enums.TableType;

    import java.io.IOException;
    import java.util.LinkedHashMap;
    import java.util.Map;

    import static report_utility.constants.CommonConstants.*;
    import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
    import static report_utility.utils.CommonUtils.*;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class GridTableComponent implements ReportComponent {

        private GridTableBean inputBean;


        @Override
        public void render(Document document) throws IOException {

            inputBean = mergeWithDefaults(inputBean);

            if (inputBean.getTableType() == TableType.HEADER) {
                addEmptyLines(2, document);
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
                    : "FFFFFF";
            Table table = new Table(UnitValue.createPercentArray(new float[]{450L, 450L, 450L}));
            table.setMarginLeft(-36);
            table.setWidth(PageSize.A4.getWidth());
            table.setPaddingTop(5);
            if(inputBean.getTableType() == TableType.HEADER){
                table.setMarginTop(-5);
            }
            table.setMarginBottom(0);
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
                    : "FFFFFF";

            float valueFontSize = inputBean.getFontSize();
            float headerFontSize = inputBean.getFontSize() - 1;

            PdfFont headerFont = loadFont(inputBean.getFontFamilyType().getValue());
            PdfFont valueFont = loadFont(FontFamilyType.ROBOTO_MEDIUM.getValue());

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
                                                .setBorder(Border.NO_BORDER)
                                                .setFontSize(headerFontSize))
                                .add(
                                        new Paragraph(new Text(value))
                                                .setFont(valueFont)
                                                .setFontColor(hexaDecimalToRGB("000000"))
                                                .setMarginTop(-5f)
                                                .setPaddingLeft(17)
                                                .setBorder(Border.NO_BORDER)
                                                .setFontSize(valueFontSize))
                                .setBackgroundColor(hexaDecimalToRGB(backgroundColor))
                                .setBorder(Border.NO_BORDER)
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
                            : hexaDecimalToRGB("D60000");

            jobStatusCell.add(
                    new Paragraph(new Text("Job Status : "
                            + inputBean.getJobStatusInputBean().getJobStatus().getStatus()))
                            .setFont(loadFont(jobStatusInputBean.getFontFamily().getValue()))
                            .setFontSize(inputBean.getJobStatusInputBean().getFontSize())
                            .setFontColor(hexaDecimalToRGB("FFFFFF"))
                            .setTextAlignment(TextAlignment.LEFT)
                            .setMarginLeft(4f));
            jobStatusCell.setWidth(UnitValue.createPercentValue(100));
            jobStatusCell.setBackgroundColor(fontColor);
            jobStatusCell.setBorder(Border.NO_BORDER);
            table.addCell(jobStatusCell);

            if (!inputBean.getJobStatusInputBean().getJobStatus().getStatus().equalsIgnoreCase("Success")) {
                Cell errorCell = new Cell(1, 3);
                errorCell.add(new Paragraph(new Text("Error Message : "
                                + inputBean.getJobStatusInputBean().getErrorMessage()))
                                .setFont(loadFont(jobStatusInputBean.getFontFamily().getValue())))
                        .setFontSize(inputBean.getJobStatusInputBean().getFontSize())
                        .setFontColor(fontColor)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setMarginLeft(4f);
                errorCell.setWidth(UnitValue.createPercentValue(100));
                errorCell.setBackgroundColor(hexaDecimalToRGB("FFEAEA"));
                errorCell.setBorder(Border.NO_BORDER);
                table.addCell(errorCell);
            }
            table.setMarginTop(2);
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
                    .fontSize(gridTableBean.getFontSize() != 0 ? gridTableBean.getFontSize() : GridTableBean.DEFAULT_CONFIG.getFontSize())
                    .fontColor(gridTableBean.getFontColor() != null ? gridTableBean.getFontColor() : GridTableBean.DEFAULT_CONFIG.getFontColor())
                    .isJobStatusInclusion(gridTableBean.getIsJobStatusInclusion() != null ? gridTableBean.getIsJobStatusInclusion() : GridTableBean.DEFAULT_CONFIG.getIsJobStatusInclusion())
                    .jobStatusInputBean(gridTableBean.getJobStatusInputBean() != null ? gridTableBean.getJobStatusInputBean() : GridTableBean.DEFAULT_CONFIG.getJobStatusInputBean())
                    .tableType(gridTableBean.getTableType() != null ? gridTableBean.getTableType() : GridTableBean.DEFAULT_CONFIG.getTableType())
                    .build();
        }
    }
