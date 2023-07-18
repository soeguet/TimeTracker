package com.soeguet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Objects;

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

  public Employee(UUID id, String firstname, Duration workingHours, LocalDate startOfEmployment, LocalDate startOfTimeTracking) {
    this.id = id;
    this.firstname = firstname;
    this.workingHours = workingHours;
    this.startOfEmployment = startOfEmployment;
    this.startOfTimeTracking = startOfTimeTracking;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public Duration getWorkingHours() {
    return this.workingHours;
  }

  public void setWorkingHours(Duration workingHours) {
    this.workingHours = workingHours;
  }

  public LocalDate getStartOfEmployment() {
    return this.startOfEmployment;
  }

  public void setStartOfEmployment(LocalDate startOfEmployment) {
    this.startOfEmployment = startOfEmployment;
  }

  public LocalDate getStartOfTimeTracking() {
    return this.startOfTimeTracking;
  }

  public void setStartOfTimeTracking(LocalDate startOfTimeTracking) {
    this.startOfTimeTracking = startOfTimeTracking;
  }

  public Employee id(UUID id) {
    setId(id);
    return this;
  }

  public Employee firstname(String firstname) {
    setFirstname(firstname);
    return this;
  }

  public Employee workingHours(Duration workingHours) {
    setWorkingHours(workingHours);
    return this;
  }

  public Employee startOfEmployment(LocalDate startOfEmployment) {
    setStartOfEmployment(startOfEmployment);
    return this;
  }

  public Employee startOfTimeTracking(LocalDate startOfTimeTracking) {
    setStartOfTimeTracking(startOfTimeTracking);
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstname, employee.firstname) && Objects.equals(workingHours, employee.workingHours) && Objects.equals(startOfEmployment, employee.startOfEmployment) && Objects.equals(startOfTimeTracking, employee.startOfTimeTracking);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, workingHours, startOfEmployment, startOfTimeTracking);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", firstname='" + getFirstname() + "'" +
      ", workingHours='" + getWorkingHours() + "'" +
      ", startOfEmployment='" + getStartOfEmployment() + "'" +
      ", startOfTimeTracking='" + getStartOfTimeTracking() + "'" +
      "}";
  }
  
}
