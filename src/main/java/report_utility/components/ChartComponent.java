package report_utility.components;

import static report_utility.constants.ColorConstants.DIVIDER_GRAY_COLOR;
import static report_utility.constants.ColorConstants.GRAY_FONT_COLOR;
import static report_utility.constants.CommonConstants.*;
import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.*;
import static report_utility.utils.chart.ChartCreationConfigUtil.buildHtmlCreationInfoBean;
import static report_utility.utils.screenshot_utils.HeadlessScreenshot.takeScreenshot;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.ChartCreationConfig;
import report_utility.beans.charts.HtmlCreationInfoBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.utils.html.HtmlFileGenerator;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartComponent implements ReportComponent {

  private ChartCreationConfig inputBean;

  @Override
  public void render(Document document) throws IOException {

    inputBean = mergeWithDefaults(inputBean);

    if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
      document.add(
          new Paragraph(inputBean.getTitle())
              .setTextAlignment(TextAlignment.LEFT)
              .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
              .setFontSize(inputBean.getTitleFontSize())
              .setMarginLeft(MARGIN_LEFT)
              .setMarginTop(0)
              .setMarginBottom(0)
              .setPadding(0)
              .setFont(loadFont(inputBean.getTitleFontFamily().getValue())));
      drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
    }

    addEmptyLines(1, document);

    List<HtmlCreationInfoBean> htmlCreationInfoBeans = buildHtmlCreationInfoBean(inputBean);

    Table chartTable = new Table(inputBean.getChartInputBean().size());
    chartTable.setMarginLeft(MARGIN_LEFT);
    for (HtmlCreationInfoBean htmlCreationInfoBean : htmlCreationInfoBeans) {
      File htmlFile = HtmlFileGenerator.generateHtml(htmlCreationInfoBean);
      Image chart =
          takeScreenshot(
              htmlFile.toURI().toString(),
              "chrome",
              htmlCreationInfoBean.getChartBasicInfo().getChartType());
      chart.scaleToFit(inputBean.getImageWidth(), inputBean.getImageHeight());
      Cell chartCell =
          new Cell()
              .add(chart)
              .setTextAlignment(TextAlignment.LEFT)
              .setHorizontalAlignment(HorizontalAlignment.LEFT)
              .setBorder(Border.NO_BORDER);
      chartCell.setPaddingLeft(-80f);
      chartTable.addCell(chartCell);
      chartTable.setMarginTop(-10f);
    }
    chartTable.setFixedLayout();
    document.add(chartTable);
    addEmptyLines(5, document);
  }

  private ChartCreationConfig mergeWithDefaults(ChartCreationConfig inputBean) {

    if (inputBean == null) {
      return ChartCreationConfig.DEFAULT_CONFIG;
    }

    return ChartCreationConfig.builder()
        .title(
            inputBean.getTitle() != null
                ? inputBean.getTitle()
                : ChartCreationConfig.DEFAULT_CONFIG.getTitle())
        .titleFontFamily(
            inputBean.getTitleFontFamily() != null
                ? inputBean.getTitleFontFamily()
                : ChartCreationConfig.DEFAULT_CONFIG.getTitleFontFamily())
        .titleFontSize(
            inputBean.getTitleFontSize() != 0
                ? inputBean.getTitleFontSize()
                : ChartCreationConfig.DEFAULT_CONFIG.getTitleFontSize())
        .imageWidth(
            inputBean.getImageWidth() != 0
                ? inputBean.getImageWidth()
                : ChartCreationConfig.DEFAULT_CONFIG.getImageWidth())
        .imageHeight(
            inputBean.getImageHeight() != 0
                ? inputBean.getImageHeight()
                : ChartCreationConfig.DEFAULT_CONFIG.getImageHeight())
        .chartInputBean(
            inputBean.getChartInputBean() != null
                ? inputBean.getChartInputBean()
                : ChartCreationConfig.DEFAULT_CONFIG.getChartInputBean())
        .build();
  }
}
