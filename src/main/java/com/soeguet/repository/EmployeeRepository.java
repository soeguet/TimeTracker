package com.soeguet.repository;

import com.soeguet.model.Employee;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  Optional<Employee> findByFirstname(String firstname);

  Optional<Employee> getEmployeeByFirstname(String s);
}
