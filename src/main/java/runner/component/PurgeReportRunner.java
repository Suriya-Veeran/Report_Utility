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

      TableBean tableBean =
          TableBean.builder()
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
                      List.of("Employees", "104_ret, Retention Set_17", "N/A", "3","0", "Success", "N/A"),
                          List.of("Jobs", "Retention Set_17", "N/A", "2","0", "Disposed Failure", "Expired"),
                          List.of("Job History", "104_ret, Retention Set_17", "N/A", "3","0", "Success", "N/A")
                  ))
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
              .title("Job Summary")
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
