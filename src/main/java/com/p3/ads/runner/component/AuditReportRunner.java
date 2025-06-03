package com.p3.ads.runner.component;

import com.p3.ads.beans.FooterBean;
import com.p3.ads.beans.GridTableBean;
import com.p3.ads.beans.HeaderBean;
import com.p3.ads.beans.SingleCardBean;
import com.p3.ads.core.Report;
import com.p3.ads.core.ReportBuilder;
import com.p3.ads.enums.ComponentType;
import lombok.extern.slf4j.Slf4j;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

import static runner.utils.DataBuilderUtils.buildHeaderParameters;

@Slf4j
public class AuditReportRunner implements CommonRunner {
    @Override
    public void generateReport(String location, ReportNameConstants reportNameConstants) {

        try {
            Report report = new ReportBuilder(location, reportNameConstants.getFileName()).build();
            report.addComponent(ComponentType.HEADER, HeaderBean.builder().title(reportNameConstants.getReportName()).build());

            report.addComponent(
                    ComponentType.GRID_SECTION, GridTableBean.builder().gridValues(buildHeaderParameters(reportNameConstants)).build());

            String firstHeaderValue =
                    "User sysadmin (sysadmin@ads.com) exported the audit events for date range 2023-11-07 - 2024-06-12 as CSV";
            String firstHeader = " System | Management | Audit | Export";

            String secondHeaderValue =
                    "Output of Searches job (Job_name_1717412150060) processed for search "
                            + "'PartSupp_Supplier_Lineitem_Search_CustomTemp' under application "
                            + "'TST_CUSTOMTEMP_TPCH_DATASET' was downloaded by user sysadmin (sysadmin@ads.com).";

            String secondHeader = "System | Processing | Background Jobs | Download";

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(firstHeader).value(firstHeaderValue).build());

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(secondHeader).value(secondHeaderValue).build());

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(firstHeader).value(firstHeaderValue).build());

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(secondHeader).value(secondHeaderValue).build());

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(firstHeader).value(firstHeaderValue).build());

            report.addComponent(
                    ComponentType.CARD_SECTION,
                    SingleCardBean.builder().header(secondHeader).value(secondHeaderValue).build());


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
