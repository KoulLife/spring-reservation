package ToMist.reservation.service;

import ToMist.reservation.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
  Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

  List<String> getAllRoomTypes();

  List<Room> getAllRooms();

  byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException;

  void deleteRoom(Long roomId);

  Room updateRoom(long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

  Optional<Room> getRoomById(Long roomId);
}
