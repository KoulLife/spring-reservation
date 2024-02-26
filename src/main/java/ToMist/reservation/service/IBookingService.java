package ToMist.reservation.service;

import ToMist.reservation.model.BookedRoom;

import java.util.List;

public interface IBookingService {
  public List<BookedRoom> getAllBookingsByRoomId(Long roomId);
}
