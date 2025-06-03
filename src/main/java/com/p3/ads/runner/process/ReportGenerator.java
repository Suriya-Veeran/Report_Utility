//package com.p3.ads.com.p3.ads.runner.process;
//
//import com.p3.ads.exception.EnumNotFound;
//import com.p3.ads.exception.ReportGenerationException;
//import lombok.extern.slf4j.Slf4j;
//import com.p3.ads.runner.enums.ReportNameConstants;
//import com.p3.ads.runner.factory.ReportGeneratorFactory;
//import com.p3.ads.runner.services.CommonRunner;
//import com.p3.ads.runner.yaml_utils.ConfigLoader;
//
//@Slf4j
//public class ReportGenerator {
//    public static void main(String[] args) throws ReportGenerationException {
//        String reportName = ConfigLoader.getInstance().getReportName();
//        ReportNameConstants reportNameConstants =
//                ReportNameConstants.getReportNameConstants(reportName);
//        runComponent(ConfigLoader.getInstance().getLocation(), reportNameConstants);
//    }
//
//    private static void runComponent(String location, ReportNameConstants reportNameConstants) {
//        try {
//            CommonRunner reportGenerator = ReportGeneratorFactory.getReportGenerator(reportNameConstants);
//            reportGenerator.generateReport(location, reportNameConstants);
//        } catch (IllegalArgumentException e) {
//            throw new EnumNotFound("Unsupported report type " + reportNameConstants.getReportName());
//        }
//    }
//}
