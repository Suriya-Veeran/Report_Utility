package com.p3.ads.builder;

import com.p3.ads.beans.HeaderBean;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.ProductLogo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HeaderConfigBuilder {

    // ✅ **1️⃣ Default Header Configuration**
    public static HeaderBean getDefaultHeader() {
        return HeaderBean.DEFAULT_CONFIG;
    }

    // ✅ **2️⃣ Flexible Header Configuration Builder**
    public static HeaderBean createHeader(String title, String fontColor, Float fontSize,
                                          FontFamilyType fontFamily, ProductLogo productLogo) {
        return HeaderBean.builder()
                .title(title != null ? title : HeaderBean.DEFAULT_CONFIG.getTitle())
                .fontColor(fontColor != null ? fontColor : HeaderBean.DEFAULT_CONFIG.getFontColor())
                .fontSize(fontSize != null ? fontSize : HeaderBean.DEFAULT_CONFIG.getFontSize())
                .fontFamily(fontFamily != null ? fontFamily : HeaderBean.DEFAULT_CONFIG.getFontFamily())
                .productLogo(productLogo != null ? productLogo : HeaderBean.DEFAULT_CONFIG.getProductLogo())
                .build();
    }

    // ✅ **3️⃣ Simplified Methods (Calls createHeader internally)**

    // Set Only Title
    public static HeaderBean setTitle(String title) {
        return createHeader(title, null, null, null, null);
    }

    // Set Only Font Size
    public static HeaderBean setFontSize(float size) {
        return createHeader(null, null, size, null, null);
    }

    // Set Only Font Color
    public static HeaderBean setFontColor(String color) {
        return createHeader(null, color, null, null, null);
    }

    // Set Only Font Family
    public static HeaderBean setFontFamily(FontFamilyType family) {
        return createHeader(null, null, null, family, null);
    }

    // Set Only Product Logo
    public static HeaderBean setProductLogo(ProductLogo logo) {
        return createHeader(null, null, null, null, logo);
    }

    // ✅ **4️⃣ Fully Customizable Header**
    public static HeaderBean customizeHeader(String title, String fontColor, float fontSize,
                                             FontFamilyType fontFamily, ProductLogo productLogo) {
        return createHeader(title, fontColor, fontSize, fontFamily, productLogo);
    }
}
