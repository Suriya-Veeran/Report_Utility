package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import static report_utility.constants.FontSizeConstants.TEN_FONT_SIZE;

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
