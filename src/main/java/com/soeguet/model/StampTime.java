package com.soeguet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class StampTime {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID employeeId;
  private LocalDate date;
  private LocalTime time;
  private Duration workingHoursPerWeek;

  public StampTime() {
  }

  public StampTime(UUID employeeId, LocalDate date, LocalTime time, Duration workingHoursPerWeek) {
    this.employeeId = employeeId;
    this.date = date;
    this.time = time;
    this.workingHoursPerWeek = workingHoursPerWeek;
  }

  public UUID getId() {
    return id;
  }

  public UUID getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(UUID employeeId) {
    this.employeeId = employeeId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public Duration getWorkingHoursPerWeek() {
    return workingHoursPerWeek;
  }

  public void setWorkingHoursPerWeek(Duration workingHoursPerWeek) {
    this.workingHoursPerWeek = workingHoursPerWeek;
  }

  @Override
  public String toString() {
    return "StampTime{" +
            "id=" + id +
            ", employeeId=" + employeeId +
            ", date=" + date +
            ", time=" + time +
            ", workingHoursPerWeek=" + workingHoursPerWeek +
            '}';
  }
}
