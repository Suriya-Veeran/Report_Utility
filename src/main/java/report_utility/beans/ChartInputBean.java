package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.beans.charts.DataInfoBean;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

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
