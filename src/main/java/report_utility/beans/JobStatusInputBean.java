package report_utility.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import report_utility.enums.FontFamilyType;
import report_utility.enums.JobStatusEnum;

@Getter
@Setter
@Builder
public class JobStatusInputBean {

    private JobStatusEnum jobStatus; // jobStatus i.e -> success, warning , error

    private String errorMessage; // error message

    private float fontSize; // font size

    private FontFamilyType fontFamily; // font Family


    public static final JobStatusInputBean DEFAULT_CONFIG = JobStatusInputBean.builder()
            .jobStatus(JobStatusEnum.SUCCESS) // Default status: SUCCESS
            .errorMessage("") // No default error message
            .fontSize(8) // Default font size
            .fontFamily(FontFamilyType.ROBOTO_ITALIC) // font Family
            .build();

}
