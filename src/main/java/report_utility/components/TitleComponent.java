package report_utility.components;

import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import report_utility.beans.TitleBean;
import report_utility.core.interfaces.ReportComponent;
import report_utility.enums.FontFamilyType;

import java.awt.*;
import java.io.IOException;

import static report_utility.constants.ColorConstants.DIVIDER_GRAY_COLOR;
import static report_utility.constants.ColorConstants.GRAY_FONT_COLOR;
import static report_utility.constants.CommonConstants.*;
import static report_utility.utils.ColorUtils.hexaDecimalToRGB;
import static report_utility.utils.CommonUtils.*;
import static report_utility.utils.CommonUtils.addEmptyLines;

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
