package report_utility.builder;

import lombok.experimental.UtilityClass;
import report_utility.beans.ObjectiveBean;
import report_utility.enums.FontFamilyType;

@UtilityClass
public class ObjectiveConfigurationHelper {

    // ✅ **1️⃣ Get Default Objective Configuration**
    public static ObjectiveBean getDefault() {
        return ObjectiveBean.DEFAULT_CONFIG;
    }

    // ✅ **2️⃣ Create Objective with Custom Title**
    public static ObjectiveBean setTitle(String title) {
        return build(title, null, null, null, null, null);
    }

    // ✅ **3️⃣ Create Objective with Custom Description**
    public static ObjectiveBean setDescription(String description) {
        return build(null, description, null, null, null, null);
    }

    // ✅ **4️⃣ Change Title Font**
    public static ObjectiveBean setTitleFont(FontFamilyType font) {
        return build(null, null, font, null, null, null);
    }

    // ✅ **5️⃣ Change Title Font Size**
    public static ObjectiveBean setTitleFontSize(float size) {
        return build(null, null, null, size, null, null);
    }

    // ✅ **6️⃣ Change Description Font**
    public static ObjectiveBean setDescriptionFont(FontFamilyType font) {
        return build(null, null, null, null, font, null);
    }

    // ✅ **7️⃣ Change Description Font Size**
    public static ObjectiveBean setDescriptionFontSize(float size) {
        return build(null, null, null, null, null, size);
    }

    // ✅ **8️⃣ Change Both Title & Description**
    public static ObjectiveBean setTitleAndDescription(String title, String description) {
        return build(title, description, null, null, null, null);
    }

    // ✅ **9️⃣ Fully Customizable Configuration**
    public static ObjectiveBean customize(
            String title,
            String description,
            FontFamilyType titleFont,
            float titleSize,
            FontFamilyType descFont,
            float descSize) {
        return build(title, description, titleFont, titleSize, descFont, descSize);
    }

    // 🔥 **Private Utility Method** (Avoids Code Duplication)
    private static ObjectiveBean build(
            String title,
            String description,
            FontFamilyType titleFont,
            Float titleSize,
            FontFamilyType descFont,
            Float descSize) {
        return ObjectiveBean.builder()
                .title(title != null ? title : ObjectiveBean.DEFAULT_CONFIG.getTitle())
                .description(description != null ? description : ObjectiveBean.DEFAULT_CONFIG.getDescription())
                .titleFontFamily(titleFont != null ? titleFont : ObjectiveBean.DEFAULT_CONFIG.getTitleFontFamily())
                .titleFontSize(titleSize != null ? titleSize : ObjectiveBean.DEFAULT_CONFIG.getTitleFontSize())
                .descriptionFontFamily(descFont != null ? descFont : ObjectiveBean.DEFAULT_CONFIG.getDescriptionFontFamily())
                .descriptionFontSize(descSize != null ? descSize : ObjectiveBean.DEFAULT_CONFIG.getDescriptionFontSize())
                .build();
    }
}

