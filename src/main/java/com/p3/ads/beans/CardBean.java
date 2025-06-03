package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class CardBean implements ReportBean {

  private String headerFontColor; // for both

  private float headerFontSize; // for both

  private FontFamilyType headerFontFamily; // for both

  private FontFamilyType valueFontFamily; // for both

  private String valueFontColor; // for both

  private float valueFontSize; // for both

  private String cardBackgroundColor; // for both
}
