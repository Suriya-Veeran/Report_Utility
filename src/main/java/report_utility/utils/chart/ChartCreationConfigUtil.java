package report_utility.utils.chart;

import lombok.experimental.UtilityClass;
import report_utility.beans.ChartCreationConfig;
import report_utility.beans.ChartInputBean;
import report_utility.beans.charts.HtmlCreationInfoBean;

import java.util.LinkedList;
import java.util.List;

import static report_utility.utils.chart.ChartBeanUtils.createChartBasicInfo;

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
