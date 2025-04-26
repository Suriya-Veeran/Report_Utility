package report_utility.enums;

import lombok.Getter;

@Getter
public enum JobStatusEnum {
  SUCCESS("Success"),
  FAILURE("Failed"),
  FAILURE_WITH_ERROR("Failed With Error"),
  WARNING("Warning");

  private String status;

  JobStatusEnum(String status) {
    this.status = status;
  }
}
