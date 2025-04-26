package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import java.util.ArrayList;
import java.util.List;

import static report_utility.constants.ColorConstants.*;
import static report_utility.constants.ColorConstants.BLACK_FONT_COLOR;
import static report_utility.constants.FontSizeConstants.TEN_FONT_SIZE;

@Getter
@Setter
@Builder
public class TableBeanForPurge implements ReportBean {
    private String title;

    private List<String> headers;

    private List<List<String>> values;

    private FontFamilyType fontFamily;

    private float fontSize;

    private String headerBackgroundColor;

    private String valueBackgroundColor;

    private String headerValueFontColor;

    private String valueFontColor;

    private String successFontColor;

    private String errorFontColor;

    public static final TableBeanForPurge DEFAULT_CONFIG =
            TableBeanForPurge.builder()
                    .title("")
                    .fontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .fontSize(TEN_FONT_SIZE)
                    .headers(new ArrayList<>())
                    .values(new ArrayList<>())
                    .headerBackgroundColor(LIGHT_BLUE_FONT_COLOR)
                    .headerValueFontColor(BLACK_FONT_COLOR)
                    .valueBackgroundColor(WHITE_FONT_COLOR)
                    .valueFontColor(BLACK_FONT_COLOR)
                    .successFontColor("007D2B")
                    .errorFontColor("D60000")
                    .build();
}
