package report_utility.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

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
