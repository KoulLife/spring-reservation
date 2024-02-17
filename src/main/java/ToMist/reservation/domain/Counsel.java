package ToMist.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Counsel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long counselId;

  @Column private LocalDateTime appliedAt;

  @Column private String name;

  @Column private String cellPhone;

  @Column private String email;

  @Column private String memo;

  @Column private String address;

  @Column private String addressDetail;

  @Column private String zipCode;
}
