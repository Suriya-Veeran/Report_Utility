package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
public class ChartCreationConfig implements ReportBean {

  private String title;

  private FontFamilyType titleFontFamily;

  private float titleFontSize;

  private float imageWidth;

  private float imageHeight;

  @Builder.Default private List<ChartInputBean> chartInputBean = new LinkedList<>();

  public static final ChartCreationConfig DEFAULT_CONFIG =
      ChartCreationConfig.builder()
          .title("Chart")
          .titleFontFamily(FontFamilyType.ROBOTO_MEDIUM)
          .titleFontSize(13)
          .imageWidth(400)
          .imageHeight(400)
          .chartInputBean(new LinkedList<>())
          .build();
}
