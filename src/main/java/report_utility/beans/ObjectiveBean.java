package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

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
                    .titleFontFamily(FontFamilyType.ROBOTO_MEDIUM)
                    .descriptionFontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .titleFontSize(13)
                    .descriptionFontSize(10)
                    .build();

}
