package report_utility.beans.charts;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemStyle {
    @Builder.Default
    private String color = "#000000";
}
