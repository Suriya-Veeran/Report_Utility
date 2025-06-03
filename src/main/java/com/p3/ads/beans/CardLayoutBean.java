package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.p3.ads.constants.ColorConstants.BLACK_FONT_COLOR;
import static com.p3.ads.constants.FontSizeConstants.TEN_FONT_SIZE;

@Getter
@Setter
@Builder
public class CardLayoutBean implements ReportBean {

    @Builder.Default
    private List<CardValueBean> vauleList = new ArrayList<>(); // key -> header and values are values

    private FontFamilyType fontFamilyType;  // font family type like Helvetica , Robotica

    private float fontSize; // font size

    private String fontColor; // font color

    private float[] columnWidths;

    public static final CardLayoutBean DEFAULT_CONFIG = CardLayoutBean.builder()
            .columnWidths(new float[]{200L, 200L, 200L, 200L, 200L})
            .vauleList(new ArrayList<>()) // Empty default map
            .fontFamilyType(FontFamilyType.ROBOTO_MEDIUM) // Default font
            .fontSize(TEN_FONT_SIZE) // Default font size
            .fontColor(BLACK_FONT_COLOR) // Default black color
            .build();


}
