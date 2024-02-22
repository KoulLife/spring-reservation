package ToMist.reservation.service;

import ToMist.reservation.model.HRoom;
import ToMist.reservation.repository.HRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class HRoomService implements IRoomService{
  private final HRoomRepository roomRepository;

  @Override
  public HRoom addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
    HRoom room = new HRoom();
    room.setRoomType(roomType);
    room.setRoomPrice(roomPrice);
    if(!file.isEmpty()){
      byte[] photoBytes = file.getBytes();
      Blob photoBlob = new SerialBlob(photoBytes);
      room.setPhoto(photoBlob);
    }

    return roomRepository.save(room);
  }
}
