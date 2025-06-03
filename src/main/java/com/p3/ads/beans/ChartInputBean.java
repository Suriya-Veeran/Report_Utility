package com.p3.ads.beans;

import com.p3.ads.beans.charts.DataInfoBean;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
@Builder
public class ChartInputBean implements ReportBean {

  private String chartTitle;

  private int chartFontSize;

  private FontFamilyType chartFontFamily;

  private String chartType;

  private String chartWidth;

  private String chartHeight;

  @Builder.Default
  List<String> legendInfo = new LinkedList<>();

  @Builder.Default
  List<DataInfoBean> dataInfo = new LinkedList<>();

  public static final ChartInputBean DEFAULT_CONFIG = ChartInputBean.builder()
          .chartTitle("Chart")
          .chartFontSize(16)
          .chartFontFamily(FontFamilyType.ROBOTO_REGULAR)
          .chartType("pie")
          .chartWidth("500")
          .chartHeight("400")
          .legendInfo(new LinkedList<>())
          .dataInfo(new LinkedList<>())
          .build();

}
