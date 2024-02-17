package ToMist.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CounselDTO {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  public static class Request{  // 상담 요청

    private String name;

    private String cellPhone;

    private String email;

    private String memo;

    private String address;

    private String addressDetail;

    private String zipcode;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response{ //  응답: 값이 정상적으로 저장이 되었는지 정의

    private Long counselId;

    private String name;

    private String cellPhone;

    private String email;

    private String memo;

    private String address;

    private String addressDetail;

    private String zipcode;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;
  }
}
