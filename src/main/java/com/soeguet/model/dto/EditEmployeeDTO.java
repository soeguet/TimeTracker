package com.soeguet.model.dto;

import java.util.UUID;

public record EditEmployeeDTO(UUID id, String name, String hoursPerWeek, String startOfEmployment) {
}
