package com.soeguet.model.dto.overview;

import java.util.List;
import java.util.UUID;

public record EmployeeOverviewDTO(UUID uuid,
                                  String name,
                                  List<Workday> workdayList) {}
