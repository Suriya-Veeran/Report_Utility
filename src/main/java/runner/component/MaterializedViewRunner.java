package runner.component;

import static report_utility.constants.FontSizeConstants.EIGHT_FONT_SIZE;
import static report_utility.constants.FontSizeConstants.ELEVEN_FONT_SIZE;
import static runner.constants.CommonConstants.JOB_SUMMARY;
import static runner.utils.DataBuilderUtils.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
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

@Slf4j
public class MaterializedViewRunner implements CommonRunner {
    @Override
    public void generateReport(String location, ReportNameConstants reportNameConstants) {
        try {
            Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
            report.addComponent(ComponentType.HEADER, HeaderBean.builder().title(reportNameConstants.getReportName()).build());

            Map<String, String> additionalParameters = new LinkedHashMap<>();
            additionalParameters.put("Record Count Before Refresh", "0");
            additionalParameters.put("Record Count After Refresh", "702");

            report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());
            report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildJobSummaryParameters(reportNameConstants))
                    .title(JOB_SUMMARY)
                    .tableType(TableType.SUMMARY)
//                    .fontFamilyType(FontFamilyType.ROBOTO_REGULAR)//added
//                    .fontSize(ELEVEN_FONT_SIZE)//added
                    .isJobStatusInclusion(true)
//                            .jobStatusInputBean(JobStatusInputBean.builder()
//                                    .jobStatus(JobStatusEnum.FAILURE)
//                                    .errorMessage("Schema ad_test_dbo2 is not found")
//                                    .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
//                                    .errorMessageFontFamily(FontFamilyType.ROBOTO_MEDIUM)
//                                    .fontSize(EIGHT_FONT_SIZE)
//                                    .build())
                    .build());

            report.addComponent(ComponentType.OBJECTIVE, ObjectiveBean.builder().description(buildObjectiveDescription(reportNameConstants)).build());

            report.addComponent(ComponentType.GRID_SECTION, GridTableBean.builder()
                    .gridValues(additionalParameters)
                    .tableType(TableType.SUMMARY)
                    .title("Additional Details").build());

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
