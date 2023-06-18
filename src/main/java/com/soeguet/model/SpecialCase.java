package com.soeguet.model;

import com.soeguet.model.enums.Case;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpecialCase {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID employeeId;
  @Enumerated private Case cause;
  private LocalDate date;
  private Duration timeValue;
}
