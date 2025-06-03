package com.p3.ads.runner.factory;

import lombok.experimental.UtilityClass;
import runner.component.*;
import runner.enums.ReportNameConstants;
import runner.services.CommonRunner;

@UtilityClass
public class ReportGeneratorFactory {
    public static CommonRunner getReportGenerator(ReportNameConstants reportNameConstants) {
        switch (reportNameConstants) {
            case LICENSE_VOLUME_STATISTICS_REPORT:
                return new LicenseVolumeRunner();
            case MATERIALIZED_VIEW_REFRESH_REPORT:
                return new MaterializedViewRunner();
            case TABLE_OPTIMIZATION_REPORT:
                return new TableOptimizationRunner();
            case PURGE_REPORT:
                return new PurgeReportRunner();
            case ROLLBACK_REPORT:
                return new RollbackRunner();
            case AUDIT_REPORT:
                return new AuditReportRunner();
            case CONSOLIDATED_INGESTION_VALIDATION_REPORT:
                return new ConsolidatedIngestionValidationRunner();
            case SOURCE_TO_TARGET_VALIDATION_REPORT:
                return new SourceToValidationRunner();
            case INGESTION_REPORT:
                return new IngestionRunner();
            case CHAIN_OF_CUSTODY_REPORT:
                return new ChainOfCustodyRunner();
            default:
                throw new IllegalArgumentException("Unsupported report type " + reportNameConstants);
        }
    }
}
