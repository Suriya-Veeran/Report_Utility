package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import static report_utility.constants.FontSizeConstants.*;

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
