package com.p3.ads.beans;

import com.p3.ads.enums.FontFamilyType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

import static com.p3.ads.constants.ColorConstants.GRAY_FONT_COLOR;
import static com.p3.ads.constants.ColorConstants.LIGHT_BLUE_FONT_COLOR;
import static com.p3.ads.constants.FontSizeConstants.TEN_FONT_SIZE;

@Getter
@Setter
@SuperBuilder
public class SingleCardBean extends CardBean {

  private String header; // for single

  private String generatedTime; // for single

  private String value; // for single

  public static final SingleCardBean DEFAULT_CONFIG =
      SingleCardBean.builder()
          .header("Single Card Default Header")
          .generatedTime(new Date().toString())
          .value("Default Value")
          .headerFontColor(GRAY_FONT_COLOR)
          .headerFontSize(TEN_FONT_SIZE)
          .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .valueFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .valueFontColor(GRAY_FONT_COLOR)
          .valueFontSize(TEN_FONT_SIZE)
          .cardBackgroundColor(LIGHT_BLUE_FONT_COLOR)
          .build();
}
