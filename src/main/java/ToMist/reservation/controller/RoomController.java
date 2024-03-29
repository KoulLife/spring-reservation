package ToMist.reservation.controller;

import ToMist.reservation.exception.PhotoRetrievalException;
import ToMist.reservation.exception.ResouceNotFoundException;
import ToMist.reservation.model.BookedRoom;
import ToMist.reservation.model.Room;
import ToMist.reservation.response.BookingResponse;
import ToMist.reservation.response.RoomResponse;
import ToMist.reservation.service.BookingService;
import ToMist.reservation.service.IRoomService;
import ToMist.reservation.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

  private final IRoomService roomService;
  private final BookingService bookingService;

  //새로운 방 추가
  @PostMapping("/add/new-room")
  public ResponseEntity<RoomResponse> addNewRoom(
    @RequestParam("photo") MultipartFile photo,
    @RequestParam("roomType") String roomType,
    @RequestParam("roomPrice") BigDecimal roomPrice
  ) throws SQLException, IOException {
    Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
    RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
    return ResponseEntity.ok(response);
  }

  // 타입으로 방 검색
  @GetMapping("/room/types")
  public List<String> getRoomTypes(){
    return roomService.getAllRoomTypes();
  }

  //모든 방 가져오기
  @GetMapping("/all-rooms")
  public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
    List<Room> rooms = roomService.getAllRooms();
    List<RoomResponse> roomResponses = new ArrayList<>();

    for(Room room : rooms){
      byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
      if(photoBytes != null && photoBytes.length > 0){
        String base64Photo = Base64.encodeBase64String(photoBytes);
        RoomResponse roomResponse = getRoomResponse(room);
        roomResponse.setPhoto(base64Photo);
        roomResponses.add(roomResponse);
      }
    }
    return ResponseEntity.ok(roomResponses);
  }

  // 방 삭제 roomId 이용
  @DeleteMapping("/delete/room/{roomId}")
  public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
    roomService.deleteRoom(roomId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/update/{roomId}")
  public ResponseEntity<RoomResponse> updateRoom(
          @PathVariable Long roomId,
          @RequestParam(required = false) String roomType,
          @RequestParam(required = false) BigDecimal roomPrice,
          @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {
    byte[] photoBytes = photo != null && !photo.isEmpty() ? photo.getBytes() : roomService.getRoomPhotoByRoomId(roomId);
    Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;
    Room theRoom = roomService.updateRoom(roomId, roomType, roomPrice, photoBytes);
    theRoom.setPhoto(photoBlob);
    RoomResponse roomResponse = getRoomResponse(theRoom);
    return ResponseEntity.ok(roomResponse);
  }

  // id로 방 찾기
  @GetMapping("/room/{roomId}")
  public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
    Optional<Room> theRoom = roomService.getRoomById(roomId);
    return theRoom.map(room -> {
      RoomResponse roomResponse = getRoomResponse(room);
      return  ResponseEntity.ok(Optional.of(roomResponse));
    }).orElseThrow(() -> new ResouceNotFoundException("Room not found"));
  }

  // 사용 가능한 방 정보 확인
  @GetMapping("/available-rooms")
  public ResponseEntity<List<RoomResponse>> getAvailableRooms(
          @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkInDate,
          @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
          @RequestParam("roomType") String roomType
          ) throws SQLException {
    List<Room> availableRooms = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
    List<RoomResponse> roomResponses = new ArrayList<>();
    for(Room room : availableRooms){
      byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
      if (photoBytes != null && photoBytes.length > 0){
        String photoBase64 = Base64.encodeBase64String(photoBytes);
        RoomResponse roomResponse = getRoomResponse(room);
        roomResponse.setPhoto(photoBase64);
        roomResponses.add(roomResponse);
      }
    }
    if(roomResponses.isEmpty()){
      return ResponseEntity.noContent().build();
    }else {
      return ResponseEntity.ok(roomResponses);
    }
  }

  // 방 상태
  private RoomResponse getRoomResponse(Room room){
    List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
    List<BookingResponse> bookingInfo = bookings
            .stream()
            .map(booking -> new BookingResponse(booking.getBookingId(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate(),
                    booking.getBookingConfirmationCode())).toList();

    byte[] photoBytes = null;
    Blob photoBlob = room.getPhoto();

    if(photoBlob != null){
      try {
        photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
      }catch (SQLException e){
        throw new PhotoRetrievalException("Error retrieving photo");
      }
    }
    return new RoomResponse(
            room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoBytes,bookingInfo);
  }

  // roomId로 예약된 방들 가져오기
  private List<BookedRoom> getAllBookingsByRoomId(Long roomId){
    return bookingService.getAllBookingsByRoomId(roomId);
  }
}
















