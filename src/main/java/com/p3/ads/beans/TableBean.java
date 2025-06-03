package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.FontSizeConstants.PADDING_TWELVE_FONT_SIZE;

@Getter
@Setter
@Builder
public class TableBean implements ReportBean {

  private String title;

  private List<String> headers;

  private List<List<String>> values;

  private float headerFontSize;

  private float cellFontSize;

  private FontFamilyType headerFontFamily;

  private FontFamilyType cellFontFamily;

  private float borderRadius;

  private String headerBackgroundColor;

  private String valueBackgroundColor;

  private String headerValueFontColor;

  private String valueFontColor;

  private String successFontColor;

  private String drawDividerNeed;
  private String drawDividerNeedAtBottom;

  private String emptyLineNeedAtFront;

  private String errorFontColor;

  private Boolean hasPercentageColorHighlight;

  private String percentageColorHighlight;

  public static final TableBean DEFAULT_CONFIG =
      TableBean.builder()
          .title("")
          .headerFontSize(PADDING_TWELVE_FONT_SIZE)
          .cellFontSize(PADDING_TWELVE_FONT_SIZE)
          .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .cellFontFamily(FontFamilyType.ROBOTO_REGULAR)
          .borderRadius(3f)
          .headers(new ArrayList<>())
          .values(new ArrayList<>())
          .headerBackgroundColor(LIGHT_BLUE_CARD_COLOR)
          .headerValueFontColor(BLACK_FONT_COLOR)
          .valueBackgroundColor(WHITE_FONT_COLOR)
          .valueFontColor(BLACK_FONT_COLOR)
              .hasPercentageColorHighlight(Boolean.FALSE)
              .drawDividerNeed("true")
              .emptyLineNeedAtFront("true")
              .drawDividerNeedAtBottom("false")
          .successFontColor("007D2B")
          .errorFontColor("D60000")
          .build();
}
