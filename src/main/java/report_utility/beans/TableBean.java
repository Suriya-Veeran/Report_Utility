package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.core.interfaces.ReportBean;
import report_utility.enums.FontFamilyType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class TableBean implements ReportBean {

    private String title;

    private List<String> headers;

    private List<List<String>> values;

    private FontFamilyType fontFamily;

    private float fontSize;

    public static final TableBean DEFAULT_CONFIG =
            TableBean.builder()
                    .title("")
                    .fontFamily(FontFamilyType.ROBOTO_REGULAR)
                    .fontSize(12)
                    .headers(new ArrayList<>())
                    .values(new ArrayList<>())
                    .build();


}
