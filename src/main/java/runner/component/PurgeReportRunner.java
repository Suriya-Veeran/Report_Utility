package runner.component;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.TableBeanForPurge;
import report_utility.beans.*;
import report_utility.core.Report;
import report_utility.core.ReportBuilder;
import report_utility.enums.ComponentType;
import report_utility.enums.FontFamilyType;
import report_utility.enums.TableType;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

@Slf4j
public class PurgeReportRunner implements CommonRunner {
  @Override
  public void generateReport(String location, ReportNameConstants reportNameConstants) {

    try {
      Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
      report.addComponent(
          ComponentType.HEADER,
          HeaderBean.builder().title(reportNameConstants.getReportName()).build());

      Map<String, String> additionalParameters = new LinkedHashMap<>();
      additionalParameters.put("Approved By", "SYSTEM");
      additionalParameters.put("Approved date", "Apr 23 2024 06:08:04 GMT");
      additionalParameters.put("Approvel note", "Auto Approval");
      additionalParameters.put("Attachments", "0");

      TableBeanForPurge tableBean =
          TableBeanForPurge.builder()
              .title("Record / Group Details")
              .headers(
                  List.of(
                      "Table Name",
                      "Retention Sets",
                      "Qualification Name",
                      "Records qualified",
                      "Records disposed",
                      "Status",
                      "Failure Reason"))
              .values(
                  List.of(
                      List.of("EMPLOYEES", "104_ret,Retention_Set_17,123456", "N/A", "3","0", "Success", "Record in Hold/ Has another retention " +
                              "which is not expired"),
                          List.of("JOBS", "Retention_Set_1312354621", "N/A", "2","0", "Success", "Record in Hold/ Has another retention " +
                                  "which is not expired"),
                          List.of("JOB_HISTORY", "104_ret,Retention_Set_17_31313313,Retention_Set_12_3123213", "N/A", "3","0", "Success", "Record in Hold/ Has another retention " +
                                  "which is not expired")
                  ))
              .fontFamily(FontFamilyType.ROBOTO_REGULAR)
              .fontSize(11.5f)
              .build();

      report.addComponent(
          ComponentType.GRID_SECTION,
          GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());
      report.addComponent(
          ComponentType.GRID_SECTION,
          GridTableBean.builder()
              .gridValues(buildJobSummaryParameters(reportNameConstants))
              .title(JOB_SUMMARY)
              .tableType(TableType.SUMMARY)
//              .isJobStatusInclusion(true)
              .build());
      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());
      report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder()
              .gridValues(additionalParameters)
              .tableType(TableType.SUMMARY)
              .title("Approval Details").build());

      report.addComponent(ComponentType.TABLE_FOR_PURGE, tableBean);
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
