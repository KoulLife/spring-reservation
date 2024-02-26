package ToMist.reservation.service;

import ToMist.reservation.model.BookedRoom;
import ToMist.reservation.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
  private final BookingRepository bookingRepository;
  private final IRoomService roomService;

  @Override
  public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
    return bookingRepository.findByRoomId(roomId);
  }
}
