package com.p3.ads.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;

import static com.p3.ads.constants.FontSizeConstants.TEN_FONT_SIZE;

@Getter
@Setter
@Builder
public class TitleBean implements ReportBean {
    private String title;
    private FontFamilyType fontFamily;
    private float fontSize;


    public static final TitleBean DEFAULT_CONFIG =
            TitleBean.builder()
                    .title("Chain Of Custody Summary")
                    .fontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .fontSize(TEN_FONT_SIZE)
                    .build();
}
