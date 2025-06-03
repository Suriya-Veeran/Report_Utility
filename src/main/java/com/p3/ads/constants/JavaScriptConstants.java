package com.p3.ads.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaScriptConstants {

    public static final String HIDDEN_SCRIPT = "document.body.style.overflow = 'hidden';";

    public static final String HEIGHT_SCRIPT = "document.body.style.height = '100vh';";

    public static final String SCROLL_SCRIPT = "window.scrollTo(0, 0);";

    public static final String RESIZE_SCRIPT = "window.dispatchEvent(new Event('resize'));";

    public static final String CHART_NAME = "chart";

    public static final String CANVAS_NAME = "canvas";

}
