package com.p3.ads.components;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.p3.ads.beans.TitleBean;
import com.p3.ads.core.interfaces.ReportComponent;
import com.p3.ads.enums.FontFamilyType;

import java.io.IOException;

import static com.p3.ads.constants.ColorConstants.DIVIDER_GRAY_COLOR;
import static com.p3.ads.constants.ColorConstants.GRAY_FONT_COLOR;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.*;
import static com.p3.ads.utils.CommonUtils.addEmptyLines;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleComponent implements ReportComponent {

    private TitleBean inputBean;

    @Override
    public void render(Document document) throws IOException {

        addEmptyLines(1, document);

        if (inputBean.getTitle() != null && !inputBean.getTitle().isEmpty()) {
            document.add(
                    new Paragraph(inputBean.getTitle())
                            .setTextAlignment(TextAlignment.LEFT)
                            .setFontColor(hexaDecimalToRGB(GRAY_FONT_COLOR))
                            .setFontSize(13)
                            .setMarginLeft(MARGIN_LEFT)
                            .setMarginTop(0)
                            .setMarginBottom(0)
                            .setPadding(0)
                            .setFont(loadFont(FontFamilyType.ROBOTO_BOLD.getValue())));

            drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);
        }


    }
}
