package ToMist.reservation.service;

import ToMist.reservation.exception.ResouceNotFoundException;
import ToMist.reservation.model.Room;
import ToMist.reservation.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{
  private final RoomRepository roomRepository;

  @Override
  public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
    Room room = new Room();
    room.setRoomType(roomType);
    room.setRoomPrice(roomPrice);
    if(!file.isEmpty()){
      byte[] photoBytes = file.getBytes();
      Blob photoBlob = new SerialBlob(photoBytes);
      room.setPhoto(photoBlob);
    }

    return roomRepository.save(room);
  }

  @Override
  public List<String> getAllRoomTypes() {
    return roomRepository.findDistinctRoomTypes();
  }

  @Override
  public List<Room> getAllRooms() {
    return roomRepository.findAll();
  }

  // roomId로 방 정보를 가져온다.
  // 방 정보가 없으면 예외 메세지 날리기.
  // 방 정보가 있다면 사진의 byte 리턴하기.
  @Override
  public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
    Optional<Room> theRoom = roomRepository.findById(roomId);
    if(theRoom.isEmpty()){
      throw new ResouceNotFoundException("Sorry, Room not found");
    }
    Blob photoBlob = theRoom.get().getPhoto();
    if(photoBlob != null){
      return photoBlob.getBytes(1, (int) photoBlob.length());
    }
    return null;
  }

  @Override
  public void deleteRoom(Long roomId) {
    Optional<Room> theRoom = roomRepository.findById(roomId);
    if (theRoom.isPresent()){
      roomRepository.deleteById(roomId);
    }
  }

  @Override
  public Room updateRoom(long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
    Room room = roomRepository.findById(roomId).get();
    if (roomType != null) room.setRoomType(roomType);
    if (roomPrice != null) room.setRoomPrice(roomPrice);
    if (photoBytes != null && photoBytes.length > 0){
      try {
        room.setPhoto(new SerialBlob(photoBytes));
      } catch (SerialException e) {
        throw new RuntimeException(e);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    return roomRepository.save(room);
  }

  @Override
  public Optional<Room> getRoomById(Long roomId) {
    return Optional.of(roomRepository.findById(roomId).get());
  }
}
