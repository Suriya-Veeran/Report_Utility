package runner.component;

import static runner.utils.DataBuilderUtils.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.*;
import report_utility.core.Report;
import report_utility.core.ReportBuilder;
import report_utility.enums.ComponentType;
import report_utility.enums.FontFamilyType;
import report_utility.enums.TableType;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

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
              .title("Job Summary")
              .tableType(TableType.SUMMARY)
              .isJobStatusInclusion(true)
              .build());

      report.addComponent(
          ComponentType.OBJECTIVE,
          ObjectiveBean.builder()
              .description(buildObjectiveDescription(reportNameConstants))
              .build());

      TableBean chainOfCustodySummary =
          TableBean.builder().title("Chain of Custody Summary").build();

      TableBean schemaTable =
          TableBean.builder()
              .title("Schema Details")
              .headers(List.of("S.No", "Schema Name", "Ingestion Status", "Message"))
              .values(List.of(List.of("1", "10 TB", "Partially Ingested", "3/18 tables ingested")))
              .build();

      TableBean tableBean =
          TableBean.builder()
              .title("Table Details")
              .headers(List.of("S.No", "Schema Name", "Ingestion Status", "Message"))
              .values(
                  List.of(
                      List.of("1", "DBO", "Address", "Ingested")
                          ,
                      List.of("2", "DBO", "Claim", "Not Ingested"),
                      List.of("3", "DBO", "Member", "Completed")
                  )
              )
              .fontFamily(FontFamilyType.ROBOTO_REGULAR)
              .build();

      report.addComponent(ComponentType.TABLE, chainOfCustodySummary);
      report.addComponent(ComponentType.TABLE, schemaTable);
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
