package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.FontSizeConstants.*;
import static com.p3.ads.enums.FontFamilyType.ROBOTO_MEDIUM;

@Getter
@Setter
@Builder
public class CardValueBean implements ReportBean {

    private String header;

    private float headerFontSize;

    private FontFamilyType headerFontFamily;

    private String headerFontColor;

    private String headerBackgroundColor;


    private String body;

    private float bodyFontSize;

    private FontFamilyType bodyFontFamily;

    private String bodyFontColor;

    private String bodyBackgroundColor;

    private String subBody;

    private float subBodyFontSize;

    private FontFamilyType subBodyFontFamily;

    private String subBodyFontColor;

    private String subBodyBackgroundColor;


    private String footer;

    private float footerFontSize;

    private FontFamilyType footerFontFamily;

    private String footerFontColor;

    private String footerBackgroundColor;
    public static final CardValueBean DEFAULT_CONFIG = CardValueBean.builder()
            .header("")
            .headerBackgroundColor(LIGHT_BLUE_CARD_COLOR)
            .headerFontColor(BLACK_FONT_COLOR)
            .headerFontFamily(ROBOTO_MEDIUM)
            .headerFontSize(TEN_FONT_SIZE)
            .body("")
            .bodyBackgroundColor(LIGHT_BLUE_CARD_COLOR)
            .bodyFontColor(BLACK_FONT_COLOR)
            .bodyFontFamily(ROBOTO_MEDIUM)
            .bodyFontSize(ELEVEN_FONT_SIZE)
            .subBody("")
            .subBodyBackgroundColor(LIGHT_BLUE_CARD_COLOR)
            .subBodyFontColor(BLACK_FONT_COLOR)
            .subBodyFontFamily(ROBOTO_MEDIUM)
            .subBodyFontSize(PADDING_SEVEN_FONT_SIZE)
            .footer("")
            .footerBackgroundColor(DARK_BLUE_CARD_COLOR)
            .footerFontColor(BLACK_FONT_COLOR)
            .footerFontFamily(ROBOTO_MEDIUM)
            .footerFontSize(EIGHT_FONT_SIZE)
            .build();


}
