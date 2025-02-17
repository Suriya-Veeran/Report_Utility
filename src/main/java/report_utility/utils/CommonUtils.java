package report_utility.utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.experimental.UtilityClass;

import java.io.IOException;

import static report_utility.utils.ColorUtils.hexaDecimalToRGB;

@UtilityClass
public class CommonUtils {

    public static PdfFont loadFont(String fontName) throws IOException {
        PdfFont font = null;
        if (fontName.startsWith("Roboto")) {
            font = PdfFontFactory.createFont(fontProgram(fontName),
                    PdfEncodings.IDENTITY_H,
                    PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        } else {
            font = PdfFontFactory.createFont(fontName);
        }

        return font;

    }

    private static String fontProgram(String fontName) {
        switch (fontName.toUpperCase()) {
            case "ROBOTO-REGULAR":
                return "src/main/resources/fonts/Roboto-Regular.ttf";
            case "ROBOTO-BOLD":
                return "src/main/resources/fonts/Roboto-Bold.ttf";
            case "ROBOTO-ITALIC":
                return "src/main/resources/fonts/Roboto-Italic.ttf";
            case "ROBOTO-BOLDITALIC":
                return "src/main/resources/fonts/Roboto-BoldItalic.ttf";
            case "ROBOTO-BLACK":
                return "src/main/resources/fonts/Roboto-Black.ttf";
            case "ROBOTO-BLACKITALIC":
                return "src/main/resources/fonts/Roboto-BlackItalic.ttf";
            case "ROBOTO-LIGHT":
                return "src/main/resources/fonts/Roboto-Light.ttf";
            case "ROBOTO-LIGHTITALIC":
                return "src/main/resources/fonts/Roboto-LightItalic.ttf";
            case "ROBOTO-MEDIUM":
                return "src/main/resources/fonts/Roboto-Medium.ttf";
            case "ROBOTO-MEDIUMITALIC":
                return "src/main/resources/fonts/Roboto-MediumItalic.ttf";
            case "ROBOTO-THIN":
                return "src/main/resources/fonts/Roboto-Thin.ttf";
            case "ROBOTO-THINITALIC":
                return "src/main/resources/fonts/Roboto-ThinItalic.ttf";
            default:
                throw new IllegalArgumentException("Unsupported font name: " + fontName);
        }
    }

    public static void addEmptyLines(int numberOfPages, Document document) {
        for (int i = 0; i < numberOfPages; i++) {
            document.add(new Paragraph(""));
        }
    }

    public static void drawDivider(Document document, float marginLeft, float marginRight,
                                   float lineWidth,
                                   String color) {
        SolidLine solidLine = new SolidLine(lineWidth);
        solidLine.setColor(hexaDecimalToRGB(color));
        LineSeparator lineSeparator = new LineSeparator(solidLine);
        lineSeparator.setWidth(UnitValue.createPointValue(PageSize.A4.getWidth() + marginLeft + marginRight));
        lineSeparator.setMarginLeft(marginLeft);
        lineSeparator.setMarginRight(marginRight);
        document.add(lineSeparator);
    }

    public static void drawDivider(Document document, float lineWidth, String color, String type) {
        SolidLine solidLine = new SolidLine(lineWidth);
        solidLine.setColor(hexaDecimalToRGB(color));
        LineSeparator lineSeparator = new LineSeparator(solidLine);
        float fullWidth = PageSize.A4.getWidth();
        lineSeparator.setWidth(UnitValue.createPointValue(fullWidth));
        lineSeparator.setMarginLeft(-36);
        if (type.equalsIgnoreCase("Header")) {
            lineSeparator.setMarginTop(5);
            lineSeparator.setMarginBottom(1);
        }
        else {
            lineSeparator.setMarginTop(-15);
            lineSeparator.setMarginBottom(0);
        }
        document.add(new Paragraph().setMargin(0).setPadding(0).add(lineSeparator));
    }

  public static void drawDivider(Document document, float lineWidth, String color, String type, int pageIndex, float yPosition) {
    SolidLine solidLine = new SolidLine(lineWidth);
    solidLine.setColor(hexaDecimalToRGB(color));
    LineSeparator lineSeparator = new LineSeparator(solidLine);

    float fullWidth = PageSize.A4.getWidth();
    lineSeparator.setWidth(UnitValue.createPointValue(fullWidth));

    document.showTextAligned(new Paragraph().add(lineSeparator),
            fullWidth / 2, yPosition, pageIndex,
            TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
  }

}
