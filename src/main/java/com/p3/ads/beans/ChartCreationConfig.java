package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

import static com.p3.ads.constants.FontSizeConstants.THIRTEEN_FONT_SIZE;

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
          .titleFontSize(THIRTEEN_FONT_SIZE)
          .imageWidth(400)
          .imageHeight(400)
          .chartInputBean(new LinkedList<>())
          .build();
}
