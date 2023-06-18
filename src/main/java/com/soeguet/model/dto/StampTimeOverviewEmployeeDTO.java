package com.soeguet.model.dto;

import java.util.UUID;

public record StampTimeOverviewEmployeeDTO(
    UUID id, UUID employeeId, String date, String time, String status) {}
