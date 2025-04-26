package runner.component;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.*;
import report_utility.beans.charts.DataInfoBean;
import report_utility.core.Report;
import report_utility.core.ReportBuilder;
import report_utility.enums.*;
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
//                  .jobStatusInputBean(JobStatusInputBean.builder()
//                          .jobStatus(JobStatusEnum.FAILURE)
//                          .errorMessage("Schema ad_test_dbo2 is not found")
//                          .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
//                          .errorMessageFontFamily(FontFamilyType.ROBOTO_MEDIUM)
//                          .fontSize(8f)
//                          .build())
              .build());

      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());

//      List<String> pieData = List.of("Succeeded");
      List<String> pieData = List.of("Succeeded", "Failed");
      List<DataInfoBean> pieDataInfoList =
          List.of(
              buildDataInfoBean("Succeeded", 1, FormatTypes.GB, "#397EE3"),
              buildDataInfoBean("Failed", 250, FormatTypes.MB, "#E53939"));

      List<ChartInputBean> chartInputBeans = new LinkedList<>();
      chartInputBeans.add(
          ChartInputBean.builder()
              .chartTitle("Table")
              .chartFontSize(30)
              .chartWidth("500px")
              .chartHeight("500px")
              .chartType("pie")
              .chartFontFamily(FontFamilyType.HELVETICA)
              .legendInfo(pieData)
              .dataInfo(pieDataInfoList)
              .build());

//      List<String> doughnutData = List.of("Structured", "Unstructured", "Compliance", "Disposed");
//      List<DataInfoBean> doughnutDataInfoList =
//          List.of(
//              buildDataInfoBean("Structured", 347, FormatTypes.GB, "#397EE3"),
//              buildDataInfoBean("Unstructured", 100, FormatTypes.GB, "#406292"),
//              buildDataInfoBean("Compliance", 32, FormatTypes.GB, "#697A91"),
//              buildDataInfoBean("Disposed", 512, FormatTypes.MB, "#9AC2FC"));
//      chartInputBeans.add(
//          ChartInputBean.builder()
//              .chartTitle("Files")
//              .chartFontSize(16)
//              .chartWidth("600px")
//              .chartHeight("500px")
//              .chartType("doughnut")
//              .chartFontFamily(FontFamilyType.HELVETICA)
//              .legendInfo(doughnutData)
//              .dataInfo(doughnutDataInfoList)
//              .build());

//      List<String> pieData1 = List.of("Succeeded");
      List<String> pieData1 = List.of("Succeeded", "Failed");
      List<DataInfoBean> pieDataInfoList1 =
              List.of(
                      buildDataInfoBean("Succeeded", 1, FormatTypes.GB, "#397EE3"),
                      buildDataInfoBean("Failed", 100, FormatTypes.MB, "#E53939"));
      chartInputBeans.add(
              ChartInputBean.builder()
                      .chartTitle("Files")
                      .chartFontSize(30)
                      .chartWidth("500px")
                      .chartHeight("500px")
                      .chartType("pie")
                      .chartFontFamily(FontFamilyType.HELVETICA)
                      .legendInfo(pieData1)
                      .dataInfo(pieDataInfoList1)
                      .build());

      ChartCreationConfig chartCreationConfig =
          ChartCreationConfig.builder()
              .title("Session Metrics")
              .titleFontFamily(FontFamilyType.ROBOTO_BOLD)
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
