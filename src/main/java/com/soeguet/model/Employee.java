package com.soeguet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String firstname;
  private Duration workingHours;
  private LocalDate startOfEmployment;
  private LocalDate startOfTimeTracking;

  public Employee() {
  }

  public Employee(String firstname, Duration workingHours, LocalDate startOfEmployment, LocalDate startOfTimeTracking) {
    this.firstname = firstname;
    this.workingHours = workingHours;
    this.startOfEmployment = startOfEmployment;
    this.startOfTimeTracking = startOfTimeTracking;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public Duration getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(Duration workingHours) {
    this.workingHours = workingHours;
  }

  public LocalDate getStartOfEmployment() {
    return startOfEmployment;
  }

  public void setStartOfEmployment(LocalDate startOfEmployment) {
    this.startOfEmployment = startOfEmployment;
  }

  public LocalDate getStartOfTimeTracking() {
    return startOfTimeTracking;
  }

  public void setStartOfTimeTracking(LocalDate startOfTimeTracking) {
    this.startOfTimeTracking = startOfTimeTracking;
  }
}
