package com.p3.ads.runner.enums;

import com.p3.ads.exception.EnumNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportNameConstants {
  MATERIALIZED_VIEW_REFRESH_REPORT(
      "Materialized View Refresh Report", "Materialized_View_Refresh.pdf"),

  TABLE_OPTIMIZATION_REPORT("Table Data Optimization Report", "Table_Optimization.pdf"),

  PURGE_REPORT("Purge Report", "Purge_Report.pdf"),

  INGESTION_REPORT("Ingestion Report", "Ingestion_Report.pdf"),

  LICENSE_VOLUME_STATISTICS_REPORT(
      "License Volume Statistics Report", "License_Volume_Statistics.pdf"),

  ROLLBACK_REPORT("Rollback Report", "Rollback_Report.pdf"),

  CONSOLIDATED_INGESTION_VALIDATION_REPORT(
      "Consolidation Ingestion Report", "Consolidated_Ingestion_Validation.pdf"),

  AUDIT_REPORT("Audit Report", "Audit_Report.pdf"),

  CHAIN_OF_CUSTODY_REPORT("Chain of Custody Report", "Chain_of_Custody.pdf"),

  SOURCE_TO_TARGET_VALIDATION_REPORT(
      "Source To Target Validation Report", "Source_To_Target_Validation_Report.pdf");

  private final String reportName;
  private final String fileName;

  public static ReportNameConstants getReportNameConstants(String reportName) {
    for (ReportNameConstants constants : ReportNameConstants.values()) {
      if (constants.getReportName().equals(reportName)) {
        return constants;
      }
    }
    throw new EnumNotFound(reportName);
  }
}
