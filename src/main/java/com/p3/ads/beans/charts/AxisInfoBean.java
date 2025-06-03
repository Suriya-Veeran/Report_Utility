package com.p3.ads.beans.charts;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AxisInfoBean {
    @Builder.Default
    private String type = "category";
    @Builder.Default
    private List<String> data = new ArrayList<>(); // Only for x-axis
    @Builder.Default
    private TextStyleBean textStyle = new TextStyleBean();
}
