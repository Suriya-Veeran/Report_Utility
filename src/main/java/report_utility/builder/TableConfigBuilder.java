package report_utility.builder;

import lombok.experimental.UtilityClass;
import report_utility.beans.TableBean;
import report_utility.enums.FontFamilyType;

import java.util.List;

@UtilityClass
public class TableConfigBuilder {

    // ✅ **1️⃣ Get Default Table Configuration**
    public static TableBean getDefaultTable() {
        return TableBean.DEFAULT_CONFIG;
    }

    // ✅ **2️⃣ Flexible Table Configuration Builder**
    public static TableBean createTable(String title, List<String> headers, List<List<String>> values,
                                        FontFamilyType fontFamily, Float fontSize, String headerBgColor,
                                        String valueBgColor, String headerFontColor, String valueFontColor,
                                        String successFontColor, String errorFontColor) {

        return TableBean.builder()
                .title(title != null ? title : TableBean.DEFAULT_CONFIG.getTitle())
                .headers(headers != null ? headers : TableBean.DEFAULT_CONFIG.getHeaders())
                .values(values != null ? values : TableBean.DEFAULT_CONFIG.getValues())
                .fontFamily(fontFamily != null ? fontFamily : TableBean.DEFAULT_CONFIG.getFontFamily())
                .fontSize(fontSize != null ? fontSize : TableBean.DEFAULT_CONFIG.getFontSize())
                .headerBackgroundColor(headerBgColor != null ? headerBgColor : TableBean.DEFAULT_CONFIG.getHeaderBackgroundColor())
                .valueBackgroundColor(valueBgColor != null ? valueBgColor : TableBean.DEFAULT_CONFIG.getValueBackgroundColor())
                .headerValueFontColor(headerFontColor != null ? headerFontColor : TableBean.DEFAULT_CONFIG.getHeaderValueFontColor())
                .valueFontColor(valueFontColor != null ? valueFontColor : TableBean.DEFAULT_CONFIG.getValueFontColor())
                .successFontColor(successFontColor != null ? successFontColor : TableBean.DEFAULT_CONFIG.getSuccessFontColor())
                .errorFontColor(errorFontColor != null ? errorFontColor : TableBean.DEFAULT_CONFIG.getErrorFontColor())
                .build();
    }

    // ✅ **3️⃣ Simplified Methods (Call createTable internally)**

    // Set Only Title
    public static TableBean setTitle(String title) {
        return createTable(title, null, null, null, null, null, null, null, null, null, null);
    }

    // Set Only Font Size
    public static TableBean setFontSize(float size) {
        return createTable(null, null, null, null, size, null, null, null, null, null, null);
    }

    // Set Only Headers
    public static TableBean setHeaders(List<String> headers) {
        return createTable(null, headers, null, null, null, null, null, null, null, null, null);
    }

    // Set Only Values
    public static TableBean setValues(List<List<String>> values) {
        return createTable(null, null, values, null, null, null, null, null, null, null, null);
    }

    // Set Font Family & Size
    public static TableBean setFontFamilyAndSize(FontFamilyType fontFamily, float fontSize) {
        return createTable(null, null, null, fontFamily, fontSize, null, null, null, null, null, null);
    }

    // Set Header & Value Background Colors
    public static TableBean setBackgroundColors(String headerColor, String valueColor) {
        return createTable(null, null, null, null, null, headerColor, valueColor, null, null, null, null);
    }

    // Set Header Font Color & Value Font Color
    public static TableBean setFontColors(String headerFontColor, String valueFontColor) {
        return createTable(null, null, null, null, null, null, null, headerFontColor, valueFontColor, null, null);
    }

    // Set Success & Error Font Colors
    public static TableBean setSuccessAndErrorColors(String successColor, String errorColor) {
        return createTable(null, null, null, null, null, null, null, null, null, successColor, errorColor);
    }

    // ✅ **4️⃣ Fully Customizable Table**
    public static TableBean customizeTable(String title, List<String> headers, List<List<String>> values,
                                           FontFamilyType fontFamily, float fontSize, String headerBgColor,
                                           String valueBgColor, String headerFontColor, String valueFontColor,
                                           String successFontColor, String errorFontColor) {
        return createTable(title, headers, values, fontFamily, fontSize, headerBgColor, valueBgColor,
                headerFontColor, valueFontColor, successFontColor, errorFontColor);
    }
}

