package report_utility.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import report_utility.enums.FontFamilyType;

import java.util.Date;

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
          .headerFontColor("030303")
          .headerFontSize(10)
          .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .valueFontFamily(FontFamilyType.ROBOTO_REGULAR)
          .valueFontColor("030303")
          .valueFontSize(10)
          .cardBackgroundColor("DFEAFF")
          .build();
}
