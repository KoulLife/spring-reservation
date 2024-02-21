package ToMist.reservation.service;

import ToMist.reservation.domain.Counsel;
import ToMist.reservation.dto.CounselDTO;
import ToMist.reservation.dto.CounselDTO.Response;
import ToMist.reservation.dto.CounselDTO.Request;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

  //엔티티를 DTO로, DTO를 엔티티로 매핑
  private final ModelMapper modelMapper;

  @Override
  public Response create(Request request) {
    Counsel counsel = modelMapper.map(request, Counsel.class);
    counsel.setAppliedAt(LocalDateTime.now());

    return null;
  }
}
