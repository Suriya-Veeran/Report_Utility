package com.p3.ads.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;

public class RoundedTableRenderer extends TableRenderer {

  private final float borderRadius;
  private final Color borderColor;
  private final float borderWidth;
  private final Color backgroundColor;
  private final Color headerBackgroundColor;
  private float headerHeight = 0f;
  public RoundedTableRenderer(
      Table table,
      float borderRadius,
      Color borderColor,
      float borderWidth,
      Color backgroundColor,
      Color headerBackgroundColor,
      float headerHeight) {
    super(table);
    this.headerHeight = headerHeight;
    this.borderRadius = borderRadius;
    this.borderColor = borderColor;
    this.borderWidth = borderWidth;
    this.backgroundColor = backgroundColor;
    this.headerBackgroundColor = headerBackgroundColor;
  }

  @Override
  public void draw(DrawContext drawContext) {

    int pageNumber = getOccupiedArea().getPageNumber();

    PdfCanvas canvas = drawContext.getCanvas();
    Rectangle rect = getOccupiedAreaBBox();

    // Adjusted radius ensures proper fit, especially for the header
    float adjustedRadius =
        Math.min(
            borderRadius,
            headerHeight / 2); // Ensures header fits well within the table's rounded corners

    // Get the table's position and dimensions
    float x = rect.getLeft() - 18;
    float y = rect.getBottom();
    float width = rect.getWidth() + 18 + 18;
    float height = rect.getHeight();
    float top = rect.getTop();
    float headerBottom = top - headerHeight;

    //  Draw Table Background with Rounded Corners
    canvas.saveState();
    canvas.setFillColor(backgroundColor);
    canvas
        .roundRectangle(x, y, width, height, adjustedRadius)
        .fill(); // Rounded corners for table background
    canvas.restoreState();

    //  Draw Header Background with Rounded Corners (Ensures it fits within table's rounded
    // edges)
    canvas.saveState();
    canvas.setFillColor(headerBackgroundColor);
    canvas
        .roundRectangle(x, headerBottom, width, headerHeight, adjustedRadius)
//        .clip()
        .fill(); // Rounded corners for header
    canvas.restoreState();

    //  Draw Table Border (Rounded Corners)
    canvas.saveState();
    canvas
        .setStrokeColor(borderColor)
        .setLineWidth(borderWidth)
        .roundRectangle(x, y, width, height, adjustedRadius) // Apply rounded corners to border
        .stroke();
    canvas.restoreState();

    // Draw the table's content (i.e., the cells) as usual
    super.draw(drawContext);
  }

  @Override
  public TableRenderer getNextRenderer() {
    return new RoundedTableRenderer(
            (Table) getModelElement(),
            borderRadius,
            borderColor,
            borderWidth,
            backgroundColor,
            headerBackgroundColor, headerHeight
    );
  }

}
