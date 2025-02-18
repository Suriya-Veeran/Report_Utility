package runner.component;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.*;
import report_utility.beans.charts.DataInfoBean;
import report_utility.core.Report;
import report_utility.core.ReportBuilder;
import report_utility.enums.ComponentType;
import report_utility.enums.FontFamilyType;
import report_utility.enums.FormatTypes;
import report_utility.enums.TableType;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static report_utility.utils.chart.ChartBeanUtils.buildDataInfoBean;
import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

@Slf4j
public class SourceToValidationRunner implements CommonRunner {
  @Override
  public void generateReport(String location, ReportNameConstants reportNameConstants) {

    try {
      Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
      report.addComponent(
          ComponentType.HEADER,
          HeaderBean.builder().title(reportNameConstants.getReportName()).build());
      report.addComponent(
          ComponentType.GRID_SECTION,
          GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());

      report.addComponent(
          ComponentType.GRID_SECTION,
          GridTableBean.builder()
              .gridValues(buildJobSummaryParameters(reportNameConstants))
              .title(JOB_SUMMARY)
              .tableType(TableType.SUMMARY)
              .isJobStatusInclusion(true)
              .build());

      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());

      List<String> pieData = List.of("350 GB", "650 GB");
      List<DataInfoBean> pieDataInfoList =
          List.of(
              buildDataInfoBean("350 GB", 350, FormatTypes.GB, "#397EE3"),
              buildDataInfoBean("650 GB", 650, FormatTypes.GB, "#9AC2FC"));

      List<ChartInputBean> chartInputBeans = new LinkedList<>();
      chartInputBeans.add(
          ChartInputBean.builder()
              .chartTitle("Table")
              .chartFontSize(16)
              .chartWidth("500px")
              .chartHeight("400px")
              .chartType("pie")
              .chartFontFamily(FontFamilyType.HELVETICA)
              .legendInfo(pieData)
              .dataInfo(pieDataInfoList)
              .build());

      List<String> doughnutData = List.of("Structured", "Unstructured", "Compliance", "Disposed");
      List<DataInfoBean> doughnutDataInfoList =
          List.of(
              buildDataInfoBean("Structured", 347, FormatTypes.GB, "#397EE3"),
              buildDataInfoBean("Unstructured", 100, FormatTypes.GB, "#406292"),
              buildDataInfoBean("Compliance", 32, FormatTypes.GB, "#697A91"),
              buildDataInfoBean("Disposed", 512, FormatTypes.MB, "#9AC2FC"));
      chartInputBeans.add(
          ChartInputBean.builder()
              .chartTitle("Files")
              .chartFontSize(16)
              .chartWidth("500px")
              .chartHeight("400px")
              .chartType("doughnut")
              .chartFontFamily(FontFamilyType.HELVETICA)
              .legendInfo(doughnutData)
              .dataInfo(doughnutDataInfoList)
              .build());

      ChartCreationConfig chartCreationConfig =
          ChartCreationConfig.builder()
              .title("Session Metrics")
              .titleFontFamily(FontFamilyType.ROBOTO_MEDIUM)
              .titleFontSize(13)
              .imageWidth(400)
              .imageHeight(250)
              .chartInputBean(chartInputBeans)
              .build();

      report.addComponent(ComponentType.CHART_SECTION, chartCreationConfig);
      report.addComponent(ComponentType.FOOTER, FooterBean.DEFAULT_CONFIG);
      report.render();
      report.close();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
