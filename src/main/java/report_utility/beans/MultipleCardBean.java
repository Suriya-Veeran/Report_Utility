package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import report_utility.enums.FontFamilyType;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class MultipleCardBean extends CardBean {

  private String title;

  @Builder.Default private Map<String, String> values = new LinkedHashMap<>(); // for Multiple

  private String headerValue; // for multiple

  private String subHeaderValue; // for multiple

  public static final MultipleCardBean DEFAULT_CONFIG =
      MultipleCardBean.builder()
              .title("")
          .values(new LinkedHashMap<>())
          .headerValue("")
          .subHeaderValue("")
          .headerFontColor("2C2C2C")
          .headerFontSize(10)
          .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .valueFontFamily(FontFamilyType.ROBOTO_REGULAR)
          .valueFontColor("000000")
          .valueFontSize(10)
          .cardBackgroundColor("E8EDF7")
          .build();
}
