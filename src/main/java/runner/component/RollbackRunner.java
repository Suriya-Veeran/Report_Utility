package runner.component;

import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

@Slf4j
public class RollbackRunner implements CommonRunner {
  @Override
  public void generateReport(String location, ReportNameConstants reportNameConstants) {

    try {
      Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
      report.addComponent(
          ComponentType.HEADER,
          HeaderBean.builder().title(reportNameConstants.getReportName()).build());

      TableBean tableBean =
          TableBean.builder()
              .title("Table level Details")
              .headers(
                  List.of(
                      "Table Name",
                      "Ingestion Record Count, (for the session)",
                      "Record Count after rollback, (for the session)",
                      "Time Taken, (hh:mm:ss)",
                      "Status"))
              .values(
                  List.of(
                      List.of(
                          "Proc Code, CLAIM_SYS_PROC_CODE",
                          "100, Content: 0",
                          "0, Content: 0",
                          "00:03:09.482",
                          "Success"),
                      List.of(
                          "Dx Code, CLAIM_SYS_DX_CODE",
                          "100, Content: 10",
                          "0, Content: 0",
                          "00:04:09.482",
                          "Failure"),
                      List.of(
                          "Subscriber, CLAIM_SYS_SUBSCRIBER",
                          "100, Content: 0",
                          "1, Content: 1",
                          "00:03:09.482",
                          "Success")))
              .fontFamily(FontFamilyType.ROBOTO_REGULAR)
              .fontSize(12)
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
              .isJobStatusInclusion(true)
              .build());
      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());
      report.addComponent(ComponentType.TABLE, tableBean);
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
