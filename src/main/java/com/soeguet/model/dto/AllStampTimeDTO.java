package com.soeguet.model.dto;

import java.util.UUID;

public record AllStampTimeDTO(UUID id,
                              String employeeName,
                              String date,
                              String time) {
}
