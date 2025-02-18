package report_utility.beans.charts;


import lombok.*;
import report_utility.enums.FormatTypes;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataInfoBean {
    @Builder.Default
    private String name = "Default";
    @Builder.Default
    private int value = 0;
    @Builder.Default
    private FormatTypes format = FormatTypes.KB;
    @Builder.Default
    private ItemStyle itemStyle = new ItemStyle();
}
