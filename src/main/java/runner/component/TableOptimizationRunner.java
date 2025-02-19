package runner.component;

import static runner.constants.CommonConstants.JOB_SUMMARY;
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
public class TableOptimizationRunner implements CommonRunner {
    @Override
    public void generateReport(String location, ReportNameConstants reportNameConstants) {
        try {
            Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
            report.addComponent(ComponentType.HEADER, HeaderBean.builder().title(reportNameConstants.getReportName()).build());

            TableBean tableBean = TableBean.builder()
                    .title("Optimization Statistics")
                    .headers(List.of("Description", "Count of pre-data optimization",
                            "Count of post-data optimization", "Message"))
                    .values(List.of(
                            List.of("Data Snapshots", "2", "1", "1 snapshot removed"),
                            List.of("Data files", "8", "1", "8 files merged as 1"),
                            List.of("Size of Table", "85.432 KB", "23.745 KB", "Table size reduced to 23.745 KB")
                    ))
                    .fontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .fontSize(12)
                    .build();

            report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());
            report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildJobSummaryParameters(reportNameConstants))
                    .title(JOB_SUMMARY)
                    .tableType(TableType.SUMMARY)
                    .isJobStatusInclusion(true)
                    .build());
            report.addComponent(ComponentType.OBJECTIVE, ObjectiveBean.builder().description(buildObjectiveDescription(reportNameConstants)).build());
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
