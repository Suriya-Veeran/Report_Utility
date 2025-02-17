package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;
import report_utility.enums.ProductLogo;

@Getter
@Setter
@Builder
public class HeaderBean implements ReportBean {

    private String title;  // title of the report

    private String fontColor; // font color in hexa code

    private float fontSize; // font size

    private FontFamilyType fontFamily;  // font family like Roboto-Regular , Helvetica

    private ProductLogo productLogo; // product logo like ETL , ADS

    public static final HeaderBean DEFAULT_CONFIG = HeaderBean.builder()
            .title("Materialized View Refresh Report")
            .fontColor("#030303")
            .fontSize(16)
            .fontFamily(FontFamilyType.ROBOTO_REGULAR)
            .productLogo(ProductLogo.ARCHON_DATA_STORE)
            .build();

}
