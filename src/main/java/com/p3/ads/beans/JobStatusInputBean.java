package com.p3.ads.beans;

import com.p3.ads.enums.FontFamilyType;
import com.p3.ads.enums.JobStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.p3.ads.constants.FontSizeConstants.EIGHT_FONT_SIZE;

@Getter
@Setter
@Builder
public class JobStatusInputBean {

    private JobStatusEnum jobStatus; // jobStatus i.e -> success, warning , error

    private String errorMessage; // error message

    private float fontSize; // font size

    private FontFamilyType jobStatusFontFamily; // font Family for success

    private FontFamilyType errorMessageFontFamily;  // font Family for Failure


    public static final JobStatusInputBean DEFAULT_CONFIG = JobStatusInputBean.builder()
            .jobStatus(JobStatusEnum.SUCCESS) // Default status: SUCCESS
            .errorMessage("") // No default error message
            .fontSize(EIGHT_FONT_SIZE) // Default font size
            .jobStatusFontFamily(FontFamilyType.ROBOTO_BOLD_ITALIC)
            .errorMessageFontFamily(FontFamilyType.ROBOTO_REGULAR) // font Family
            .build();

}
