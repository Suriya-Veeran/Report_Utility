package com.p3.ads.components;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.p3.ads.beans.CocCardBean;
import com.p3.ads.constants.ColorConstants;
import com.p3.ads.core.interfaces.ReportComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static com.p3.ads.utils.ColorUtils.hexaDecimalToRGB;
import static com.p3.ads.utils.CommonUtils.loadFont;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocCardComponent implements ReportComponent {

    private CocCardBean inputBean;
    @Override
    public void render(Document document) throws IOException {

        inputBean=mergeWithDefault(inputBean);

//        addEmptyLines(1, document);


        Table headerCard=new Table(UnitValue.createPercentArray(new float[]{1000L}));
        headerCard.setWidth(PageSize.A4.getWidth()-35);
        headerCard.setMarginLeft(-18);
        headerCard.setMarginTop(25);
        headerCard.setKeepTogether(false);
        headerCard.setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()));
        headerCard.setBorder(Border.NO_BORDER);


        Paragraph paragraph=new Paragraph();
        paragraph
                .setFont(loadFont(inputBean.getHeaderFontFamily().getValue()))
                .setMultipliedLeading(1)
                .setTextAlignment(TextAlignment.LEFT);

        paragraph.add(new Text(inputBean.getHeader()).setFontSize(inputBean.getHeaderFontSize()));

        Cell headerCell =
                new Cell(1, 3)
                        .add(paragraph)
                        .add(new Paragraph(inputBean.getSubheader().trim()).setFontSize(inputBean.getHeaderFontSize() -3.5f))
                        .setPadding(5)
                        .setPaddingTop(4)
                        .setPaddingBottom(4)
                        .setBorder(Border.NO_BORDER)
                        .setBackgroundColor(hexaDecimalToRGB(inputBean.getCardBackgroundColor()));

        headerCard.addCell(headerCell);
        document.add(headerCard);

        Table contentCard=new Table(UnitValue.createPercentArray(new float[]{1000L}));
        contentCard.setWidth(PageSize.A4.getWidth()-35);
        contentCard.setMarginLeft(-18);
        contentCard.setMarginTop(9);
        contentCard.setKeepTogether(false);
        contentCard.setBorder(Border.NO_BORDER);

        for(String value : inputBean.getValues())
        {
            Paragraph content = new Paragraph();
            String[] parts = value.split(",", 3);

            content.add(new Text(parts[0])).setFontSize(inputBean.getHeaderFontSize()).setBold();
            content.setMultipliedLeading(1.5f);

            Paragraph secondLineRenderer=colorStatus(parts[1]);

            Cell contentCell=new Cell()
                              .add(content)
                              .add(secondLineRenderer)
                               .add(new Paragraph(parts[2]).setFontSize(inputBean.getHeaderFontSize()))
                               .setBorder(Border.NO_BORDER)
                               .setPadding(4)
                               .setBackgroundColor(hexaDecimalToRGB(ColorConstants.WHITE_FONT_COLOR));

            contentCard.addCell(contentCell);
        }
        document.add(contentCard);

    }

    public  Paragraph colorStatus(String input) {
        Paragraph paragraph = new Paragraph();

        if (input.contains("Success")) {
            paragraph.add(new Text(input.replace("Success", "")));
            paragraph.add(new Text("Success").setFontColor(hexaDecimalToRGB("#008000")));
            paragraph.setFontSize(inputBean.getHeaderFontSize()).setMultipliedLeading(2);
        } else if (input.contains("Failed")) {
            paragraph.add(new Text(input.replace("Failed", "")));
            paragraph.add(new Text("Failed").setFontColor(com.itextpdf.kernel.colors.ColorConstants.RED));
            paragraph.setFontSize(inputBean.getHeaderFontSize()).setMultipliedLeading(2);
        } else {
            paragraph.add(new Text(input));
            paragraph.setFontSize(inputBean.getHeaderFontSize()).setMultipliedLeading(2);
        }

        return paragraph;
    }





    public CocCardBean mergeWithDefault(CocCardBean cocCardBean) {
        if (cocCardBean == null) {
            return CocCardBean.DEFAULT_CONFIG;
        }
        return CocCardBean.builder()
                .header(cocCardBean.getHeader()!=null? cocCardBean.getHeader() : CocCardBean.DEFAULT_CONFIG.getHeader())
                .subheader(cocCardBean.getSubheader()!=null? cocCardBean.getSubheader() : CocCardBean.DEFAULT_CONFIG.getSubheader())
                .values(cocCardBean.getValues()!=null? cocCardBean.getValues(): CocCardBean.DEFAULT_CONFIG.getValues())
                .headerFontSize(cocCardBean.getHeaderFontSize()!=0? cocCardBean.getHeaderFontSize(): CocCardBean.DEFAULT_CONFIG.getHeaderFontSize())
                .headerFontColor(cocCardBean.getHeaderFontColor()!=null? cocCardBean.getHeaderFontColor() : CocCardBean.DEFAULT_CONFIG.getHeaderFontColor())
                .headerFontFamily(cocCardBean.getHeaderFontFamily()!=null? cocCardBean.getHeaderFontFamily():CocCardBean.DEFAULT_CONFIG.getHeaderFontFamily())
                .valueFontSize(cocCardBean.getValueFontSize()!=0? cocCardBean.getValueFontSize(): CocCardBean.DEFAULT_CONFIG.getValueFontSize())
                .valueFontColor(cocCardBean.getValueFontColor()!=null? cocCardBean.getValueFontColor() : CocCardBean.DEFAULT_CONFIG.getValueFontColor())
                .valueFontFamily(cocCardBean.getValueFontFamily()!=null? cocCardBean.getValueFontFamily():CocCardBean.DEFAULT_CONFIG.getValueFontFamily())
                .cardBackgroundColor(cocCardBean.getCardBackgroundColor()!=null? cocCardBean.getCardBackgroundColor() : CocCardBean.DEFAULT_CONFIG.getCardBackgroundColor())
                .build();


    }
}
