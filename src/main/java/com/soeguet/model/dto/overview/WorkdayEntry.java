package com.soeguet.model.dto.overview;

import java.time.LocalTime;
import java.util.UUID;

public record WorkdayEntry(UUID uuid, LocalTime workTime) {}
