package report_utility.beans;

import lombok.Builder;
import lombok.Data;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

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
          .fontFamily(FontFamilyType.ROBOTO_REGULAR)
          .build();
}
