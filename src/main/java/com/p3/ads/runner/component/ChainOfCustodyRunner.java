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
import java.util.ArrayList;
import java.util.List;

import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

@Slf4j
public class ChainOfCustodyRunner implements CommonRunner {
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
                            .jobStatusInputBean(JobStatusInputBean.builder()
                            .jobStatus(JobStatusEnum.FAILURE)
                            .errorMessage("Schema ad_test_dbo2 is not found")
                            .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
                            .errorMessageFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                                    .fontSize(8f)
                            .build())
              .build());

      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());

//      TableBean chainOfCustodySummary =
//          TableBean.builder().title("Chain of Custody Summary") .build();

      TableBean schemaTable =
          TableBean.builder()
              .title("Schema Details")
              .headers(List.of("S.No", "Schema Name", "Ingestion Status", "Message"))
              .values(List.of(List.of("1", "10 TB", "Partially Ingested", "3/18 tables ingested")))
                  .headerFontSize(11.5f)
                  .cellFontSize(9.5f)
                  .drawDividerNeed("false")
                  .emptyLineNeedAtFront("false")
              .build();

      TableBean tableBean =
          TableBean.builder()
              .title("Table Details")
              .headers(List.of("S.No", "Schema Name", "Table Name", "Ingestion Status"))
              .values(
                  List.of(
                      List.of("1", "DBO", "ADDRESS", "Ingested")
                          ,
                      List.of("2", "DBO", "ASCMAP_CP", "Not Ingested"),
                      List.of("3", "DBO", "CLAIM", "Completed"),
                          List.of("4", "DBO", "LOGPROGRESS", "Not Ingested"),
                          List.of("5", "DBO", "MEMBER", "Not Ingested")

                  )
              )
              .headerFontFamily(FontFamilyType.ROBOTO_REGULAR)
              .cellFontFamily(FontFamilyType.ROBOTO_REGULAR)
                  .headerFontSize(11.5f)
                  .cellFontSize(9.5f)
                  .drawDividerNeed("false")
                  .emptyLineNeedAtFront("false")
              .build();


      List<String>value1=new ArrayList<>();
      value1.add("Table Row Count Test,Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value1.add("Table Column Count Test,Time Taken:00:00:00:641 | Status:Failed,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value1.add("Table Length Test(Rows accounted 10),Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.This Type Was Mismatched.");
      value1.add("Table Row Count Test,Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value1.add("Table Column Count Test,Time Taken:00:00:00:641 | Status:Failed,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");


      List<String>value2=new ArrayList<>();
      value2.add("Table Row Count Test,Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value2.add("Table Column Count Test,Time Taken:00:00:00:641 | Status:Failed,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value2.add("Table Length Test(Rows accounted 10),Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.This Type Was Mismatched.");
      value2.add("Table Row Count Test,Time Taken:00:00:00:641 | Status:Success,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");
      value2.add("Table Column Count Test,Time Taken:00:00:00:641 | Status:Failed,Message: The Table Contains" +
              "78 ingested records,matching the expected count.");

      CocCardBean cocCardBean=
              CocCardBean.builder()
                              .header("ADDRESS")
                              .subheader("_123_LIC_DBO_ADDRESS")
                              .values(value1)
                              .build();

      CocCardBean cocCardBean2=
              CocCardBean.builder()
                      .header("CLAIMS")
                      .subheader("_123_LIC_DBO_CLAIMS")
                      .values(value2)
                      .build();

      report.addComponent(ComponentType.TITLE, TitleBean.DEFAULT_CONFIG);
      report.addComponent(ComponentType.TABLE, schemaTable);
      report.addComponent(ComponentType.TABLE, tableBean);
      report.addComponent(ComponentType.COC_CARD_SECTION,cocCardBean);//New Line Added For Testing Purpose
      report.addComponent(ComponentType.COC_CARD_SECTION,cocCardBean2);//New Line Added For Testing Purpose
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
