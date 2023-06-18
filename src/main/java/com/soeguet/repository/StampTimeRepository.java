package com.soeguet.repository;

import com.soeguet.model.StampTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampTimeRepository extends JpaRepository<StampTime, UUID> {
  List<StampTime> findAllByEmployeeId(UUID employeeId);
}
