package com.soeguet.model;

import com.soeguet.model.enums.Case;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class SpecialCase {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID employeeId;
  @Enumerated private Case cause;
  private LocalDate date;
  private Duration timeValue;

  public SpecialCase() {
  }

  public SpecialCase(UUID employeeId, Case cause, LocalDate date, Duration timeValue) {
    this.employeeId = employeeId;
    this.cause = cause;
    this.date = date;
    this.timeValue = timeValue;
  }
}
