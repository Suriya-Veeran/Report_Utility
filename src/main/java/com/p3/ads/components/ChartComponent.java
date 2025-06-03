package com.p3.ads.components;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.p3.ads.beans.ChartCreationConfig;
import com.p3.ads.beans.charts.HtmlCreationInfoBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.utils.html.HtmlFileGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.p3.ads.constants.ColorConstants.DIVIDER_GRAY_COLOR;
import static com.p3.ads.constants.ColorConstants.GRAY_FONT_COLOR;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.*;
import static com.p3.ads.utils.chart.ChartCreationConfigUtil.buildHtmlCreationInfoBean;
import static com.p3.ads.utils.screenshot_utils.HeadlessScreenshot.takeScreenshot;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartComponent implements ReportComponent {

  private ChartCreationConfig inputBean;

  @Override
  public void render(Document document) throws IOException {

    inputBean = mergeWithDefaults(inputBean);

    addEmptyLines(1, document);

    if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
      document.add(
          new Paragraph(inputBean.getTitle())
              .setTextAlignment(TextAlignment.LEFT)
              .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
              .setFontSize(inputBean.getTitleFontSize())
              .setMarginLeft(MARGIN_LEFT)
              .setMarginTop(0)
              .setMarginBottom(0)
              .setPadding(0)//Header Part
              .setFont(loadFont(inputBean.getTitleFontFamily().getValue())));
      drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
    }

    addEmptyLines(1, document);

    List<HtmlCreationInfoBean> htmlCreationInfoBeans = buildHtmlCreationInfoBean(inputBean);

    Table chartTable = new Table(inputBean.getChartInputBean().size());
    chartTable.setMarginLeft(MARGIN_LEFT);
    chartTable.setWidth(UnitValue.createPercentValue(100));
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
              .setBorder(Border.NO_BORDER)
              ;
      chartCell.setPaddingLeft(-80f);
      chartTable.addCell(chartCell);
      chartTable.setMarginTop(10f);
    }
//    chartTable.setFixedLayout();
    document.add(chartTable);


    addEmptyLines(8, document);//Before That Value Was 5

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
