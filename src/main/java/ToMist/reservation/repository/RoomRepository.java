package ToMist.reservation.repository;

import ToMist.reservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
  // 맞춤형 쿼리를 작성
  // DISTINCT은 중복을 제거해준다. 예시로 a,a,b,b,c가 있다면 a,b,c만 나오는 기능이다.
  @Query("SELECT DISTINCT r.roomType from Room r")
  List<String> findDistinctRoomTypes();
}
