package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FooterBean implements ReportBean {

  private String fontColor;

  private float fontSize;

  private FontFamilyType fontFamily;

  public static final FooterBean DEFAULT_CONFIG =
      FooterBean.builder()
          .fontSize(8f)
          .fontColor("#3F3F3F")
          .fontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .build();
}
