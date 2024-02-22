package ToMist.reservation.controller;

import ToMist.reservation.model.HRoom;
import ToMist.reservation.response.RoomResponse;
import ToMist.reservation.service.HRoomService;
import ToMist.reservation.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class HRoomController {

  private final HRoomService roomService;

  @PostMapping("/add/new-room")
  public ResponseEntity<RoomResponse> addNewRoom(
          @RequestParam("photo") MultipartFile photo,
          @RequestParam("roomType") String roomType,
          @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
    HRoom saveRoom = roomService.addNewRoom(photo, roomType, roomPrice);
    RoomResponse response = new RoomResponse(saveRoom.getId(), saveRoom.getRoomType(), saveRoom.getRoomPrice());
    return ResponseEntity.ok(response);
  }
}
