package com.p3.ads.beans;

import com.p3.ads.core.interfaces.ReportBean;
import com.p3.ads.enums.FontFamilyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.p3.ads.constants.FontSizeConstants.TEN_FONT_SIZE;
import static com.p3.ads.constants.FontSizeConstants.THIRTEEN_FONT_SIZE;

@Getter
@Setter
@Builder
public class ObjectiveBean implements ReportBean {

    private String title;

    private String description;

    private FontFamilyType titleFontFamily;

    private float titleFontSize;

    private FontFamilyType descriptionFontFamily;

    private float descriptionFontSize;

    public static final ObjectiveBean DEFAULT_CONFIG =
            ObjectiveBean.builder()
                    .title("Objective")
                    .description("Description")
                    .titleFontFamily(FontFamilyType.ROBOTO_BOLD)
                    .descriptionFontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .titleFontSize(THIRTEEN_FONT_SIZE)
                    .descriptionFontSize(TEN_FONT_SIZE)
                    .build();

}
