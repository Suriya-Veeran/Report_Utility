package com.p3.ads.core.factory;


import com.p3.ads.beans.*;
import com.p3.ads.core.ReportBuilder;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.enums.ComponentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
            case CARD_SECTION:
                component = ReportBuilder.addComponent((CardBean) bean);
                break;
            case CARD_LAYOUT:
                component = ReportBuilder.addComponent((CardLayoutBean) bean);
                break;
            case COC_CARD_SECTION:
                component = ReportBuilder.addComponent((CocCardBean) bean);
                break;
            case OBJECTIVE:
                component = ReportBuilder.addComponent((ObjectiveBean) bean);
                break;
            case TABLE:
                component = ReportBuilder.addComponent((TableBean) bean);
                break;
            case TABLE_FOR_PURGE:
                component = ReportBuilder.addComponent((TableBeanForPurge) bean);
                break;
            case TITLE:
                component = ReportBuilder.addComponent((TitleBean) bean);
                break;
            case CHART_SECTION:
                component = ReportBuilder.addComponent((ChartCreationConfig) bean);
                break;
            case TABLE_CONTAINER:
                component = ReportBuilder.addComponent((TableContainerBean) bean);
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
