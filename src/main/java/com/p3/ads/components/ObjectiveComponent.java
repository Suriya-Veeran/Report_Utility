package com.p3.ads.components;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.p3.ads.beans.ObjectiveBean;
import com.p3.ads.core.interfaces.ReportComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static com.p3.ads.constants.ColorConstants.GRAY_FONT_COLOR;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.drawDivider;
import static com.p3.ads.utils.CommonUtils.loadFont;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectiveComponent implements ReportComponent {

    private ObjectiveBean inputBean;

    @Override
    public void render(Document document) throws IOException {
        inputBean = mergeWithDefaults(inputBean);

//        addEmptyLines(1, document);
        if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
            document.add(new Paragraph(inputBean.getTitle())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
                    .setFontSize(inputBean.getTitleFontSize())
                    .setMarginLeft(MARGIN_LEFT)
                    .setMarginTop(0)
                    .setMarginBottom(0)
                    .setPadding(0)
                    .setFont(loadFont(inputBean.getTitleFontFamily().getValue()))
            );
            drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_ZERO_75F, "BCBCBC");
        }

        if (inputBean.getDescription() != null && !inputBean.getDescription().isEmpty()) {
            document.add(new Paragraph(inputBean.getDescription())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
                    .setFontSize(inputBean.getDescriptionFontSize())
                    .setMarginLeft(MARGIN_LEFT)
                     .setMultipliedLeading(1f)
                     .setMarginTop(MARGIN_TOP)
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
