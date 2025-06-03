package com.p3.ads.runner.component;

import com.p3.ads.core.Report;
import com.p3.ads.core.ReportBuilder;
import com.p3.ads.enums.ComponentType;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.JobStatusEnum;
import com.p3.ads.enums.TableType;
import lombok.extern.slf4j.Slf4j;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.p3.ads.constants.FontSizeConstants.EIGHT_FONT_SIZE;
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
                          "PROC_CODE, CLAIM_SYS_PROC_CODE",
                          "100, Content: 0",
                          "0, Content: 0",
                          "00:03:09.482",
                          "Success"),
                      List.of(
                          "DX_CODE, CLAIM_SYS_DX_CODE",
                          "100, Content: 10",
                          "0, Content: 0",
                          "00:04:09.482",
                          "Failed"),
                      List.of(
                          "SUBSCRIBERS, CLAIM_SYS_SUBSCRIBER",
                          "0, Content: 0",
                          "0, Content: 0",
                          "00:03:09.482",
                          "Success"),
                          List.of(
                                  "ADDRESS, CLAIM_SYS_ADDRESS",
                                  "0, Content: 10",
                                  "0, Content: 0",
                                  "00:04:09.482",
                                  "Failed"),
                          List.of(
                                  "PROVIDER, CLAIM_SYS_PROVIDER",
                                  "100, Content: 0",
                                  "0, Content: 0",
                                  "00:03:09.482",
                                  "Success")))
              .headerFontFamily(FontFamilyType.ROBOTO_REGULAR)
              .cellFontFamily(FontFamilyType.ROBOTO_REGULAR)
              .headerFontSize(11.5f)
              .cellFontSize(9.5f)
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
                  .jobStatusInputBean(JobStatusInputBean.builder()
                          .jobStatus(JobStatusEnum.FAILURE)
                          .errorMessage("Some Tables Failed To Rollback")
                          .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
                          .errorMessageFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                          .fontSize(EIGHT_FONT_SIZE)
                          .build())
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
