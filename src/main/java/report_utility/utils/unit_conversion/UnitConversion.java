package report_utility.utils.unit_conversion;


import lombok.experimental.UtilityClass;
import report_utility.enums.FormatTypes;

@UtilityClass
public class UnitConversion {

  public static int convertToKb(FormatTypes formatTypes, int value) {

    switch (formatTypes) {
      case GB:
        return value * 1024 * 1024; // GB to KB
      case MB:
        return value * 1024; // MB to KB
      case KB:
        return value; // Already in KB
      default:
        throw new IllegalArgumentException("Unknown file size unit: " + formatTypes.name());
    }
  }
}
