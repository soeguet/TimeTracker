package com.soeguet.repository;

import com.soeguet.model.SpecialCase;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialCaseRepository extends JpaRepository<SpecialCase, UUID> {}
