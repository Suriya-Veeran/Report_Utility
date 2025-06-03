package com.p3.ads.builder;

import lombok.experimental.UtilityClass;
import com.p3.ads.beans.TableBean;
import com.p3.ads.enums.FontFamilyType;

import java.util.List;

@UtilityClass
public class TableConfigBuilder {

    // ✅ **1️⃣ Get Default Table Configuration**
    public static TableBean getDefaultTable() {
        return TableBean.DEFAULT_CONFIG;
    }

    // ✅ **2️⃣ Flexible Table Configuration Builder**
    public static TableBean createTable(String title, List<String> headers, List<List<String>> values,
                                        FontFamilyType headerFontFamily, Float headerFontSize, FontFamilyType cellFontFamily, Float cellFontSize, String headerBgColor,
                                        String valueBgColor, String headerFontColor, String valueFontColor,
                                        String successFontColor, String errorFontColor) {

        return TableBean.builder()
                .title(title != null ? title : TableBean.DEFAULT_CONFIG.getTitle())
                .headers(headers != null ? headers : TableBean.DEFAULT_CONFIG.getHeaders())
                .values(values != null ? values : TableBean.DEFAULT_CONFIG.getValues())
                .headerFontFamily(headerFontFamily != null ? headerFontFamily : TableBean.DEFAULT_CONFIG.getHeaderFontFamily())
                .cellFontFamily(cellFontFamily != null ? cellFontFamily : TableBean.DEFAULT_CONFIG.getCellFontFamily())
                .headerFontSize(headerFontSize != null ? headerFontSize : TableBean.DEFAULT_CONFIG.getHeaderFontSize())
                .cellFontSize(cellFontSize != null ? cellFontSize : TableBean.DEFAULT_CONFIG.getCellFontSize())
                .headerBackgroundColor(headerBgColor != null ? headerBgColor : TableBean.DEFAULT_CONFIG.getHeaderBackgroundColor())
                .valueBackgroundColor(valueBgColor != null ? valueBgColor : TableBean.DEFAULT_CONFIG.getValueBackgroundColor())
                .headerValueFontColor(headerFontColor != null ? headerFontColor : TableBean.DEFAULT_CONFIG.getHeaderValueFontColor())
                .valueFontColor(valueFontColor != null ? valueFontColor : TableBean.DEFAULT_CONFIG.getValueFontColor())
                .successFontColor(successFontColor != null ? successFontColor : TableBean.DEFAULT_CONFIG.getSuccessFontColor())
                .errorFontColor(errorFontColor != null ? errorFontColor : TableBean.DEFAULT_CONFIG.getErrorFontColor())
                .build();
    }


}

