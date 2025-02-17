package report_utility.components;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.ObjectiveBean;
import report_utility.core.interfaces.ReportComponent;

import java.io.IOException;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.drawDivider;
import static report_utility.utils.CommonUtils.loadFont;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectiveComponent implements ReportComponent {

    private ObjectiveBean inputBean;

    @Override
    public void render(Document document) throws IOException {
        inputBean = mergeWithDefaults(inputBean);

        if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
            document.add(new Paragraph(inputBean.getTitle())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(hexaDecimalToRGB("030303"))
                    .setFontSize(inputBean.getTitleFontSize())
                    .setMarginLeft(-17)
                            .setMarginTop(0)
                    .setMarginBottom(0)
                            .setPadding(0)
                    .setFont(loadFont(inputBean.getTitleFontFamily().getValue()))
            );
            drawDivider(document, -18, -18, 0.75f, "BCBCBC");
        }

        if (inputBean.getDescription() != null && !inputBean.getDescription().isEmpty()) {
            document.add(new Paragraph(inputBean.getDescription())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(hexaDecimalToRGB("030303"))
                    .setFontSize(inputBean.getDescriptionFontSize())
                    .setMarginLeft(-17)
                    .setPaddingTop(0)
                    .setMarginBottom(0)
                            .setPadding(0)
                    .setFont(loadFont(inputBean.getDescriptionFontFamily().getValue()))
            );
        }


    }

    private ObjectiveBean mergeWithDefaults(ObjectiveBean inputBean) {
        if (inputBean == null) {
            return ObjectiveBean.DEFAULT_CONFIG;
        }

        return ObjectiveBean.builder()
                .title(inputBean.getTitle() != null ? inputBean.getTitle() : ObjectiveBean.DEFAULT_CONFIG.getTitle())
                .description(inputBean.getDescription() != null ? inputBean.getDescription() : ObjectiveBean.DEFAULT_CONFIG.getDescription())
                .titleFontFamily(inputBean.getTitleFontFamily() != null ? inputBean.getTitleFontFamily() : ObjectiveBean.DEFAULT_CONFIG.getTitleFontFamily())
                .descriptionFontFamily(inputBean.getDescriptionFontFamily() != null ? inputBean.getDescriptionFontFamily() : ObjectiveBean.DEFAULT_CONFIG.getDescriptionFontFamily())
                .titleFontSize(inputBean.getTitleFontSize() != 0 ? inputBean.getTitleFontSize() : ObjectiveBean.DEFAULT_CONFIG.getTitleFontSize())
                .descriptionFontSize(inputBean.getDescriptionFontSize() != 0 ? inputBean.getDescriptionFontSize() : ObjectiveBean.DEFAULT_CONFIG.getDescriptionFontSize())
                .build();

    }
}
