package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class TableBean implements ReportBean {

  private String title;

  private List<String> headers;

  private List<List<String>> values;

  private FontFamilyType fontFamily;

  private float fontSize;

  private String headerBackgroundColor;

  private String valueBackgroundColor;

  private String headerValueFontColor;

  private String valueFontColor;

  private String successFontColor;

  private String errorFontColor;

  public static final TableBean DEFAULT_CONFIG =
      TableBean.builder()
          .title("")
          .fontFamily(FontFamilyType.ROBOTO_REGULAR)
          .fontSize(10)
          .headers(new ArrayList<>())
          .values(new ArrayList<>())
          .headerBackgroundColor("DFEAFF")
          .headerValueFontColor("000000")
          .valueBackgroundColor("FFFFFF")
          .valueFontColor("000000")
          .successFontColor("0072DB")
          .errorFontColor("D60000")
          .build();
}
