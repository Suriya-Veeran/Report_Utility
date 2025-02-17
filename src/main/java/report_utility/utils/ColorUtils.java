package report_utility.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtils {
  public static Color hexaDecimalToRGB(String hexaDecimal) {
    if(hexaDecimal.startsWith("#")) {
      hexaDecimal = hexaDecimal.substring(1);
    }
    int r = Integer.valueOf(hexaDecimal.substring(0, 2), 16);
    int g = Integer.valueOf(hexaDecimal.substring(2, 4), 16);
    int b = Integer.valueOf(hexaDecimal.substring(4, 6), 16);
    return new DeviceRgb(r, g, b);
  }
}
