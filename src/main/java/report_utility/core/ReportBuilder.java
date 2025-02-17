package report_utility.core;

import lombok.extern.slf4j.Slf4j;
import report_utility.beans.*;
import report_utility.components.*;
import report_utility.core.factory.ReportComponentFactory;
import report_utility.core.interfaces.ReportBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.enums.ComponentType;

import java.io.IOException;

@Slf4j
public class ReportBuilder extends AbstractReport {

    private final Report report;

    public ReportBuilder(String outputPath) throws IOException {
        super(outputPath);
        this.report = new Report(document);
    }

    public ReportBuilder(String outputPath, String filename) throws IOException {
        super(outputPath, filename);
        this.report = new Report(document);
    }

    public ReportBuilder appendComponent(ComponentType type, ReportBean bean) {
        report.addComponent(ReportComponentFactory.createComponent(type, bean));
        return this;
    }

    public static ReportComponent addComponent(HeaderBean bean) {
        return HeaderComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(GridTableBean bean) {
        return GridTableComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(CardBean bean) {
        return CardComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(ObjectiveBean bean) {
        return ObjectiveComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(ChartCreationConfig bean) {
        return ChartComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(FooterBean bean) {
        return FooterComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(TableBean bean) {
        return TableComponent.builder()
                .inputBean(bean).build();
    }


    public Report build() {
        if (report.getDocument() == null) {
            throw new IllegalStateException("Document is null in ReportBuilder.build()");
        }
        return report;
    }

}
