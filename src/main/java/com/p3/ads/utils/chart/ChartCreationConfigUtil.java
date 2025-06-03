package com.p3.ads.utils.chart;

import com.p3.ads.beans.ChartCreationConfig;
import com.p3.ads.beans.ChartInputBean;
import com.p3.ads.beans.charts.HtmlCreationInfoBean;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;
import java.util.List;

import static com.p3.ads.utils.chart.ChartBeanUtils.createChartBasicInfo;

@UtilityClass
public class ChartCreationConfigUtil {

  public static List<HtmlCreationInfoBean> buildHtmlCreationInfoBean(
      ChartCreationConfig inputBean) {

    List<HtmlCreationInfoBean> htmlCreationInfoBeans = new LinkedList<>();
    for (ChartInputBean chartInputBean : inputBean.getChartInputBean()) {

      HtmlCreationInfoBean htmlCreationInfoBean =
          ChartBeanUtils.createChartConfig(
              createChartBasicInfo(
                  chartInputBean.getChartWidth(),
                  chartInputBean.getChartHeight(),
                  chartInputBean.getChartType()),
              ChartBeanUtils.createTitleConfig(
                  chartInputBean.getChartTitle(),
                  chartInputBean.getChartFontSize(),
                  chartInputBean.getChartFontFamily().getValue(),
                  "bold",
                  "#333"),
              ChartBeanUtils.createLegendInfoBean(chartInputBean.getLegendInfo()),
              ChartBeanUtils.createSeriesInfoBean(chartInputBean.getDataInfo()));

      htmlCreationInfoBeans.add(htmlCreationInfoBean);
    }
    return htmlCreationInfoBeans;
  } 
}
