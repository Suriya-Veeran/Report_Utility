package runner.component;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.*;
import report_utility.constants.FontSizeConstants;
import report_utility.core.Report;
import report_utility.core.ReportBuilder;
import report_utility.enums.ComponentType;
import report_utility.enums.FontFamilyType;
import report_utility.enums.JobStatusEnum;
import report_utility.enums.TableType;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static report_utility.constants.FontSizeConstants.EIGHT_FONT_SIZE;
import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

@Slf4j
public class ConsolidatedIngestionValidationRunner implements CommonRunner {
    @Override
    public void generateReport(String location, ReportNameConstants reportNameConstants) {
        try {
            Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
            report.addComponent(ComponentType.HEADER, HeaderBean.builder().title(reportNameConstants.getReportName()).build());

            report.addComponent(
                    ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());

            report.addComponent(
                    ComponentType.GRID_SECTION,
                    GridTableBean.builder()
                            .gridValues(buildJobSummaryParameters(reportNameConstants))
                            .title(JOB_SUMMARY)
                            .tableType(TableType.SUMMARY)
                            .isJobStatusInclusion(true)
//                            .jobStatusInputBean(JobStatusInputBean.builder()
//                                    .jobStatus(JobStatusEnum.FAILURE)
//                                    .errorMessage("Ingestion Failed Due To Some Issue")
//                                    .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
//                                    .errorMessageFontFamily(FontFamilyType.ROBOTO_MEDIUM)
//                                    .fontSize(EIGHT_FONT_SIZE)
//                                    .build())
                            .build());

            report.addComponent(
                    ComponentType.OBJECTIVE, ObjectiveBean.builder().description(buildObjectiveDescription(reportNameConstants)).build());

            Map<String, String> multipleCardValues = new LinkedHashMap<>();
            multipleCardValues.put("Ingestion Session Id", "1ef9b1d5-7533-4689-83fc");
            multipleCardValues.put("Scheduled By", "System");
            multipleCardValues.put("Scheduled Time", new Date().toString());
            multipleCardValues.put("Job Name", "Test Job");
            multipleCardValues.put("App Name", "Application");
            multipleCardValues.put("Schema Name", "Schema");
            multipleCardValues.put("Status", "Success");

            MultipleCardBean multipleCard =
                    MultipleCardBean.builder()
                            .title("Table Level Details")
                            .headerValue("ADS_DEMO_CHECK")
                            .subHeaderValue("_003_METADATA_ADS.ADS_CONTENT_FS")
                            .valueFontSize(FontSizeConstants.NINE_FONT_SIZE)
                            .values(multipleCardValues)
                            .build();
            report.addComponent(ComponentType.CARD_SECTION, multipleCard);

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
