package report_utility.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;

public class RoundedBorderCellRenderer extends CellRenderer {
    private final Color borderColor;
    private final float borderWidth;
    private final float borderRadius;

    public RoundedBorderCellRenderer(com.itextpdf.layout.element.Cell modelElement, Color borderColor, float borderWidth, float borderRadius) {
        super(modelElement);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        this.borderRadius = borderRadius;
    }

    @Override
    public void drawBorder(DrawContext drawContext) {
        Rectangle rectangle = getOccupiedAreaBBox();
        float x = rectangle.getX();
        float y = rectangle.getY();
        float width = rectangle.getWidth();
        float height = rectangle.getHeight();

        PdfCanvas canvas = drawContext.getCanvas();
        canvas.setStrokeColor(borderColor).setLineWidth(borderWidth);

        // Start drawing rounded rectangle using Bézier curves
        canvas.moveTo(x + borderRadius, y)  // Bottom-left start
                .lineTo(x + width - borderRadius, y)
                .curveTo(x + width, y, x + width, y + borderRadius, x + width, y + borderRadius) // Bottom-right curve
                .lineTo(x + width, y + height - borderRadius)
                .curveTo(x + width, y + height, x + width - borderRadius, y + height, x + width - borderRadius, y + height) // Top-right curve
                .lineTo(x + borderRadius, y + height)
                .curveTo(x, y + height, x, y + height - borderRadius, x, y + height - borderRadius) // Top-left curve
                .lineTo(x, y + borderRadius)
                .curveTo(x, y, x + borderRadius, y, x + borderRadius, y) // Bottom-left curve
                .closePathStroke();  // Close path and stroke

    }
}
