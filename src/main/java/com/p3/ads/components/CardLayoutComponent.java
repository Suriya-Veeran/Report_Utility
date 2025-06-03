package com.p3.ads.components;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.p3.ads.beans.CardLayoutBean;
import com.p3.ads.beans.CardValueBean;
import com.p3.ads.core.interfaces.ReportComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.p3.ads.constants.ColorConstants.*;
import static com.p3.ads.constants.CommonConstants.*;
import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.drawDivider;
import static com.p3.ads.utils.CommonUtils.loadFont;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardLayoutComponent implements ReportComponent {

    private CardLayoutBean inputBean;


    @Override
    public void render(Document document) throws IOException {

        inputBean = mergeWithDefaults(inputBean);

        renderGridTable(document, inputBean);
    }

    private void renderGridTable(Document document,
                                 CardLayoutBean inputBean) throws IOException {


        document.add(new Paragraph());
        Table table = new Table(UnitValue.createPercentArray(inputBean.getColumnWidths()));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setKeepTogether(false);
        table.setMarginLeft(-20f);
        table.setMarginRight(-20f);
        table.setMarginTop(-2.2f);
        table.setPadding(0);
        table.setBorder(new SolidBorder(hexaDecimalToRGB(WHITE_FONT_COLOR), BORDER_WIDTH));
        table.setBackgroundColor(hexaDecimalToRGB(WHITE_FONT_COLOR));


        if (inputBean.getVauleList() != null && !inputBean.getVauleList().isEmpty()) {
            setCellValues(table, inputBean.getVauleList(), inputBean);
        }
        table.setSkipLastFooter(true);
        document.add(table);
        document.add(new Paragraph());
        document.add(new Paragraph());
        drawDivider(document, MARGIN_LEFT, MARGIN_RIGHT, LINE_WIDTH_1L, DIVIDER_GRAY_COLOR);

    }

    private void setCellValues(Table table, List<CardValueBean> vauleList, CardLayoutBean inputBean) throws IOException {


        int i=1;
        for (CardValueBean cardValueBean : vauleList) {

        PdfFont headerFont = loadFont(cardValueBean.getHeaderFontFamily().getValue());
        PdfFont bodyFont = loadFont(cardValueBean.getBodyFontFamily().getValue());
        PdfFont subBodyFont = loadFont(cardValueBean.getSubBodyFontFamily().getValue());
        PdfFont footerFont = loadFont(cardValueBean.getFooterFontFamily().getValue());

        if(i%2==0) {
            Cell cell = new Cell()
                    .add(new Div())
                    .add(new Div())
                    .add(new Div())
                    .add(new Div())
                    .setBorder(Border.NO_BORDER)
                    .setVerticalAlignment(VerticalAlignment.TOP);
            table.addCell(cell);
            table.setMarginBottom(5f);

        }
        else {
            Cell cell =
                    new Cell()
                            .add(
                                    new Div()
                                            .setWidth(UnitValue.createPercentValue(100))
                                            .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getHeaderBackgroundColor()))
                                            .setBorderTopLeftRadius(new BorderRadius(4f))
                                            .setBorderTopRightRadius(new BorderRadius(4f))
                                            .setBorderBottomLeftRadius(new BorderRadius(0f))
                                            .setBorderBottomRightRadius(new BorderRadius(0f))
                                            .add(new Paragraph(new Text(cardValueBean.getHeader()))
                                                    .setFont(headerFont)
                                                    .setBorder(Border.NO_BORDER)
                                                    .setFontColor(hexaDecimalToRGB(BLACK_FONT_COLOR))
                                                    .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getHeaderBackgroundColor()))
                                                    .setFontSize(cardValueBean.getHeaderFontSize())
                                                    .setTextAlignment(TextAlignment.LEFT))
                                            .setPaddingTop(-1)
                                            .setPaddingLeft(10)
                                            .setPaddingBottom(-1))


                            .add(
                                    new Paragraph(new Text(cardValueBean.getBody()))
                                            .setFont(bodyFont)
                                            .setPaddingTop(-5)
                                            .setFontColor(hexaDecimalToRGB(BLACK_FONT_COLOR))
                                            .setBorder(Border.NO_BORDER)
                                            .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getBodyBackgroundColor()))
                                            .setFontSize(cardValueBean.getBodyFontSize())
                                            .setTextAlignment(TextAlignment.CENTER))
                            .add(
                                    new Paragraph(new Text(cardValueBean.getSubBody()))
                                            .setFont(subBodyFont)
                                            .setFontColor(hexaDecimalToRGB(BLACK_FONT_COLOR))
                                            .setBorder(Border.NO_BORDER)
                                            .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getSubBodyBackgroundColor()))
                                            .setFontSize(cardValueBean.getSubBodyFontSize())
                                            .setPaddingTop(-8)
                                            .setPaddingBottom(5)
                                            .setTextAlignment(TextAlignment.CENTER))
                            .add(
                                    new Div()
                                            .setWidth(UnitValue.createPercentValue(100))
                                            .setBorderTopLeftRadius(new BorderRadius(0f))
                                            .setBorderTopRightRadius(new BorderRadius(0f))
                                            .setBorderBottomLeftRadius(new BorderRadius(4f))
                                            .setBorderBottomRightRadius(new BorderRadius(4f)).setPadding(10)
                                            .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getFooterBackgroundColor()))
                                            .add(new Paragraph(new Text(cardValueBean.getFooter()))
                                                    .setFont(footerFont)
                                                    .setFontColor(hexaDecimalToRGB(BLACK_FONT_COLOR))
                                                    .setBorder(Border.NO_BORDER)
                                                    .setBackgroundColor(hexaDecimalToRGB(cardValueBean.getFooterBackgroundColor()))
                                                    .setFontSize(cardValueBean.getFooterFontSize())
                                                    .setTextAlignment(TextAlignment.LEFT))
                                            .setPaddingTop(-1)
                                            .setPaddingBottom(-1))
                            .setBorder(Border.NO_BORDER)
                            .setVerticalAlignment(VerticalAlignment.TOP);
            table.addCell(cell);
            table.setMarginBottom(5f);
         }
            i++;
        }
    }


    private CardLayoutBean mergeWithDefaults(CardLayoutBean cardLayoutBean) {
        if (cardLayoutBean == null) {
            return CardLayoutBean.DEFAULT_CONFIG;
        }

        return CardLayoutBean.builder()
                .fontFamilyType(cardLayoutBean.getFontFamilyType() != null ? cardLayoutBean.getFontFamilyType() : cardLayoutBean.DEFAULT_CONFIG.getFontFamilyType())
                .fontSize(cardLayoutBean.getFontSize() != 0 ? cardLayoutBean.getFontSize() : cardLayoutBean.DEFAULT_CONFIG.getFontSize())
                .fontColor(cardLayoutBean.getFontColor() != null ? cardLayoutBean.getFontColor() : cardLayoutBean.DEFAULT_CONFIG.getFontColor())
                .columnWidths(cardLayoutBean.getColumnWidths() != null ? cardLayoutBean.getColumnWidths() : cardLayoutBean.DEFAULT_CONFIG.getColumnWidths())
                .vauleList(mergeWithDefaults(inputBean.getVauleList()))
                .build();
    }

    private List<CardValueBean> mergeWithDefaults(List<CardValueBean> valueList) {
        List<CardValueBean> mergedList = new ArrayList<>();
        for (CardValueBean bean : valueList) {
            mergedList.add(CardValueBean.builder()
                    .header(bean.getHeader() != null ? bean.getHeader() : CardValueBean.DEFAULT_CONFIG.getHeader())
                    .headerFontColor(bean.getHeaderFontColor() != null ? bean.getHeaderFontColor() : CardValueBean.DEFAULT_CONFIG.getHeaderFontColor())
                    .headerFontFamily(bean.getHeaderFontFamily() != null ? bean.getHeaderFontFamily() : CardValueBean.DEFAULT_CONFIG.getHeaderFontFamily())
                    .headerFontSize(bean.getHeaderFontSize() != 0 ? bean.getHeaderFontSize() : CardValueBean.DEFAULT_CONFIG.getHeaderFontSize())
                    .headerBackgroundColor(bean.getHeaderBackgroundColor() != null ? bean.getHeaderBackgroundColor() : CardValueBean.DEFAULT_CONFIG.getHeaderBackgroundColor())

                    .body(bean.getBody() != null ? bean.getBody() : CardValueBean.DEFAULT_CONFIG.getBody())
                    .bodyFontColor(bean.getBodyFontColor() != null ? bean.getBodyFontColor() : CardValueBean.DEFAULT_CONFIG.getBodyFontColor())
                    .bodyFontFamily(bean.getBodyFontFamily() != null ? bean.getBodyFontFamily() : CardValueBean.DEFAULT_CONFIG.getBodyFontFamily())
                    .bodyFontSize(bean.getBodyFontSize() != 0 ? bean.getBodyFontSize() : CardValueBean.DEFAULT_CONFIG.getBodyFontSize())
                    .bodyBackgroundColor(bean.getBodyBackgroundColor() != null ? bean.getBodyBackgroundColor() : CardValueBean.DEFAULT_CONFIG.getBodyBackgroundColor())

                    .subBody(bean.getSubBody() != null ? bean.getSubBody() : CardValueBean.DEFAULT_CONFIG.getSubBody())
                    .subBodyFontColor(bean.getSubBodyFontColor() != null ? bean.getSubBodyFontColor() : CardValueBean.DEFAULT_CONFIG.getSubBodyFontColor())
                    .subBodyFontFamily(bean.getSubBodyFontFamily() != null ? bean.getSubBodyFontFamily() : CardValueBean.DEFAULT_CONFIG.getSubBodyFontFamily())
                    .subBodyFontSize(bean.getSubBodyFontSize() != 0 ? bean.getSubBodyFontSize() : CardValueBean.DEFAULT_CONFIG.getSubBodyFontSize())
                    .subBodyBackgroundColor(bean.getSubBodyBackgroundColor() != null ? bean.getSubBodyBackgroundColor() : CardValueBean.DEFAULT_CONFIG.getSubBodyBackgroundColor())

                    .footer(bean.getFooter() != null ? bean.getFooter() : CardValueBean.DEFAULT_CONFIG.getFooter())
                    .footerFontColor(bean.getFooterFontColor() != null ? bean.getFooterFontColor() : CardValueBean.DEFAULT_CONFIG.getFooterFontColor())
                    .footerFontFamily(bean.getFooterFontFamily() != null ? bean.getFooterFontFamily() : CardValueBean.DEFAULT_CONFIG.getFooterFontFamily())
                    .footerFontSize(bean.getFooterFontSize() != 0 ? bean.getFooterFontSize() : CardValueBean.DEFAULT_CONFIG.getFooterFontSize())
                    .footerBackgroundColor(bean.getFooterBackgroundColor() != null ? bean.getFooterBackgroundColor() : CardValueBean.DEFAULT_CONFIG.getFooterBackgroundColor())
                    .build());
        }
        return mergedList;
    }

}
