package report_utility.enums;

import lombok.Getter;

@Getter
public enum JobStatusEnum {
  SUCCESS("Success"),
  FAILURE("Failure"),
  WARNING("Warning");

  private String status;

  JobStatusEnum(String status) {
    this.status = status;
  }
}
