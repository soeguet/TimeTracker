package com.soeguet.model.dto;

public record IndexPageDTO(
    String id,
    String name,
    String startOfEmployment,
    String startOfTimeTracking,
    String hoursPerWeek,
    String overTime,
    String workingStatus) {}
