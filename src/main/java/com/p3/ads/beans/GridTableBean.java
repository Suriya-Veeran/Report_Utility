package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.TableType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.p3.ads.constants.ColorConstants.BLACK_FONT_COLOR;
import static com.p3.ads.constants.ColorConstants.GRAY_FONT_COLOR;
import static com.p3.ads.constants.FontSizeConstants.*;

@Getter
@Setter
@Builder
public class GridTableBean implements ReportBean {

    private String title;

    private float titleFontSize;

    private FontFamilyType titleFontFamily;

    private String titleFontColor;


    private Boolean isEmptySpaceNeededAtFront;
    @Builder.Default
    private Map<String, String> gridValues = new LinkedHashMap<>(); // key -> header and values are values

    private FontFamilyType fontFamilyType;  // font family type like Helvetica , Robotica

    private float headerFontSize; // font size
    private float valueFontSize; // font size

    private String fontColor; // font color


    private JobStatusInputBean jobStatusInputBean; // jobStatusInputBean

    private Boolean isJobStatusInclusion; // jobStatusInclusion based on jobStatus Table

    private TableType tableType;  // Table Type -> Header , Summary

    private float[] columnWidths;
    private Boolean startAtNewPage;


    public static final GridTableBean DEFAULT_CONFIG = GridTableBean.builder()
            .title("")
            .columnWidths(new float[]{450L, 450L, 450L})
            .titleFontSize(THIRTEEN_FONT_SIZE)
            .titleFontFamily(FontFamilyType.ROBOTO_BOLD)
            .titleFontColor(GRAY_FONT_COLOR)
            .gridValues(new LinkedHashMap<>()) // Empty default map
            .fontFamilyType(FontFamilyType.ROBOTO_MEDIUM) // Default font
            .headerFontSize(EIGHT_FONT_SIZE) // Default font size
            .valueFontSize(TEN_FONT_SIZE)
            .fontColor(BLACK_FONT_COLOR) // Default black color
            .isJobStatusInclusion(false) // Default jobStatus Table as false
            .tableType(TableType.HEADER) // Table Type -> Header
            .jobStatusInputBean(JobStatusInputBean.DEFAULT_CONFIG) // Default job status config
            .isEmptySpaceNeededAtFront(false)
            .startAtNewPage(false)
            .build();


}
