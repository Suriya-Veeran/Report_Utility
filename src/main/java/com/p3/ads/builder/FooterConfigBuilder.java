package com.p3.ads.builder;

import com.p3.ads.beans.FooterBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FooterConfigBuilder {

    // ✅ **1️⃣ Get Default Footer Configuration**
    public static FooterBean getDefaultFooter() {
        return FooterBean.DEFAULT_CONFIG;
    }

    // ✅ **2️⃣ Flexible Footer Configuration Builder**
    public static FooterBean createFooter(String color, Float size, FontFamilyType family) {
        return FooterBean.builder()
                .fontSize(size != null ? size : FooterBean.DEFAULT_CONFIG.getFontSize())
                .fontColor(color != null ? color : FooterBean.DEFAULT_CONFIG.getFontColor())
                .fontFamily(family != null ? family : FooterBean.DEFAULT_CONFIG.getFontFamily())
                .build();
    }

    // ✅ **3️⃣ Simplified Methods (Call createFooter internally)**

    // Set Only Font Size
    public static FooterBean setFontSize(float size) {
        return createFooter(null, size, null);
    }

    // Set Only Font Color
    public static FooterBean setFontColor(String color) {
        return createFooter(color, null, null);
    }

    // Set Only Font Family
    public static FooterBean setFontFamily(FontFamilyType family) {
        return createFooter(null, null, family);
    }

    // Set Font Size & Color
    public static FooterBean setFontSizeAndColor(float size, String color) {
        return createFooter(color, size, null);
    }

    // Set Font Size & Font Family
    public static FooterBean setFontSizeAndFamily(float size, FontFamilyType family) {
        return createFooter(null, size, family);
    }

    // Set Font Color & Font Family
    public static FooterBean setFontColorAndFamily(String color, FontFamilyType family) {
        return createFooter(color, null, family);
    }

    // ✅ **4️⃣ Fully Customizable Footer Configuration**
    public static FooterBean customizeFooter(String color, float size, FontFamilyType family) {
        return createFooter(color, size, family);
    }
}
