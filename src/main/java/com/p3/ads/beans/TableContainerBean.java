package com.p3.ads.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.TableType;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.ColorConstants.BLACK_FONT_COLOR;
import static com.p3.ads.constants.FontSizeConstants.*;
import static com.p3.ads.constants.FontSizeConstants.TEN_FONT_SIZE;

@Getter
@Setter
@Builder
public class TableContainerBean implements ReportBean {
    private GridTableBean gridTable;
    private TableBean table;
    public static final TableContainerBean DEFAULT_CONFIG =
            TableContainerBean.builder()
                    .table(
                            TableBean.builder()
                                    .title("")
                                    .headerFontSize(PADDING_TWELVE_FONT_SIZE)
                                    .cellFontSize(PADDING_TWELVE_FONT_SIZE)
                                    .headerFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                                    .cellFontFamily(FontFamilyType.ROBOTO_REGULAR)
                                    .borderRadius(3f)
                                    .headers(new ArrayList<>())
                                    .values(new ArrayList<>())
                                    .headerBackgroundColor(LIGHT_BLUE_FONT_COLOR)
                                    .headerValueFontColor(BLACK_FONT_COLOR)
                                    .valueBackgroundColor(WHITE_FONT_COLOR)
                                    .valueFontColor(BLACK_FONT_COLOR)
                                    .hasPercentageColorHighlight(Boolean.FALSE)
                                    .drawDividerNeed("true")
                                    .emptyLineNeedAtFront("true")
                                    .drawDividerNeedAtBottom("false")
                                    .successFontColor("007D2B")
                                    .errorFontColor("D60000")
                                    .build()
                    )
                    .gridTable(GridTableBean.builder()
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
                            .build())
                    .build();
}
