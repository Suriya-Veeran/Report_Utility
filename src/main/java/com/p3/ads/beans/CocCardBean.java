package com.p3.ads.beans;

import com.p3.ads.constants.ColorConstants;
import com.p3.ads.constants.FontSizeConstants;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CocCardBean implements ReportBean {

    private String header;

    private String subheader;

    private List<String>values;

    private float headerFontSize;

    private FontFamilyType headerFontFamily;

    private String headerFontColor;

    private float valueFontSize;

    private FontFamilyType valueFontFamily;

    private String valueFontColor;

    private String cardBackgroundColor;

    public static final CocCardBean DEFAULT_CONFIG=CocCardBean.builder()
            .header("Main Header")
            .subheader("SubHaeder")
            .values(new ArrayList<>())
            .headerFontSize(FontSizeConstants.TEN_FONT_SIZE)
            .headerFontColor(ColorConstants.GRAY_FONT_COLOR)
            .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
            .valueFontFamily(FontFamilyType.ROBOTO_MEDIUM)
            .valueFontColor(ColorConstants.GRAY_FONT_COLOR)
            .valueFontSize(FontSizeConstants.TEN_FONT_SIZE)
            .cardBackgroundColor(ColorConstants.LIGHT_BLUE_FONT_COLOR)
            .build();
}
