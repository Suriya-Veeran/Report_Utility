package com.p3.ads.core;

import com.p3.ads.beans.*;
import com.p3.ads.components.*;
import com.p3.ads.core.factory.ReportComponentFactory;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.enums.ComponentType;
import lombok.extern.slf4j.Slf4j;

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

    public static ReportComponent addComponent(CardLayoutBean bean) {
        return CardLayoutComponent.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(TableContainerBean bean) {
        return TableComponentForBilling.builder()
                .tableBean(bean.getTable())
                .gridTableBean(bean.getGridTable())
                .build();
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

    public static ReportComponent addComponent(CocCardBean bean) {
        return CocCardComponent.builder()
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

    public static ReportComponent addComponent(TableBeanForPurge bean){
        return TableComponentForPurge.builder()
                .inputBean(bean).build();
    }

    public static ReportComponent addComponent(TitleBean bean){
        return TitleComponent.builder()
                .inputBean(bean).build();
    }


    public Report build() {
        if (report.getDocument() == null) {
            throw new IllegalStateException("Document is null in ReportBuilder.build()");
        }
        return report;
    }

}
