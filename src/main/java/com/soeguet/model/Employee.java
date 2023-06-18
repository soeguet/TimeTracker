package com.soeguet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstname;
    private Duration workingHours;
    private LocalDate startOfEmployment;
    private LocalDate startOfTimeTracking;
}
