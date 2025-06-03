package com.p3.ads.beans;


import com.p3.ads.constants.ColorConstants;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.p3.ads.constants.ColorConstants.BLACK_FONT_COLOR;
import static com.p3.ads.constants.FontSizeConstants.*;

@Getter
@Setter
@SuperBuilder
public class MultipleCardBean extends CardBean {

    private String title;

    private FontFamilyType titleFontFamily;

    private float titleFontSize;

    private String titleFontColor;

    @Builder.Default private Map<String, String> values = new LinkedHashMap<>(); // for Multiple

    private String headerValue; // for multiple

    private FontFamilyType headerValueFontFamily; // for multiple

    private float headerValueFontSize; // for multiple

    private String headerValueFontColor;// for multiple

    private String subHeaderValue; // for multiple

    private FontFamilyType subHeaderValueFontFamily; // for multiple

    private float subHeaderValueFontSize; // for multiple

    private String subHeaderValueFontColor;// for multiple

    private Integer valueRowCount;

    private Boolean isTitleDividerNeeded;

    private Boolean isBottomDividerNeeded;

    private float borderRadius;


    public static final MultipleCardBean DEFAULT_CONFIG =
            MultipleCardBean.builder()
                    .title("")
                    .titleFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .titleFontColor(ColorConstants.BLACK_FONT_COLOR)
                    .titleFontSize(THIRTEEN_FONT_SIZE)
                    .values(new LinkedHashMap<>())
                    .valueRowCount(3)
                    .headerValue("")
                    .subHeaderValue("")
                    .subHeaderValueFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .subHeaderValueFontSize(TEN_FONT_SIZE)
                    .subHeaderValueFontColor(BLACK_FONT_COLOR)
                    .headerValueFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .headerValueFontSize(PADDING_TWELVE_FONT_SIZE)
                    .headerValueFontColor(BLACK_FONT_COLOR)
                    .headerFontColor(BLACK_FONT_COLOR)
                    .headerFontSize(EIGHT_FONT_SIZE)
                    .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .valueFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .valueFontColor(BLACK_FONT_COLOR)
                    .valueFontSize(TEN_FONT_SIZE)
                    .cardBackgroundColor("E8EDF7")
                    .borderRadius(3f)
                    .isTitleDividerNeeded(false)
                    .isBottomDividerNeeded(false)
                    .build();
}

