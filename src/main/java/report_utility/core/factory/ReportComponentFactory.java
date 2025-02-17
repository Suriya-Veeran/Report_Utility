package report_utility.core.factory;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import report_utility.beans.*;
import report_utility.core.ReportBuilder;
import report_utility.core.interfaces.ReportBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.enums.ComponentType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportComponentFactory {

    public static ReportComponent createComponent(ComponentType type, ReportBean bean) {
        if (bean == null) {
            throw new IllegalArgumentException("ReportBean cannot be null for component type: " + type);
        }

        ReportComponent component;
        switch (type) {
            case HEADER:
                component = ReportBuilder.addComponent((HeaderBean) bean);
                break;
            case GRID_SECTION:
                component = ReportBuilder.addComponent((GridTableBean) bean);
                break;
            case JOB_SUMMARY:
                component = ReportBuilder.addComponent((JobSummaryBean) bean);
                break;
            case OBJECTIVE:
                component = ReportBuilder.addComponent((ObjectiveBean) bean);
                break;
            case TABLE:
                component = ReportBuilder.addComponent((TableBean) bean);
                break;
            case CHART_SECTION:
                component = ReportBuilder.addComponent((ChartCreationConfig) bean);
                break;
            case FOOTER:
                component = ReportBuilder.addComponent((FooterBean) bean);
                break;
            default:
                throw new IllegalArgumentException("Unexpected component type: " + type);
        }
        return component;
    }
}
