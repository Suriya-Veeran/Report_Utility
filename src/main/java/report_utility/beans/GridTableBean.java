package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;
import report_utility.enums.TableType;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class GridTableBean implements ReportBean {

    private String title;

    private float titleFontSize;

    private FontFamilyType titleFontFamily;

    private String titleFontColor;

    @Builder.Default
    private Map<String, String> gridValues = new LinkedHashMap<>(); // key -> header and values are values

    private FontFamilyType fontFamilyType;  // font family type like Helvetica , Robotica

    private float fontSize; // font size

    private String fontColor; // font color

    private JobStatusInputBean jobStatusInputBean; // jobStatusInputBean

    private Boolean isJobStatusInclusion; // jobStatusInclusion based on jobStatus Table

    private TableType tableType;  // Table Type -> Header , Summary

    public static final GridTableBean DEFAULT_CONFIG = GridTableBean.builder()
            .title("")
            .titleFontSize(13)
            .titleFontFamily(FontFamilyType.ROBOTO_MEDIUM)
            .titleFontColor("030303")
            .gridValues(new LinkedHashMap<>()) // Empty default map
            .fontFamilyType(FontFamilyType.ROBOTO_REGULAR) // Default font
            .fontSize(10) // Default font size
            .fontColor("#000000") // Default black color
            .isJobStatusInclusion(false) // Default jobStatus Table as false
            .tableType(TableType.HEADER) // Table Type -> Header
            .jobStatusInputBean(JobStatusInputBean.DEFAULT_CONFIG) // Default job status config
            .build();


}
