package ToMist.reservation.service;

import ToMist.reservation.dto.CounselDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

  private final ModelMapper modelMapper;


  @Override
  public CounselDTO.Response create(CounselDTO.Request request) {
    return null;
  }
}
