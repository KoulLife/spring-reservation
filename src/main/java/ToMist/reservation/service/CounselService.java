package ToMist.reservation.service;

import ToMist.reservation.dto.CounselDTO.Response;
import ToMist.reservation.dto.CounselDTO.Request;

public interface CounselService {
  Response create(Request request);  //  상담 등록 기능
}
